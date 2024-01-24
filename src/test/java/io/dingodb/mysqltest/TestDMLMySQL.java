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
import io.dingodb.common.utils.DruidUtilsMySQL;
import io.dingodb.dailytest.MySQLHelperDruid;
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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;

public class TestDMLMySQL extends BaseTestSuiteMySQL{
    private static MySQLHelperDruid mySQLHelperDruid;
    private static HashSet<String> createTableSet = new HashSet<>();


    @BeforeClass (alwaysRun = true)
    public static void setupAll() throws SQLException, IOException, ClassNotFoundException {
        mySQLHelperDruid = new MySQLHelperDruid();
    }

    @AfterClass (alwaysRun = true)
    public static void tearDownAll() throws SQLException, IOException, ClassNotFoundException {
        if(createTableSet.size() > 0) {
            List<String> finalTableList = DruidUtilsMySQL.getTableList();
            for (String s : createTableSet) {
                if (finalTableList.contains(s.toUpperCase())) {
                    mySQLHelperDruid.doDropTable(s);
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

    @Test(priority = 0, enabled = true, dataProvider = "mysqlDMLInsertData", dataProviderClass = MySQLYamlDataHelper.class, description = "dml操作insert，正向用例")
    public void test01DMLInsert(LinkedHashMap<String,String> param) throws SQLException, IOException {
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
                    tableName = "mysql" + param.get("TestID").trim() + "_0" + i + schemaList.get(i).trim();
                    mySQLHelperDruid.doDropTable(tableName);
                    if (param.get("TestID").contains("txnlsm")) {
                        mySQLHelperDruid.execFile(TestDMLMySQL.class.getClassLoader().getResourceAsStream(mysqlIniReaderTXNLSM.getValue("TableSchema",schemaList.get(i).trim())), tableName);
                    } else if (param.get("TestID").contains("txnbt")) {
                        mySQLHelperDruid.execFile(TestDMLMySQL.class.getClassLoader().getResourceAsStream(mysqlIniReaderTXNBTREE.getValue("TableSchema",schemaList.get(i).trim())), tableName);
                    } else if (param.get("TestID").contains("btree")) {
                        mySQLHelperDruid.execFile(TestDMLMySQL.class.getClassLoader().getResourceAsStream(mysqlIniReaderBTREE.getValue("TableSchema",schemaList.get(i).trim())), tableName);
                    } else {
                        mySQLHelperDruid.execFile(TestDMLMySQL.class.getClassLoader().getResourceAsStream(mysqlIniReader.getValue("TableSchema",schemaList.get(i).trim())), tableName);
                    }
                } else {
                    String schemaName = schemaList.get(i).trim().substring(0,schemaList.get(i).trim().indexOf("_"));
                    tableName = "mysql" + param.get("TestID").trim() + "_0" + i + schemaName;
                    mySQLHelperDruid.doDropTable(tableName);
                    if (param.get("TestID").contains("txnlsm")) {
                        mySQLHelperDruid.execFile(TestDMLMySQL.class.getClassLoader().getResourceAsStream(mysqlIniReaderTXNLSM.getValue("TableSchema",schemaName)), tableName);
                    } else if (param.get("TestID").contains("txnbt")) {
                        mySQLHelperDruid.execFile(TestDMLMySQL.class.getClassLoader().getResourceAsStream(mysqlIniReaderTXNBTREE.getValue("TableSchema",schemaName)), tableName);
                    } else if (param.get("TestID").contains("btree")) {
                        mySQLHelperDruid.execFile(TestDMLMySQL.class.getClassLoader().getResourceAsStream(mysqlIniReaderBTREE.getValue("TableSchema",schemaName)), tableName);
                    } else {
                        mySQLHelperDruid.execFile(TestDMLMySQL.class.getClassLoader().getResourceAsStream(mysqlIniReader.getValue("TableSchema",schemaName)), tableName);
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
                        tableName = "mysql" + param.get("TestID").trim() + "_0" + j + schemaList.get(j).trim();
                    } else {
                        String schemaName = schemaList.get(j).trim().substring(0,schemaList.get(j).trim().indexOf("_"));
                        tableName = "mysql" + param.get("TestID").trim() + "_0" + j + schemaName;
                    }

                    if (param.get("TestID").contains("txnlsm")) {
                        InputStream stream = TestDMLMySQL.class.getClassLoader().getResourceAsStream(mysqlIniReaderTXNLSM.getValue("DMLValues", value_List.get(j).trim()));
                        insertSql = IOUtils.toString(stream, StandardCharsets.UTF_8).replace("$table", tableName);
                    } else if (param.get("TestID").contains("txnbt")) {
                        InputStream stream = TestDMLMySQL.class.getClassLoader().getResourceAsStream(mysqlIniReaderTXNBTREE.getValue("DMLValues", value_List.get(j).trim()));
                        insertSql = IOUtils.toString(stream, StandardCharsets.UTF_8).replace("$table", tableName);
                    } else if (param.get("TestID").contains("btree")) {
                        InputStream stream = TestDMLMySQL.class.getClassLoader().getResourceAsStream(mysqlIniReaderBTREE.getValue("DMLValues", value_List.get(j).trim()));
                        insertSql = IOUtils.toString(stream, StandardCharsets.UTF_8).replace("$table", tableName); 
                    } else {
                        InputStream stream = TestDMLMySQL.class.getClassLoader().getResourceAsStream(mysqlIniReader.getValue("DMLValues", value_List.get(j).trim()));
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
            List<List<String>> actualResult = mySQLHelperDruid.doDMLAndQueryWithHead(insertSql, querySql1);
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
                List<List<String>> actualResultAfterDML = mySQLHelperDruid.doDMLAndQueryWithHead(dmlSql, querySql2);
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
            List<List<String>> actualResult = mySQLHelperDruid.doDMLAndQueryWithHead(insertSql, querySql1);
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
                List<List<String>> actualResultAfterDML = mySQLHelperDruid.doDMLAndQueryWithHead(dmlSql, querySql2);
                System.out.println("Actual after dml: " + actualResultAfterDML);
                Assert.assertTrue(actualResultAfterDML.containsAll(expectedResultAfterDML));
                Assert.assertTrue(expectedResultAfterDML.containsAll(actualResultAfterDML));
            }

//            if (tableList.size() > 0) {
//                for (String s : tableList) {
//                    mySQLHelperDruid.doDropTable(s);
//                }
//            }
        }
    }

    @Test(priority = 1, enabled = true, dataProvider = "mysqlDMLUpDelData", dataProviderClass = MySQLYamlDataHelper.class, description = "dml操作update&delete，正向用例")
    public void test02DMLUpdateDelete(LinkedHashMap<String,String> param) throws SQLException, IOException, InterruptedException {
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
                    tableName = "mysql" + param.get("TestID").trim() + "_0" + i + schemaList.get(i).trim();
                    if (param.get("TestID").contains("txnlsm")) {
                        mySQLHelperDruid.execFile(TestDMLMySQL.class.getClassLoader().getResourceAsStream(mysqlIniReaderTXNLSM.getValue("TableSchema",schemaList.get(i).trim())), tableName);
                    } else if (param.get("TestID").contains("txnbt")) {
                        mySQLHelperDruid.execFile(TestDMLMySQL.class.getClassLoader().getResourceAsStream(mysqlIniReaderTXNBTREE.getValue("TableSchema",schemaList.get(i).trim())), tableName);
                    } else if (param.get("TestID").contains("btree")) {
                        mySQLHelperDruid.execFile(TestDMLMySQL.class.getClassLoader().getResourceAsStream(mysqlIniReaderBTREE.getValue("TableSchema",schemaList.get(i).trim())), tableName);
                    } else {
                        mySQLHelperDruid.execFile(TestDMLMySQL.class.getClassLoader().getResourceAsStream(mysqlIniReader.getValue("TableSchema",schemaList.get(i).trim())), tableName);
                    }
                } else {
                    String schemaName = schemaList.get(i).trim().substring(0,schemaList.get(i).trim().indexOf("_"));
                    tableName = "mysql" + param.get("TestID").trim() + "_0" + i + schemaName;
                    if (param.get("TestID").contains("txnlsm")) {
                        mySQLHelperDruid.execFile(TestDMLMySQL.class.getClassLoader().getResourceAsStream(mysqlIniReaderTXNLSM.getValue("TableSchema",schemaName)), tableName);
                    } else if (param.get("TestID").contains("txnbt")) {
                        mySQLHelperDruid.execFile(TestDMLMySQL.class.getClassLoader().getResourceAsStream(mysqlIniReaderTXNBTREE.getValue("TableSchema",schemaName)), tableName);
                    } else if (param.get("TestID").contains("btree")) {
                        mySQLHelperDruid.execFile(TestDMLMySQL.class.getClassLoader().getResourceAsStream(mysqlIniReaderBTREE.getValue("TableSchema",schemaName)), tableName);
                    } else {
                        mySQLHelperDruid.execFile(TestDMLMySQL.class.getClassLoader().getResourceAsStream(mysqlIniReader.getValue("TableSchema",schemaName)), tableName);
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
                        tableName = "mysql" + param.get("TestID").trim() + "_0" + j + schemaList.get(j).trim();
                    } else {
                        String schemaName = schemaList.get(j).trim().substring(0,schemaList.get(j).trim().indexOf("_"));
                        tableName = "mysql" + param.get("TestID").trim() + "_0" + j + schemaName;
                    }
                    if (param.get("TestID").contains("txnlsm")) {
                        mySQLHelperDruid.execFile(TestDMLMySQL.class.getClassLoader().getResourceAsStream(mysqlIniReaderTXNLSM.getValue("DMLValues", value_List.get(j).trim())), tableName);
                    } else if (param.get("TestID").contains("txnbt")) {
                        mySQLHelperDruid.execFile(TestDMLMySQL.class.getClassLoader().getResourceAsStream(mysqlIniReaderTXNBTREE.getValue("DMLValues", value_List.get(j).trim())), tableName);
                    } else if (param.get("TestID").contains("btree")) {
                        mySQLHelperDruid.execFile(TestDMLMySQL.class.getClassLoader().getResourceAsStream(mysqlIniReaderBTREE.getValue("DMLValues", value_List.get(j).trim())), tableName);
                    } else {
                        mySQLHelperDruid.execFile(TestDMLMySQL.class.getClassLoader().getResourceAsStream(mysqlIniReader.getValue("DMLValues", value_List.get(j).trim())), tableName);
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
            List<List<String>> actualResult = mySQLHelperDruid.doDMLAndQueryWithHead(dmlSql, querySql);
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
            List<List<String>> actualResult = mySQLHelperDruid.doDMLAndQueryWithHead(dmlSql, querySql);
            System.out.println("Actual: " + actualResult);
            Assert.assertTrue(actualResult.containsAll(expectedResult));
            Assert.assertTrue(expectedResult.containsAll(actualResult));
        } else if (param.get("Validation_type").equals("effected_rows_assert")) {
            int expectedEffectedRows = Integer.parseInt(param.get("Effected_rows"));
            System.out.println("Expected effected rows: " + expectedEffectedRows);
            int actualEffectedRows = mySQLHelperDruid.doDMLReturnRows(dmlSql);
            System.out.println("Actual effected rows: " + actualEffectedRows);
            Assert.assertEquals(actualEffectedRows, expectedEffectedRows);
        }

//        if (tableList.size() > 0) {
//            for (String s : tableList) {
//                mySQLHelperDruid.doDropTable(s);
//            }
//        }
    }
}
