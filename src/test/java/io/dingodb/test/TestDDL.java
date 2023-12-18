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

package io.dingodb.test;

import datahelper.YamlDataHelper;
import io.dingodb.common.utils.JDBCUtils;
import io.dingodb.dailytest.SQLHelper;
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

public class TestDDL extends BaseTestSuite{
    private static SQLHelper sqlHelper;
    private static HashSet<String> createTableSet = new HashSet<>();
    private static HashSet<String> createSchemaSet = new HashSet<>();
    private static HashSet<String> schemaTableSet = new HashSet<>();

    @BeforeClass (alwaysRun = true)
    public static void setupAll() {
        sqlHelper = new SQLHelper();
    }

    @AfterClass (alwaysRun = true)
    public static void tearDownAll() throws SQLException, IOException, ClassNotFoundException {
//        System.out.println(createTableSet);
        if(createTableSet.size() > 0) {
            List<String> finalTableList = JDBCUtils.getTableList();
            for (String s : createTableSet) {
                if (finalTableList.contains(s.toUpperCase())) {
                    sqlHelper.doDropTable(s);
                }
            }
        }
        
        System.out.println(schemaTableSet);
        for (String s : schemaTableSet) {
            sqlHelper.doDropTable(s);
        }

        if(createSchemaSet.size() > 0) {
            System.out.println(createSchemaSet);
            List<String> finalSchemaList = JDBCUtils.getSchemaList();
            for (String s : createSchemaSet) {
                if (finalSchemaList.contains(s.toUpperCase())) {
                    sqlHelper.doDropSchema(s);
                }
            }
        }
    }

    @BeforeMethod (alwaysRun = true)
    public void setup() throws Exception {
    }

    @AfterMethod (alwaysRun = true)
    public void cleanUp() throws Exception {
//        if(createTableSet.size() > 0) {
//            for (String s : createTableSet) {
//                sqlHelper.doDropTable(s);
//            }
//        }
//        createTableSet.clear();
    }

    @Test(priority = 0, enabled = true, dataProvider = "ddlData1", dataProviderClass = YamlDataHelper.class, description = "ddl操作1")
    public void testDDL1(LinkedHashMap<String,String> param) throws SQLException, IOException, ClassNotFoundException {
        if (param.get("Testable").trim().equals("n") || param.get("Testable").trim().equals("N")) {
            throw new SkipException("skip this test case");
        }
        List<String> tableList = new ArrayList<>();
        String ddlSql = param.get("Ddl_sql").trim();
        String querySql = param.get("Query_sql").trim();
        if (param.get("Table_schema_ref").trim().length() > 0) {
//            List<String> tableList = new ArrayList<>();
            List<String> schemaList = CastUtils.construct1DListIncludeBlank(param.get("Table_schema_ref"),",");
            for (int i = 0; i < schemaList.size(); i++) {
                String tableName = "";
                if (!schemaList.get(i).contains("_")) {
                    tableName = param.get("TestID").trim() + "_0" + i + schemaList.get(i).trim();
                    sqlHelper.execFile(TestDQLbak.class.getClassLoader().getResourceAsStream(iniReader.getValue("TableSchema",schemaList.get(i).trim())), tableName);
                } else {
                    String schemaName = schemaList.get(i).trim().substring(0,schemaList.get(i).trim().indexOf("_"));
                    tableName = param.get("TestID").trim() + "_0" + i + schemaName;
                    sqlHelper.execFile(TestDQLbak.class.getClassLoader().getResourceAsStream(iniReader.getValue("TableSchema",schemaName)), tableName);
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
                        tableName = param.get("TestID").trim() + "_0" + j + schemaList.get(j).trim();
                        sqlHelper.execFile(TestDQLbak.class.getClassLoader().getResourceAsStream(iniReader.getValue("DDLValues", value_List.get(j).trim())), tableName);
                    } else {
                        String schemaName = schemaList.get(j).trim().substring(0,schemaList.get(j).trim().indexOf("_"));
                        tableName = param.get("TestID").trim() + "_0" + j + schemaName;
                        sqlHelper.execFile(TestDQLbak.class.getClassLoader().getResourceAsStream(iniReader.getValue("DDLValues", value_List.get(j).trim())), tableName);
                    }
                }
            }
        }

        System.out.println(ddlSql);
        System.out.println(querySql);
        
        if (param.get("Sub_component").equalsIgnoreCase("databaseCreate")) {
            String databaseName = param.get("Ddl_sql").substring(16);
            createSchemaSet.add(databaseName);
        }

        if (param.get("Sub_component").equalsIgnoreCase("schemaCreate")) {
            String schemaName = param.get("Ddl_sql").substring(14);
            createSchemaSet.add(schemaName);
        }
        
        if (param.get("Component").equalsIgnoreCase("Schema")) {
            if (param.get("Sub_component").equalsIgnoreCase("Information_Schema")) {
                if (param.get("Ddl_sql").contains("create schema")) {
                    int endNum = param.get("Ddl_sql").indexOf(";");
                    String schemaName = param.get("Ddl_sql").substring(14, endNum).trim();
                    createSchemaSet.add(schemaName);
                }
                if (param.get("Ddl_sql").contains("create database")) {
                    int endNum = param.get("Ddl_sql").indexOf(";");
                    String schemaName = param.get("Ddl_sql").substring(16, endNum).trim();
                    createSchemaSet.add(schemaName);
                }
                if (param.get("Ddl_sql").contains("create table")) {
                    int tableStartNum = param.get("Ddl_sql").indexOf("table") + 6;
                    int tableEndNum = param.get("Ddl_sql").indexOf("(");
                    String tableName = param.get("Ddl_sql").substring(tableStartNum, tableEndNum).trim();
                    if (!param.get("Ddl_sql").contains("drop table")) {
                        schemaTableSet.add(tableName);
                    }
                }
            }
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
            sqlHelper.execBatchSqlWithState(ddlSql);
            List<List<String>> actualResult = sqlHelper.statementQueryWithHead(querySql);
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
            sqlHelper.execBatchSqlWithState(ddlSql);
            List<List<String>> actualResult = sqlHelper.statementQueryWithHead(querySql);
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
            sqlHelper.execBatchSqlWithState(ddlSql);
            List<List<String>> actualResult = sqlHelper.statementQueryWithHead(querySql);
            System.out.println("Actual: " + actualResult);
            Assert.assertTrue(actualResult.containsAll(expectedResult));
        } else if (param.get("Validation_type").equals("table_assert")) {
            sqlHelper.execBatchSqlWithState(ddlSql);
            String drop_table_name = "";
            if (ddlSql.contains("drop")) {
                if (ddlSql.contains("if exists")) {
                    drop_table_name = ddlSql.substring(21);
                } else {
                    drop_table_name = ddlSql.substring(11);
                }
            }
            List<String> existTableList = JDBCUtils.getTableList();
            Assert.assertFalse(existTableList.contains(drop_table_name));
        } else if (param.get("Validation_type").equals("justExec")) {
            sqlHelper.execBatchSqlWithState(ddlSql);
        }

//        if (tableList.size() > 0) {
//            for (String s : tableList) {
//                sqlHelper.doDropTable(s);
//            }
//        }
    }
}
