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
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.CastUtils;
import utils.ParseCsv;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;

public class TestPreparedStatementMySQL extends BaseTestSuiteMySQL {
    private static MySQLHelper mySQLHelper;
    public static Connection myConnection;
    private static HashSet<String> createTableSet = new HashSet<>();

    @BeforeClass (alwaysRun = true)
    public static void setupAll() throws SQLException, IOException, ClassNotFoundException {
        mySQLHelper = new MySQLHelper();
        MySQLUtils mySQLUtils = new MySQLUtils();
        myConnection = mySQLUtils.getMySQLConnectionInstance();
        Assert.assertNotNull(myConnection);
    }

    @AfterClass (alwaysRun = true)
    public static void tearDownAll() throws SQLException, IOException, ClassNotFoundException {
//        System.out.println(createTableSet);
        if(createTableSet.size() > 0) {
            List<String> finalTableList = MySQLUtils.getTableList();
            for (String s : createTableSet) {
                if (finalTableList.contains(s.toUpperCase())) {
                    mySQLHelper.doDropTable(myConnection, s);
                }
            }
        }
        createTableSet.clear();
        MySQLUtils.closeResource(myConnection);
    }

    @BeforeMethod (alwaysRun = true)
    public void setup() throws Exception {
    }

    @AfterMethod (alwaysRun = true)
    public void cleanUp() throws Exception {
    }
    
    @Test(priority = 0, enabled = true, dataProvider = "mysqlPSDQLData", dataProviderClass = MySQLYamlDataHelper.class, description = "验证通过预编译语句进行查询")
    public void test01PrepareStatementDQL(LinkedHashMap<String,String> param) throws SQLException, IOException {
        if (param.get("Testable").trim().equals("n") || param.get("Testable").trim().equals("N")) {
            throw new SkipException("skip this test case");
        }

        List<String> tableList = new ArrayList<>();
        String querySql = param.get("Query_sql").trim();
        Object[] ps_values_tuple = param.get("PS_values").split(",");
        String[] value_type_tuple = param.get("Value_type").split(",");
        
        if (param.get("Table_schema_ref").trim().length() > 0) {
//            List<String> tableList = new ArrayList<>();
            List<String> schemaList = CastUtils.construct1DListIncludeBlank(param.get("Table_schema_ref"),",");
            for (int i = 0; i < schemaList.size(); i++) {
                String tableName = "";
                if (!schemaList.get(i).trim().contains("_")) {
                    tableName = "mysql" + param.get("TestID").trim() + "_0" + i + schemaList.get(i).trim();
                    if (param.get("TestID").contains("btree")) {
                        mySQLHelper.execFile(myConnection, TestPreparedStatementMySQL.class.getClassLoader().getResourceAsStream(mysqlIniReaderBTREE.getValue("TableSchema",schemaList.get(i).trim())), tableName);
                    } else {
                        mySQLHelper.execFile(myConnection, TestPreparedStatementMySQL.class.getClassLoader().getResourceAsStream(mysqlIniReader.getValue("TableSchema",schemaList.get(i).trim())), tableName);
                    }
                } else {
                    String schemaName = schemaList.get(i).trim().substring(0,schemaList.get(i).trim().indexOf("_"));
                    tableName = "mysql" + param.get("TestID").trim() + "_0" + i + schemaName;
                    if (param.get("TestID").contains("btree")) {
                        mySQLHelper.execFile(myConnection, TestPreparedStatementMySQL.class.getClassLoader().getResourceAsStream(mysqlIniReaderBTREE.getValue("TableSchema",schemaName)), tableName);
                    } else {
                        mySQLHelper.execFile(myConnection, TestPreparedStatementMySQL.class.getClassLoader().getResourceAsStream(mysqlIniReader.getValue("TableSchema",schemaName)), tableName);
                    }
                }
                tableList.add(tableName);
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
                    if (param.get("TestID").contains("btree")) {
                        mySQLHelper.execFile(myConnection, TestPreparedStatementMySQL.class.getClassLoader().getResourceAsStream(mysqlIniReaderBTREE.getValue("PSValues", value_List.get(j).trim())), tableName);
                    } else {
                        mySQLHelper.execFile(myConnection, TestPreparedStatementMySQL.class.getClassLoader().getResourceAsStream(mysqlIniReader.getValue("PSValues", value_List.get(j).trim())), tableName);
                    }
                }
            }
        }
        String resultFile = param.get("Expected_result").trim();
        List<List<String>> expectedResult = new ArrayList<>();
        if (!param.get("Component").equalsIgnoreCase("ComplexDataType")){
            expectedResult = ParseCsv.splitCsvString(resultFile,",");
        } else {
            expectedResult = ParseCsv.splitCsvString(resultFile,"&");
        }
        System.out.println("Expected: " + expectedResult);
        if (param.get("Validation_type").equals("csv_equals")) {
            List<List<String>> actualResult = mySQLHelper.preparedStatementQuery(myConnection, querySql, value_type_tuple, ps_values_tuple);
            System.out.println("Actual: " + actualResult);
            Assert.assertEquals(actualResult, expectedResult);
        } else if (param.get("Validation_type").equals("csv_containsAll")) {
            List<List<String>> actualResult = mySQLHelper.preparedStatementQuery(myConnection, querySql, value_type_tuple, ps_values_tuple);
            System.out.println("Actual: " + actualResult);
            Assert.assertTrue(actualResult.containsAll(expectedResult));
            Assert.assertTrue(expectedResult.containsAll(actualResult));
        }

//        if (tableList.size() > 0) {
//            for (String s : tableList) {
//                mySQLHelper.doDropTable(s);
//            }
//        }
    }

    @Test(priority = 1, enabled = true, dataProvider = "mysqlPSDMLData", dataProviderClass = MySQLYamlDataHelper.class, description = "验证通过预编译语句进行DML操作")
    public void test02PrepareStatementDML(LinkedHashMap<String,String> param) throws SQLException, IOException {
        if (param.get("Testable").trim().equals("n") || param.get("Testable").trim().equals("N")) {
            throw new SkipException("skip this test case");
        }
        
        List<String> tableList = new ArrayList<>();
        String dmlSql = param.get("Dml_sql").trim();
        String querySql = param.get("Query_sql").trim();
        Object[] ps_values_tuple = param.get("PS_values").split(",");
        String[] value_type_tuple = param.get("Value_type").split(",");

        if (param.get("Table_schema_ref").trim().length() > 0) {
//            List<String> tableList = new ArrayList<>();
            List<String> schemaList = CastUtils.construct1DListIncludeBlank(param.get("Table_schema_ref"),",");
            for (int i = 0; i < schemaList.size(); i++) {
                String tableName = "";
                if (!schemaList.get(i).trim().contains("_")) {
                    tableName = "mysql" + param.get("TestID").trim() + "_0" + i + schemaList.get(i).trim();
                    if (param.get("TestID").contains("btree")) {
                        mySQLHelper.execFile(myConnection, TestPreparedStatementMySQL.class.getClassLoader().getResourceAsStream(mysqlIniReaderBTREE.getValue("TableSchema",schemaList.get(i).trim())), tableName);
                    } else {
                        mySQLHelper.execFile(myConnection, TestPreparedStatementMySQL.class.getClassLoader().getResourceAsStream(mysqlIniReader.getValue("TableSchema",schemaList.get(i).trim())), tableName);
                    }
                } else {
                    String schemaName = schemaList.get(i).trim().substring(0,schemaList.get(i).trim().indexOf("_"));
                    tableName = "mysql" + param.get("TestID").trim() + "_0" + i + schemaName;
                    if (param.get("TestID").contains("btree")) {
                        mySQLHelper.execFile(myConnection, TestPreparedStatementMySQL.class.getClassLoader().getResourceAsStream(mysqlIniReaderBTREE.getValue("TableSchema",schemaName)), tableName);
                    } else {
                        mySQLHelper.execFile(myConnection, TestPreparedStatementMySQL.class.getClassLoader().getResourceAsStream(mysqlIniReader.getValue("TableSchema",schemaName)), tableName);
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
                    if (param.get("TestID").contains("btree")) {
                        mySQLHelper.execFile(myConnection, TestPreparedStatementMySQL.class.getClassLoader().getResourceAsStream(mysqlIniReaderBTREE.getValue("PSValues", value_List.get(j).trim())), tableName);
                    } else {
                        mySQLHelper.execFile(myConnection, TestPreparedStatementMySQL.class.getClassLoader().getResourceAsStream(mysqlIniReader.getValue("PSValues", value_List.get(j).trim())), tableName);
                    }
                }
            }
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
            List<List<String>> actualResult = mySQLHelper.preparedStatementDMLGetData(myConnection, dmlSql, querySql, value_type_tuple, ps_values_tuple);
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
            List<List<String>> actualResult = mySQLHelper.preparedStatementDMLGetData(myConnection, dmlSql, querySql, value_type_tuple, ps_values_tuple);
            System.out.println("Actual: " + actualResult);
            Assert.assertTrue(actualResult.containsAll(expectedResult));
            Assert.assertTrue(expectedResult.containsAll(actualResult));
        } else if (param.get("Validation_type").equals("effected_rows_assert")) {
            int expectedEffectedRows = Integer.parseInt(param.get("Effected_rows"));
            System.out.println("Expected effected rows: " + expectedEffectedRows);
            int actualEffectedRows = mySQLHelper.preparedStatementDMLGetRows(myConnection, dmlSql, value_type_tuple, ps_values_tuple);
            System.out.println("Actual effected rows: " + actualEffectedRows);
            Assert.assertEquals(actualEffectedRows, expectedEffectedRows);
        }

//        if (tableList.size() > 0) {
//            for (String s : tableList) {
//                mySQLHelper.doDropTable(s);
//            }
//        }
    }

    @Test(priority = 2, enabled = true, dataProvider = "mysqlPSBatchData", dataProviderClass = MySQLYamlDataHelper.class, description = "验证通过prepareStatement进行批量插入数据")
    public void test03PrepareStatementBatchInsert(LinkedHashMap<String,String> param) throws SQLException, IOException, ParseException, InterruptedException {
        if (param.get("Testable").trim().equals("n") || param.get("Testable").trim().equals("N")) {
            throw new SkipException("skip this test case");
        }

        List<String> tableList = new ArrayList<>();
        String insertSql = param.get("Insert_sql").trim();
        String querySql1 = param.get("Query_sql1").trim();
        String querySql2 = param.get("Query_sql2").trim();
        String querySql3 = param.get("Query_sql3").trim();
        String querySql4 = param.get("Query_sql4").trim();
        String dmlSql1 = param.get("Dml_sql1").trim();
        String dmlSql2 = param.get("Dml_sql2").trim();
        String[] value_type_tuple = param.get("Value_type").split(",");

        if (param.get("Table_schema_ref").trim().length() > 0) {
            List<String> schemaList = CastUtils.construct1DListIncludeBlank(param.get("Table_schema_ref"),",");
            for (int i = 0; i < schemaList.size(); i++) {
                String tableName = "";
                if (!schemaList.get(i).trim().contains("_")) {
                    tableName = "mysql" + param.get("TestID").trim() + "_0" + i + schemaList.get(i).trim();
                    if (param.get("TestID").contains("btree")) {
                        mySQLHelper.execFile(myConnection, TestPreparedStatementMySQL.class.getClassLoader().getResourceAsStream(mysqlIniReaderBTREE.getValue("TableSchema",schemaList.get(i).trim())), tableName);
                    } else {
                        mySQLHelper.execFile(myConnection, TestPreparedStatementMySQL.class.getClassLoader().getResourceAsStream(mysqlIniReader.getValue("TableSchema",schemaList.get(i).trim())), tableName);
                    }
                } else {
                    String schemaName = schemaList.get(i).trim().substring(0,schemaList.get(i).trim().indexOf("_"));
                    tableName = "mysql" + param.get("TestID").trim() + "_0" + i + schemaName;
                    if (param.get("TestID").contains("btree")) {
                        mySQLHelper.execFile(myConnection, TestPreparedStatementMySQL.class.getClassLoader().getResourceAsStream(mysqlIniReaderBTREE.getValue("TableSchema",schemaName)), tableName);
                    } else {
                        mySQLHelper.execFile(myConnection, TestPreparedStatementMySQL.class.getClassLoader().getResourceAsStream(mysqlIniReader.getValue("TableSchema",schemaName)), tableName);
                    }
                }
                tableList.add(tableName);
                insertSql = insertSql.replace("$" + schemaList.get(i).trim(), tableName);
                querySql1 = querySql1.replace("$" + schemaList.get(i).trim(), tableName);
                querySql2 = querySql2.replace("$" + schemaList.get(i).trim(), tableName);
                querySql3 = querySql3.replace("$" + schemaList.get(i).trim(), tableName);
                querySql4 = querySql4.replace("$" + schemaList.get(i).trim(), tableName);
                dmlSql1 = dmlSql1.replace("$" + schemaList.get(i).trim(), tableName);
                dmlSql2 = dmlSql2.replace("$" + schemaList.get(i).trim(), tableName);
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
                    if (param.get("TestID").contains("btree")) {
                        mySQLHelper.execFile(myConnection, TestPreparedStatementMySQL.class.getClassLoader().getResourceAsStream(mysqlIniReaderBTREE.getValue("PSValues", value_List.get(j).trim())), tableName);
                    } else {
                        mySQLHelper.execFile(myConnection, TestPreparedStatementMySQL.class.getClassLoader().getResourceAsStream(mysqlIniReader.getValue("PSValues", value_List.get(j).trim())), tableName);
                    }
                }
            }
        }
        mySQLHelper.preparedStatementBatchInsert(myConnection, 
                insertSql,Integer.parseInt(param.get("Insert_count")), value_type_tuple,6,
                100,0,10000,2, -1000000,1000000,2, 
                "yyyy-MM-dd","1970-10-01","2022-03-31","HH:mm:ss",
                "00:00:00","23:59:59","yyyy-MM-dd HH:mm:ss", 
                "1990-01-01 00:00:00","2023-03-21 23:59:59"
        );
        Thread.sleep(10000);
       if (param.get("Validation_type").equals("effected_rows_assert")) {
           int expectedQuery1Rows = Integer.parseInt(param.get("Query_result1"));
           System.out.println("Expected query1 rows: " + expectedQuery1Rows);
           int actualQuery1Rows = Integer.parseInt(mySQLHelper.queryWithStrReturn(myConnection, querySql1));
           System.out.println("Actual query1 rows: " + actualQuery1Rows);
           Assert.assertEquals(actualQuery1Rows, expectedQuery1Rows);

           int expectedQuery2Rows = Integer.parseInt(param.get("Query_result2"));
           System.out.println("Expected query2 rows: " + expectedQuery2Rows);
           int actualQuery2Rows = Integer.parseInt(mySQLHelper.queryWithStrReturn(myConnection, querySql2));
           System.out.println("Actual query2 rows: " + actualQuery2Rows);
           Assert.assertEquals(actualQuery2Rows, expectedQuery2Rows);

           int expectedDml1Rows = Integer.parseInt(param.get("Dml_result1"));
           System.out.println("Expected dml1 rows: " + expectedDml1Rows);
           int actualDml1Rows = mySQLHelper.doDMLReturnRows(myConnection, dmlSql1);
           System.out.println("Actual dml1 rows: " + actualDml1Rows);
           Assert.assertEquals(actualDml1Rows, expectedDml1Rows);

           int expectedQuery3Rows = Integer.parseInt(param.get("Query_result3"));
           System.out.println("Expected query3 rows: " + expectedQuery3Rows);
           int actualQuery3Rows = Integer.parseInt(mySQLHelper.queryWithStrReturn(myConnection, querySql3));
           System.out.println("Actual query3 rows: " + actualQuery3Rows);
           Assert.assertEquals(actualQuery3Rows, expectedQuery3Rows);

           int expectedDml2Rows = Integer.parseInt(param.get("Dml_result2"));
           System.out.println("Expected dml2 rows: " + expectedDml2Rows);
           int actualDml2Rows = mySQLHelper.doDMLReturnRows(myConnection, dmlSql2);
           System.out.println("Actual dml2 rows: " + actualDml2Rows);
           Assert.assertEquals(actualDml2Rows, expectedDml2Rows);
           
           int expectedQuery4Rows = Integer.parseInt(param.get("Query_result4"));
           System.out.println("Expected query4 rows: " + expectedQuery4Rows);
           int actualQuery4Rows = Integer.parseInt(mySQLHelper.queryWithStrReturn(myConnection, querySql4));
           System.out.println("Actual query4 rows: " + actualQuery4Rows);
           Assert.assertEquals(actualQuery4Rows, expectedQuery4Rows);
        }

//        if (tableList.size() > 0) {
//            for (String s : tableList) {
//                mySQLHelper.doDropTable(s);
//            }
//        }
    }

    @Test(priority = 3, enabled = true, dataProvider = "mysqlPSBlobData", dataProviderClass = MySQLYamlDataHelper.class, description = "验证Blob类型的输入与输出")
    public void test04PrepareStatementBlobType(LinkedHashMap<String,String> param) throws SQLException, IOException {
        if (param.get("Testable").trim().equals("n") || param.get("Testable").trim().equals("N")) {
            throw new SkipException("skip this test case");
        }

        List<String> tableList = new ArrayList<>();
        String insertSql = param.get("Insert_sql").trim();
        String querySql = param.get("Query_sql").trim();
        List<Object> insert_ps_values_list = CastUtils.construct1DListIncludeBlankChangeable(param.get("Insert_ps_values"), ",");
        String[] insert_value_type_tuple = param.get("Insert_value_type").split(",");
        Object[] query_ps_values_tuple = param.get("Query_ps_values").split(",");
        String[] query_value_type_tuple = param.get("Query_value_type").split(",");

        if (param.get("Table_schema_ref").trim().length() > 0) {
            List<String> schemaList = CastUtils.construct1DListIncludeBlank(param.get("Table_schema_ref"),",");
            for (int i = 0; i < schemaList.size(); i++) {
                String tableName = "";
                if (!schemaList.get(i).trim().contains("_")) {
                    if (param.get("Case_table_dependency").trim().length() > 0) {
                        tableName = "mysql" + param.get("Case_table_dependency").trim() + "_0" + i + schemaList.get(i).trim();
                    } else {
                        tableName = "mysql" + param.get("TestID").trim() + "_0" + i + schemaList.get(i).trim();
                    }
                    if (param.get("TestID").contains("btree")) {
                        mySQLHelper.execFile(myConnection, TestPreparedStatementMySQL.class.getClassLoader().getResourceAsStream(mysqlIniReaderBTREE.getValue("TableSchema",schemaList.get(i).trim())), tableName);
                    } else {
                        mySQLHelper.execFile(myConnection, TestPreparedStatementMySQL.class.getClassLoader().getResourceAsStream(mysqlIniReader.getValue("TableSchema",schemaList.get(i).trim())), tableName);
                    }
                } else {
                    String schemaName = schemaList.get(i).trim().substring(0,schemaList.get(i).trim().indexOf("_"));
                    if (param.get("Case_table_dependency").trim().length() > 0) {
                        tableName = "mysql" + param.get("Case_table_dependency").trim() + "_0" + i + schemaName;
                    } else {
                        tableName = "mysql" + param.get("TestID").trim() + "_0" + i + schemaName;
                    }
                    if (param.get("TestID").contains("btree")) {
                        mySQLHelper.execFile(myConnection, TestPreparedStatementMySQL.class.getClassLoader().getResourceAsStream(mysqlIniReaderBTREE.getValue("TableSchema",schemaName)), tableName);
                    } else {
                        mySQLHelper.execFile(myConnection, TestPreparedStatementMySQL.class.getClassLoader().getResourceAsStream(mysqlIniReader.getValue("TableSchema",schemaName)), tableName);
                    }
                }
                tableList.add(tableName);
                insertSql = insertSql.replace("$" + schemaList.get(i).trim(), tableName);
                querySql = querySql.replace("$" + schemaList.get(i).trim(), tableName);
            }
            createTableSet.addAll(tableList);
        }
        
        if (param.get("Blob_resource").trim().length() > 0) {
            String blobResource = null;
            if (param.get("TestID").contains("btree")) {
                blobResource = mysqlIniReaderBTREE.getValue("PSValues", param.get("Blob_resource"));
            } else {
                blobResource = mysqlIniReader.getValue("PSValues", param.get("Blob_resource"));
            }
            System.out.println("blob resource path: " + blobResource);
            FileInputStream fileInputStream = new FileInputStream(blobResource);
            File fileIn = new File(blobResource);
            long fileInSize = fileIn.length();
            System.out.println("输入文件大小：" + fileInSize);
            insert_ps_values_list.set(insert_ps_values_list.size() - 1, fileInputStream);
            int expectedInsertRows = Integer.parseInt(param.get("Effected_rows"));
            System.out.println("Expected insert rows: " + expectedInsertRows);
            int actualInsertRows = mySQLHelper.preparedStatementInsertBlobData(myConnection, insertSql, insert_value_type_tuple, insert_ps_values_list);
            System.out.println("Actual insert rows: " + actualInsertRows);
            Assert.assertEquals(actualInsertRows, expectedInsertRows);
            int blobIndex = 0;
            for (int i = 0; i < insert_value_type_tuple.length; i++) {
                if (insert_value_type_tuple[i].equalsIgnoreCase("Blob")) {
                    blobIndex = i + 1;
                }
            }
            
            String fileOutPutPath = param.get("Blob_out") + "mysql" + param.get("TestID") + "." + param.get("Resource_type");
            File oldFile = new File(fileOutPutPath);
            if (oldFile.exists()) {
                oldFile.delete();
            }
            
            FileOutputStream actualFileOutputStream = mySQLHelper.preparedStatementGetBlobData(myConnection, querySql, query_value_type_tuple, blobIndex, fileOutPutPath, query_ps_values_tuple);
            Assert.assertNotNull(actualFileOutputStream);
            File fileOut = new File(fileOutPutPath);
            long fileOutSize = fileOut.length();
            System.out.println("输出文件大小：" + fileOutSize);
            
            Assert.assertEquals(fileOutSize, fileInSize);
            
            if (fileOut.exists()) {
                fileOut.delete();
            }
        }
    }
    
}
