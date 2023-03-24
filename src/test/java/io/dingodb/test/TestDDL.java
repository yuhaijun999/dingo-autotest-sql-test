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

    @BeforeClass (alwaysRun = true)
    public static void setupAll() {
        sqlHelper = new SQLHelper();
    }

    @AfterClass (alwaysRun = true)
    public static void tearDownAll() throws SQLException {
        System.out.println(createTableSet);
        if(createTableSet.size() > 0) {
            for (String s : createTableSet) {
                sqlHelper.doDropTable(s);
            }
        }
        createTableSet.clear();
    }

    @BeforeMethod (alwaysRun = true)
    public void setup() throws Exception {
    }

    @AfterMethod (alwaysRun = true)
    public void cleanUp() throws Exception {
        if(createTableSet.size() > 0) {
            for (String s : createTableSet) {
                sqlHelper.doDropTable(s);
            }
        }
        createTableSet.clear();
    }

    @Test(priority = 0, enabled = true, dataProvider = "ddlData1", dataProviderClass = YamlDataHelper.class, description = "ddl操作1")
    public void testDDL1(LinkedHashMap<String,String> param) throws SQLException, IOException, ClassNotFoundException {
        if (param.get("Testable").trim().equals("n") || param.get("Testable").trim().equals("N")) {
            throw new SkipException("skip this test case");
        }
        String ddlSql = param.get("Ddl_sql").trim();
        String querySql = param.get("Query_sql").trim();
        if (param.get("Table_schema_ref").trim().length() > 0) {
            List<String> tableList = new ArrayList<>();
            List<String> schemaList = CastUtils.construct1DListIncludeBlank(param.get("Table_schema_ref"),",");
            for (int i = 0; i < schemaList.size(); i++) {
                String tableName = "";
                if (!schemaList.get(i).contains("_")) {
                    tableName = param.get("TestID").trim() + "_0" + i + schemaList.get(i).trim();
                    sqlHelper.execFile(TestDQL.class.getClassLoader().getResourceAsStream(iniReader.getValue("TableSchema",schemaList.get(i).trim())), tableName);
                } else {
                    String schemaName = schemaList.get(i).trim().substring(0,schemaList.get(i).trim().indexOf("_"));
                    tableName = param.get("TestID").trim() + "_0" + i + schemaName;
                    sqlHelper.execFile(TestDQL.class.getClassLoader().getResourceAsStream(iniReader.getValue("TableSchema",schemaName)), tableName);
                }
                ddlSql = ddlSql.replace("$" + schemaList.get(i).trim(), tableName);
                querySql = querySql.replace("$" + schemaList.get(i).trim(), tableName);
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
                        sqlHelper.execFile(TestDQL.class.getClassLoader().getResourceAsStream(iniReader.getValue("DDLValues", value_List.get(j).trim())), tableName);
                    } else {
                        String schemaName = schemaList.get(j).trim().substring(0,schemaList.get(j).trim().indexOf("_"));
                        tableName = param.get("TestID").trim() + "_0" + j + schemaName;
                        sqlHelper.execFile(TestDQL.class.getClassLoader().getResourceAsStream(iniReader.getValue("DDLValues", value_List.get(j).trim())), tableName);
                    }
                }
            }
        }
        
        if (param.get("Validation_type").equals("csv_equals")) {
            String resultFile = param.get("Query_result").trim();
            List<List<String>> expectedResult = ParseCsv.splitCsvString(resultFile);
            System.out.println("Expected: " + expectedResult);
            sqlHelper.execSql(ddlSql);
            List<List<String>> actualResult = sqlHelper.statementQueryWithHead(querySql);
            System.out.println("Actual: " + actualResult);
            Assert.assertEquals(actualResult, expectedResult);
        } else if (param.get("Validation_type").equals("csv_containsAll")) {
            String resultFile = param.get("Query_result").trim();
            List<List<String>> expectedResult = ParseCsv.splitCsvString(resultFile);
            System.out.println("Expected: " + expectedResult);
            sqlHelper.execSql(ddlSql);
            List<List<String>> actualResult = sqlHelper.statementQueryWithHead(querySql);
            System.out.println("Actual: " + actualResult);
            Assert.assertTrue(actualResult.containsAll(expectedResult));
            Assert.assertTrue(expectedResult.containsAll(actualResult));
        } else if (param.get("Validation_type").equals("table_assert")) {
            sqlHelper.execSql(ddlSql);
            String drop_table_name = "";
            if (ddlSql.contains("drop")) {
                drop_table_name = ddlSql.substring(11);
            }
            List<String> existTableList = JDBCUtils.getTableList();
            Assert.assertFalse(existTableList.contains(drop_table_name));
        }
    }
}
