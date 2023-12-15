/*
 * Copyright 2021 DataCanvas
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.dingodb.mysqltest;

import datahelper.MySQLYamlDataHelper;
import io.dingodb.common.utils.MySQLUtils;
import io.dingodb.dailytest.MySQLHelper;
import io.dingodb.test.TestDQLbak;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.CastUtils;
import utils.ParseCsv;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;

public class TestDDLMySQL extends BaseTestSuiteMySQL{
    private static MySQLHelper mySQLHelper;
    private static HashSet<String> createTableSet = new HashSet<>();
    private static HashSet<String> createSchemaSet = new HashSet<>();

    @BeforeClass (alwaysRun = true)
    public static void setupAll() {
        mySQLHelper = new MySQLHelper();
    }

    @AfterClass (alwaysRun = true)
    public static void tearDownAll() throws SQLException, IOException, ClassNotFoundException {
        if(createTableSet.size() > 0) {
            List<String> finalTableList = MySQLUtils.getTableList();
            for (String s : createTableSet) {
                if (finalTableList.contains(s.toUpperCase())) {
                    mySQLHelper.doDropTable(s);
                }
            }
        }

        if(createSchemaSet.size() > 0) {
            List<String> finalSchemaList = MySQLUtils.getSchemaList();
            for (String s : createSchemaSet) {
                if (finalSchemaList.contains(s.toUpperCase())) {
                    mySQLHelper.doDropSchema(s);
                }
            }
        }
    }

    @BeforeMethod (alwaysRun = true)
    public void setup() throws Exception {
    }

    @AfterMethod (alwaysRun = true)
    public void cleanUp() throws Exception {
    }

    @Test(priority = 0, enabled = true, dataProvider = "mysqlDDLData1", dataProviderClass = MySQLYamlDataHelper.class, description = "ddl操作1")
    public void testDDL1(LinkedHashMap<String,String> param) throws SQLException, IOException, ClassNotFoundException {
        if (param.get("Testable").trim().equals("n") || param.get("Testable").trim().equals("N")) {
            throw new SkipException("skip this test case");
        }
        List<String> tableList = new ArrayList<>();
        String ddlSql = param.get("Ddl_sql").trim();
        String querySql = param.get("Query_sql").trim();
        if (param.get("Table_schema_ref").trim().length() > 0) {
            List<String> schemaList = CastUtils.construct1DListIncludeBlank(param.get("Table_schema_ref"),",");
            for (int i = 0; i < schemaList.size(); i++) {
                String tableName = "";
                if (!schemaList.get(i).contains("_")) {
                    tableName = "mysql" + param.get("TestID").trim() + "_0" + i + schemaList.get(i).trim();
                    mySQLHelper.execFile(TestDQLbak.class.getClassLoader().getResourceAsStream(mysqlIniReader.getValue("TableSchema",schemaList.get(i).trim())), tableName);
                } else {
                    String schemaName = schemaList.get(i).trim().substring(0,schemaList.get(i).trim().indexOf("_"));
                    tableName = "mysql" + param.get("TestID").trim() + "_0" + i + schemaName;
                    mySQLHelper.execFile(TestDQLbak.class.getClassLoader().getResourceAsStream(mysqlIniReader.getValue("TableSchema",schemaName)), tableName);
                }
                ddlSql = ddlSql.replace("$" + schemaList.get(i).trim(), tableName);
                querySql = querySql.replace("$" + schemaList.get(i).trim(), tableName.toUpperCase());
                if (!ddlSql.contains("drop")) {
                    tableList.add(tableName);
                }
            }
            createTableSet.addAll(tableList);
            if (param.get("Table_value_ref").trim().length() > 0) {
                List<String> value_List = CastUtils.construct1DListIncludeBlank(param.get("Table_value_ref").trim(),",");
                for (int j = 0; j < value_List.size(); j++) {
                    String tableName = "";
                    if (!schemaList.get(j).trim().contains("_")) {
                        tableName = "mysql" + param.get("TestID").trim() + "_0" + j + schemaList.get(j).trim();
                        mySQLHelper.execFile(TestDQLbak.class.getClassLoader().getResourceAsStream(mysqlIniReader.getValue("DDLValues", value_List.get(j).trim())), tableName);
                    } else {
                        String schemaName = schemaList.get(j).trim().substring(0,schemaList.get(j).trim().indexOf("_"));
                        tableName = "mysql" + param.get("TestID").trim() + "_0" + j + schemaName;
                        mySQLHelper.execFile(TestDQLbak.class.getClassLoader().getResourceAsStream(mysqlIniReader.getValue("DDLValues", value_List.get(j).trim())), tableName);
                    }
                }
            }
        }

        if (param.get("Sub_component").equalsIgnoreCase("databaseCreate")) {
            String databaseName = param.get("Ddl_sql").substring(16);
            createSchemaSet.add(databaseName);
        }

        if (param.get("Sub_component").equalsIgnoreCase("schemaCreate")) {
            String schemaName = param.get("Ddl_sql").substring(14);
            createSchemaSet.add(schemaName);
        }
        
        if (param.get("Validation_type").equals("csv_equals")) {
            String resultFile = param.get("Query_result").trim();
            List<List<String>> expectedResult = new ArrayList<>();
            if (!param.get("Component").equalsIgnoreCase("ComplexDataType")){
                expectedResult = ParseCsv.splitCsvString(resultFile,",");
            } else {
                expectedResult = ParseCsv.splitCsvString(resultFile,"&");
            }
            System.out.println("Expected: " + expectedResult);
            mySQLHelper.execBatchSqlWithState(ddlSql);
            List<List<String>> actualResult = mySQLHelper.statementQueryWithHead(querySql);
            System.out.println("Actual: " + actualResult);
            Assert.assertEquals(actualResult, expectedResult);
        } else if (param.get("Validation_type").equals("csv_containsAll")) {
            String resultFile = param.get("Query_result").trim();
            List<List<String>> expectedResult = new ArrayList<>();
            if (!param.get("Component").equalsIgnoreCase("ComplexDataType")){
                expectedResult = ParseCsv.splitCsvString(resultFile,",");
            } else {
                expectedResult = ParseCsv.splitCsvString(resultFile,"&");
            }
            System.out.println("Expected: " + expectedResult);
            mySQLHelper.execBatchSqlWithState(ddlSql);
            List<List<String>> actualResult = mySQLHelper.statementQueryWithHead(querySql);
            System.out.println("Actual: " + actualResult);
            Assert.assertTrue(actualResult.containsAll(expectedResult));
            Assert.assertTrue(expectedResult.containsAll(actualResult));
        } else if (param.get("Validation_type").equals("csv_contains")) {
            String resultFile = param.get("Query_result").trim();
            List<List<String>> expectedResult = new ArrayList<>();
            if (!param.get("Component").equalsIgnoreCase("ComplexDataType")){
                expectedResult = ParseCsv.splitCsvString(resultFile,",");
            } else {
                expectedResult = ParseCsv.splitCsvString(resultFile,"&");
            }
            System.out.println("Expected: " + expectedResult);
            mySQLHelper.execBatchSqlWithState(ddlSql);
            List<List<String>> actualResult = mySQLHelper.statementQueryWithHead(querySql);
            System.out.println("Actual: " + actualResult);
            Assert.assertTrue(actualResult.containsAll(expectedResult));
        } else if (param.get("Validation_type").equals("table_assert")) {
            mySQLHelper.execSql(ddlSql);
            String drop_table_name = "";
            if (ddlSql.contains("drop")) {
                if (ddlSql.contains("if exists")) {
                    drop_table_name = ddlSql.substring(21);
                } else {
                    drop_table_name = ddlSql.substring(11);
                }
            }
            List<String> existTableList = MySQLUtils.getTableList();
            Assert.assertFalse(existTableList.contains(drop_table_name));
        } else if (param.get("Validation_type").equals("justExec")) {
            mySQLHelper.execBatchSqlWithState(ddlSql);
        }

//        if (tableList.size() > 0) {
//            for (String s : tableList) {
//                mySQLHelper.doDropTable(s);
//            }
//        }
    }
}
