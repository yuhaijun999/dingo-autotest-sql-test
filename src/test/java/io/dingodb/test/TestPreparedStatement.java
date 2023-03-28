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
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;

public class TestPreparedStatement extends BaseTestSuite {
    private static SQLHelper sqlHelper;
    private static HashSet<String> createTableSet = new HashSet<>();

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
        createTableSet.clear();
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
    
    @Test(priority = 0, enabled = true, dataProvider = "psDQLData", dataProviderClass = YamlDataHelper.class, description = "验证通过预编译语句进行查询")
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
                    tableName = param.get("TestID").trim() + "_0" + i + schemaList.get(i).trim();
                    sqlHelper.execFile(TestDQLbak.class.getClassLoader().getResourceAsStream(iniReader.getValue("TableSchema",schemaList.get(i).trim())), tableName);
                } else {
                    String schemaName = schemaList.get(i).trim().substring(0,schemaList.get(i).trim().indexOf("_"));
                    tableName = param.get("TestID").trim() + "_0" + i + schemaName;
                    sqlHelper.execFile(TestDQLbak.class.getClassLoader().getResourceAsStream(iniReader.getValue("TableSchema",schemaName)), tableName);
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
                        tableName = param.get("TestID").trim() + "_0" + j + schemaList.get(j).trim();
                    } else {
                        String schemaName = schemaList.get(j).trim().substring(0,schemaList.get(j).trim().indexOf("_"));
                        tableName = param.get("TestID").trim() + "_0" + j + schemaName;
                    }
                    sqlHelper.execFile(TestDQLbak.class.getClassLoader().getResourceAsStream(iniReader.getValue("PSValues", value_List.get(j).trim())), tableName);
                }
            }
        }
        String resultFile = param.get("Expected_result").trim();
        List<List<String>> expectedResult = ParseCsv.splitCsvString(resultFile);
        System.out.println("Expected: " + expectedResult);
        if (param.get("Validation_type").equals("csv_equals")) {
            List<List<String>> actualResult = sqlHelper.preparedStatementQuery(querySql, value_type_tuple, ps_values_tuple);
            System.out.println("Actual: " + actualResult);
            Assert.assertEquals(actualResult, expectedResult);
        } else if (param.get("Validation_type").equals("csv_containsAll")) {
            List<List<String>> actualResult = sqlHelper.preparedStatementQuery(querySql, value_type_tuple, ps_values_tuple);
            System.out.println("Actual: " + actualResult);
            Assert.assertTrue(actualResult.containsAll(expectedResult));
            Assert.assertTrue(expectedResult.containsAll(actualResult));
        }

        if (tableList.size() > 0) {
            for (String s : tableList) {
                sqlHelper.doDropTable(s);
            }
        }
    }

    @Test(priority = 1, enabled = true, dataProvider = "psDMLData", dataProviderClass = YamlDataHelper.class, description = "验证通过预编译语句进行DML操作")
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
                    tableName = param.get("TestID").trim() + "_0" + i + schemaList.get(i).trim();
                    sqlHelper.execFile(TestDQLbak.class.getClassLoader().getResourceAsStream(iniReader.getValue("TableSchema",schemaList.get(i).trim())), tableName);
                } else {
                    String schemaName = schemaList.get(i).trim().substring(0,schemaList.get(i).trim().indexOf("_"));
                    tableName = param.get("TestID").trim() + "_0" + i + schemaName;
                    sqlHelper.execFile(TestDQLbak.class.getClassLoader().getResourceAsStream(iniReader.getValue("TableSchema",schemaName)), tableName);
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
                    sqlHelper.execFile(TestDQLbak.class.getClassLoader().getResourceAsStream(iniReader.getValue("PSValues", value_List.get(j).trim())), tableName);
                }
            }
        }
        if (param.get("Validation_type").equals("csv_equals")) {
            String resultFile = param.get("Expected_result").trim();
            List<List<String>> expectedResult = ParseCsv.splitCsvString(resultFile);
            System.out.println("Expected: " + expectedResult);
            List<List<String>> actualResult = sqlHelper.preparedStatementDMLGetData(dmlSql, querySql, value_type_tuple, ps_values_tuple);
            System.out.println("Actual: " + actualResult);
            Assert.assertEquals(actualResult, expectedResult);
        } else if (param.get("Validation_type").equals("csv_containsAll")) {
            String resultFile = param.get("Expected_result").trim();
            List<List<String>> expectedResult = ParseCsv.splitCsvString(resultFile);
            System.out.println("Expected: " + expectedResult);
            List<List<String>> actualResult = sqlHelper.preparedStatementDMLGetData(dmlSql, querySql, value_type_tuple, ps_values_tuple);
            System.out.println("Actual: " + actualResult);
            Assert.assertTrue(actualResult.containsAll(expectedResult));
            Assert.assertTrue(expectedResult.containsAll(actualResult));
        } else if (param.get("Validation_type").equals("effected_rows_assert")) {
            int expectedEffectedRows = Integer.parseInt(param.get("Effected_rows"));
            System.out.println("Expected effected rows: " + expectedEffectedRows);
            int actualEffectedRows = sqlHelper.preparedStatementDMLGetRows(dmlSql, value_type_tuple, ps_values_tuple);
            System.out.println("Actual effected rows: " + actualEffectedRows);
            Assert.assertEquals(actualEffectedRows, expectedEffectedRows);
        }

        if (tableList.size() > 0) {
            for (String s : tableList) {
                sqlHelper.doDropTable(s);
            }
        }
    }

    @Test(priority = 2, enabled = true, dataProvider = "psBatchData", dataProviderClass = YamlDataHelper.class, description = "验证通过prepareStatement进行批量插入数据")
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
//            List<String> tableList = new ArrayList<>();
            List<String> schemaList = CastUtils.construct1DListIncludeBlank(param.get("Table_schema_ref"),",");
            for (int i = 0; i < schemaList.size(); i++) {
                String tableName = "";
                if (!schemaList.get(i).trim().contains("_")) {
                    tableName = param.get("TestID").trim() + "_0" + i + schemaList.get(i).trim();
                    sqlHelper.execFile(TestDQLbak.class.getClassLoader().getResourceAsStream(iniReader.getValue("TableSchema",schemaList.get(i).trim())), tableName);
                } else {
                    String schemaName = schemaList.get(i).trim().substring(0,schemaList.get(i).trim().indexOf("_"));
                    tableName = param.get("TestID").trim() + "_0" + i + schemaName;
                    sqlHelper.execFile(TestDQLbak.class.getClassLoader().getResourceAsStream(iniReader.getValue("TableSchema",schemaName)), tableName);
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
                        tableName = param.get("TestID").trim() + "_0" + j + schemaList.get(j).trim();
                    } else {
                        String schemaName = schemaList.get(j).trim().substring(0,schemaList.get(j).trim().indexOf("_"));
                        tableName = param.get("TestID").trim() + "_0" + j + schemaName;
                    }
                    sqlHelper.execFile(TestDQLbak.class.getClassLoader().getResourceAsStream(iniReader.getValue("PSValues", value_List.get(j).trim())), tableName);
                }
            }
        }
        sqlHelper.preparedStatementBatchInsert(
                insertSql,200000, value_type_tuple,6,
                100,0,10000,2, -1000000,1000000,2, 
                "yyyy-MM-dd","1970-10-01","2022-03-31","HH:mm:ss",
                "00:00:00","23:59:59","yyyy-MM-dd HH:mm:ss", 
                "1990-01-01 00:00:00","2023-03-21 23:59:59"
        );
        Thread.sleep(60000);
       if (param.get("Validation_type").equals("effected_rows_assert")) {
           int expectedQuery1Rows = Integer.parseInt(param.get("Query_result1"));
           System.out.println("Expected query1 rows: " + expectedQuery1Rows);
           int actualQuery1Rows = Integer.parseInt(sqlHelper.queryWithStrReturn(querySql1));
           System.out.println("Actual query1 rows: " + actualQuery1Rows);
           Assert.assertEquals(actualQuery1Rows, expectedQuery1Rows);

           int expectedQuery2Rows = Integer.parseInt(param.get("Query_result2"));
           System.out.println("Expected query2 rows: " + expectedQuery2Rows);
           int actualQuery2Rows = Integer.parseInt(sqlHelper.queryWithStrReturn(querySql2));
           System.out.println("Actual query2 rows: " + actualQuery2Rows);
           Assert.assertEquals(actualQuery2Rows, expectedQuery2Rows);

           int expectedDml1Rows = Integer.parseInt(param.get("Dml_result1"));
           System.out.println("Expected dml1 rows: " + expectedDml1Rows);
           int actualDml1Rows = sqlHelper.doDMLReturnRows(dmlSql1);
           System.out.println("Actual dml1 rows: " + actualDml1Rows);
           Assert.assertEquals(actualDml1Rows, expectedDml1Rows);

           int expectedQuery3Rows = Integer.parseInt(param.get("Query_result3"));
           System.out.println("Expected query3 rows: " + expectedQuery3Rows);
           int actualQuery3Rows = Integer.parseInt(sqlHelper.queryWithStrReturn(querySql3));
           System.out.println("Actual query3 rows: " + actualQuery3Rows);
           Assert.assertEquals(actualQuery3Rows, expectedQuery3Rows);

           int expectedDml2Rows = Integer.parseInt(param.get("Dml_result2"));
           System.out.println("Expected dml2 rows: " + expectedDml2Rows);
           int actualDml2Rows = sqlHelper.doDMLReturnRows(dmlSql2);
           System.out.println("Actual dml2 rows: " + actualDml2Rows);
           Assert.assertEquals(actualDml2Rows, expectedDml2Rows);
           
           int expectedQuery4Rows = Integer.parseInt(param.get("Query_result4"));
           System.out.println("Expected query4 rows: " + expectedQuery4Rows);
           int actualQuery4Rows = Integer.parseInt(sqlHelper.queryWithStrReturn(querySql4));
           System.out.println("Actual query4 rows: " + actualQuery4Rows);
           Assert.assertEquals(actualQuery4Rows, expectedQuery4Rows);
        }

        if (tableList.size() > 0) {
            for (String s : tableList) {
                sqlHelper.doDropTable(s);
            }
        }
    }
}
