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

package io.dingodb.dingotest;

import datahelper.YamlDataHelper;
import io.dingodb.common.utils.JDBCUtils;
import io.dingodb.dailytest.SQLHelper;
import org.apache.commons.io.IOUtils;
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
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;

public class TestDML extends BaseTestSuite {
    private static SQLHelper sqlHelper;
    public static Connection connection;
    private static HashSet<String> createTableSet = new HashSet<>();


    @BeforeClass (alwaysRun = true)
    public static void setupAll() throws SQLException, ClassNotFoundException {
        sqlHelper = new SQLHelper();
        JDBCUtils jdbcUtils = new JDBCUtils();
        connection = jdbcUtils.getDingoConnectionInstance();
        Assert.assertNotNull(connection);
    }

    @AfterClass (alwaysRun = true)
    public static void tearDownAll() throws SQLException, IOException, ClassNotFoundException {
//        System.out.println(createTableSet);
        if(createTableSet.size() > 0) {
            List<String> finalTableList = JDBCUtils.getTableList();
            for (String s : createTableSet) {
                if (finalTableList.contains(s.toUpperCase())) {
                    sqlHelper.doDropTable(connection, s);
                }
            }
        }
        
        JDBCUtils.closeResource(connection);
    }

    @BeforeMethod (alwaysRun = true)
    public void setup() throws Exception {
    }

    @AfterMethod (alwaysRun = true)
    public void cleanUp() throws Exception {
    }

    @Test(priority = 0, enabled = true, dataProvider = "dmlInsertData", dataProviderClass = YamlDataHelper.class, description = "dml操作insert，正向用例")
    public void test01DMLInsert(LinkedHashMap<String,String> param) throws SQLException, IOException, ClassNotFoundException {
//        JDBCUtils jdbcUtils = new JDBCUtils();
//        Connection connection = jdbcUtils.getDingoConnectionInstance();
        if (param.get("Testable").trim().equals("n") || param.get("Testable").trim().equals("N")) {
            throw new SkipException("skip this test case");
        }
        List<String> tableList = new ArrayList<>();
        String insertSql = "";
        String dmlSql = param.get("Dml_sql").trim();
        String querySql1 = param.get("Query_sql1").trim();
        String querySql2 = param.get("Query_sql2").trim();
        if (param.get("Table_schema_ref").trim().length() > 0) {
//            List<String> tableList = new ArrayList<>();
            List<String> schemaList = CastUtils.construct1DListIncludeBlank(param.get("Table_schema_ref"),",");
            for (int i = 0; i < schemaList.size(); i++) {
                String tableName = "";
                if (!schemaList.get(i).trim().contains("_")) {
                    tableName = param.get("TestID").trim() + "_0" + i + schemaList.get(i).trim();
                    if (param.get("TestID").contains("btree")) {
                        sqlHelper.execFile(connection, TestDML.class.getClassLoader().getResourceAsStream(iniReaderBTREE.getValue("TableSchema",schemaList.get(i).trim())), tableName);
                    } else {
                        sqlHelper.execFile(connection, TestDML.class.getClassLoader().getResourceAsStream(iniReader.getValue("TableSchema",schemaList.get(i).trim())), tableName);
                    }
                } else {
                    String schemaName = schemaList.get(i).trim().substring(0,schemaList.get(i).trim().indexOf("_"));
                    tableName = param.get("TestID").trim() + "_0" + i + schemaName;
                    if (param.get("TestID").contains("btree")) {
                        sqlHelper.execFile(connection, TestDML.class.getClassLoader().getResourceAsStream(iniReaderBTREE.getValue("TableSchema",schemaName)), tableName);
                    } else {
                        sqlHelper.execFile(connection, TestDML.class.getClassLoader().getResourceAsStream(iniReader.getValue("TableSchema",schemaName)), tableName);
                    }
                }
                tableList.add(tableName);
                querySql1 = querySql1.replace("$"+schemaList.get(i).trim(),tableName);
                if (param.get("Dml_sql").trim().length() > 0) {
                    dmlSql = dmlSql.replace("$"+schemaList.get(i).trim(),tableName);
                    querySql2 = querySql2.replace("$"+schemaList.get(i).trim(),tableName);
                }
            }
            createTableSet.addAll(tableList);
            if (param.get("Table_value_ref").trim().length() > 0) {
                List<String> value_List = CastUtils.construct1DListIncludeBlank(param.get("Table_value_ref").trim(),",");
                for (int j = 0; j < value_List.size(); j++) {
                    String tableName = "";
                    if (!schemaList.get(j).trim().contains("_")) {
                        tableName = param.get("TestID").trim() + "_0" + j + schemaList.get(j).trim();
                    } else {
                        String schemaName = schemaList.get(j).trim().substring(0,schemaList.get(j).trim().indexOf("_"));
                        tableName = param.get("TestID").trim() + "_0" + j + schemaName;
                    }
                    if (param.get("TestID").contains("btree")) {
                        InputStream stream = TestDML.class.getClassLoader().getResourceAsStream(iniReaderBTREE.getValue("DMLValues", value_List.get(j).trim()));
                        insertSql = IOUtils.toString(stream, StandardCharsets.UTF_8).replace("$table", tableName);
                    } else {
                        InputStream stream = TestDML.class.getClassLoader().getResourceAsStream(iniReader.getValue("DMLValues", value_List.get(j).trim()));
                        insertSql = IOUtils.toString(stream, StandardCharsets.UTF_8).replace("$table", tableName);
                    }
                }
            }
        }
        
        if (param.get("Validation_type").equals("csv_equals")) {
            String resultFile1 = param.get("Expected_result1").trim();
            List<List<String>> expectedResult = new ArrayList<>();
            if (!param.get("Component").equalsIgnoreCase("ComplexDataType")){
                expectedResult = ParseCsv.splitCsvString(resultFile1,",");
            } else {
                expectedResult = ParseCsv.splitCsvString(resultFile1,"&");
            }
            System.out.println("Expected: " + expectedResult);
            List<List<String>> actualResult = sqlHelper.doDMLAndQueryWithHead(connection, insertSql, querySql1);
            System.out.println("Actual: " + actualResult);
            Assert.assertEquals(actualResult, expectedResult);
            if (dmlSql.length() > 0) {
                String resultFile2 = param.get("Expected_result2").trim();
                List<List<String>> expectedResultAfterDML = new ArrayList<>();
                if (!param.get("Component").equalsIgnoreCase("ComplexDataType")){
                    expectedResultAfterDML = ParseCsv.splitCsvString(resultFile2,",");
                } else {
                    expectedResultAfterDML = ParseCsv.splitCsvString(resultFile2,"&");
                }
                System.out.println("Expected after dml: " + expectedResultAfterDML);
                List<List<String>> actualResultAfterDML = sqlHelper.doDMLAndQueryWithHead(connection, dmlSql, querySql2);
                System.out.println("Actual after dml: " + actualResultAfterDML);
                Assert.assertEquals(actualResultAfterDML, expectedResultAfterDML);
            }
        } else if (param.get("Validation_type").equals("csv_containsAll")) {
            String resultFile1 = param.get("Expected_result1").trim();
            List<List<String>> expectedResult = new ArrayList<>();
            if (!param.get("Component").equalsIgnoreCase("ComplexDataType")){
                expectedResult = ParseCsv.splitCsvString(resultFile1,",");
            } else {
                expectedResult = ParseCsv.splitCsvString(resultFile1,"&");
            }
            System.out.println("Expected: " + expectedResult);
            List<List<String>> actualResult = sqlHelper.doDMLAndQueryWithHead(connection, insertSql, querySql1);
            System.out.println("Actual: " + actualResult);
            Assert.assertTrue(actualResult.containsAll(expectedResult));
            Assert.assertTrue(expectedResult.containsAll(actualResult));
            if (dmlSql.length() > 0) {
                String resultFile2 = param.get("Expected_result2").trim();
                List<List<String>> expectedResultAfterDML = new ArrayList<>();
                if (!param.get("Component").equalsIgnoreCase("ComplexDataType")){
                    expectedResultAfterDML = ParseCsv.splitCsvString(resultFile2,",");
                } else {
                    expectedResultAfterDML = ParseCsv.splitCsvString(resultFile2,"&");
                }
                System.out.println("Expected after dml: " + expectedResultAfterDML);
                List<List<String>> actualResultAfterDML = sqlHelper.doDMLAndQueryWithHead(connection, dmlSql, querySql2);
                System.out.println("Actual after dml: " + actualResultAfterDML);
                Assert.assertTrue(actualResultAfterDML.containsAll(expectedResultAfterDML));
                Assert.assertTrue(expectedResultAfterDML.containsAll(actualResultAfterDML));
            }
        }
//        if (tableList.size() > 0) {
//            for (String s : tableList) {
//                sqlHelper.doDropTable(s);
//            }
//        }
//        JDBCUtils.closeResource(connection);
    }

    @Test(priority = 1, enabled = true, dataProvider = "dmlUpDelData", dataProviderClass = YamlDataHelper.class, description = "dml操作update&delete，正向用例")
    public void test02DMLUpdateDelete(LinkedHashMap<String,String> param) throws SQLException, IOException, InterruptedException, ClassNotFoundException {
//        JDBCUtils jdbcUtils = new JDBCUtils();
//        Connection connection = jdbcUtils.getDingoConnectionInstance();
        if (param.get("Testable").trim().equals("n") || param.get("Testable").trim().equals("N")) {
            throw new SkipException("skip this test case");
        }
        List<String> tableList = new ArrayList<>();
        String dmlSql = param.get("Sql_state").trim();
        String querySql = param.get("Query_sql").trim();
        if (param.get("Table_schema_ref").trim().length() > 0) {
//            List<String> tableList = new ArrayList<>();
            List<String> schemaList = CastUtils.construct1DListIncludeBlank(param.get("Table_schema_ref"),",");
            for (int i = 0; i < schemaList.size(); i++) {
                String tableName = "";
                if (!schemaList.get(i).contains("_")) {
                    tableName = param.get("TestID").trim() + "_0" + i + schemaList.get(i).trim();
                    if (param.get("TestID").contains("btree")) {
                        sqlHelper.execFile(connection, TestDML.class.getClassLoader().getResourceAsStream(iniReaderBTREE.getValue("TableSchema",schemaList.get(i).trim())), tableName);
                    } else {
                        sqlHelper.execFile(connection, TestDML.class.getClassLoader().getResourceAsStream(iniReader.getValue("TableSchema",schemaList.get(i).trim())), tableName);
                    }
                } else {
                    String schemaName = schemaList.get(i).trim().substring(0,schemaList.get(i).trim().indexOf("_"));
                    tableName = param.get("TestID").trim() + "_0" + i + schemaName;
                    if (param.get("TestID").contains("btree")) {
                        sqlHelper.execFile(connection, TestDML.class.getClassLoader().getResourceAsStream(iniReaderBTREE.getValue("TableSchema",schemaName)), tableName);
                    } else {
                        sqlHelper.execFile(connection, TestDML.class.getClassLoader().getResourceAsStream(iniReader.getValue("TableSchema",schemaName)), tableName);
                    }
                }
                tableList.add(tableName);
                dmlSql = dmlSql.replace("$" + schemaList.get(i).trim(), tableName);
                querySql = querySql.replace("$" + schemaList.get(i).trim(), tableName);
            }
            createTableSet.addAll(tableList);
            if (param.get("Table_value_ref").trim().length() > 0) {
                List<String> value_List = CastUtils.construct1DListIncludeBlank(param.get("Table_value_ref").trim(),",");
                for (int j = 0; j < value_List.size(); j++) {
                    String tableName = "";
                    if (!schemaList.get(j).trim().contains("_")) {
                        tableName = param.get("TestID").trim() + "_0" + j + schemaList.get(j).trim();
                    } else {
                        String schemaName = schemaList.get(j).trim().substring(0,schemaList.get(j).trim().indexOf("_"));
                        tableName = param.get("TestID").trim() + "_0" + j + schemaName;
                    }
                    if (param.get("TestID").contains("btree")) {
                        sqlHelper.execFile(connection, TestDML.class.getClassLoader().getResourceAsStream(iniReaderBTREE.getValue("DMLValues", value_List.get(j).trim())), tableName);
                    } else {
                        sqlHelper.execFile(connection, TestDML.class.getClassLoader().getResourceAsStream(iniReader.getValue("DMLValues", value_List.get(j).trim())), tableName);
                    }
                }
            }
        }

        if (param.get("Sub_component").equalsIgnoreCase("Explain")) {
            Thread.sleep(330000);
        }
        
        if (param.get("Validation_type").equals("csv_equals")) {
            String resultFile = param.get("Expected_result").trim();
            List<List<String>> expectedResult = new ArrayList<>();
            if (!param.get("Component").equalsIgnoreCase("ComplexDataType")){
                expectedResult = ParseCsv.splitCsvString(resultFile,",");
            } else {
                expectedResult = ParseCsv.splitCsvString(resultFile,"&");
            }
            System.out.println("Expected: " + expectedResult);
            List<List<String>> actualResult = sqlHelper.doDMLAndQueryWithHead(connection, dmlSql, querySql);
            System.out.println("Actual: " + actualResult);
            Assert.assertEquals(actualResult, expectedResult);
        } else if (param.get("Validation_type").equals("csv_containsAll")) {
            String resultFile = param.get("Expected_result").trim();
            List<List<String>> expectedResult = new ArrayList<>();
            if (!param.get("Component").equalsIgnoreCase("ComplexDataType")){
                expectedResult = ParseCsv.splitCsvString(resultFile,",");
            } else {
                expectedResult = ParseCsv.splitCsvString(resultFile,"&");
            }
            System.out.println("Expected: " + expectedResult);
            List<List<String>> actualResult = sqlHelper.doDMLAndQueryWithHead(connection, dmlSql, querySql);
            System.out.println("Actual: " + actualResult);
            Assert.assertTrue(actualResult.containsAll(expectedResult));
            Assert.assertTrue(expectedResult.containsAll(actualResult));
        } else if (param.get("Validation_type").equals("effected_rows_assert")) {
            int expectedEffectedRows = Integer.parseInt(param.get("Effected_rows"));
            System.out.println("Expected effected rows: " + expectedEffectedRows);
            int actualEffectedRows = sqlHelper.doDMLReturnRows(connection, dmlSql);
            System.out.println("Actual effected rows: " + actualEffectedRows);
            Assert.assertEquals(actualEffectedRows, expectedEffectedRows);
        }

//        if (tableList.size() > 0) {
//            for (String s : tableList) {
//                sqlHelper.doDropTable(s);
//            }
//        }
//        JDBCUtils.closeResource(connection);
    }
}
