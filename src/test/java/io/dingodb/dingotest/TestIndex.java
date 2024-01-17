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
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;

public class TestIndex extends BaseTestSuite {
    private static SQLHelper sqlHelper;
    public static Connection connection;
    private static HashSet<String> createTableSet = new HashSet<>();
    
    @BeforeClass (alwaysRun = true)
    public void setupAll() throws SQLException, ClassNotFoundException {
        sqlHelper = new SQLHelper();
        JDBCUtils jdbcUtils = new JDBCUtils();
        connection = jdbcUtils.getDingoConnectionInstance();
        Assert.assertNotNull(connection);
    }

    @AfterClass (alwaysRun = true)
    public void tearDownAll() throws SQLException, IOException, ClassNotFoundException {
        System.out.println("Create table set: " + createTableSet);
        if(createTableSet.size() > 0) {
            List<String> finalTableList = JDBCUtils.getTableList();
            System.out.println("Get table list: " + finalTableList);
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

    @Test(priority = 0, enabled = true, dataProvider = "indexData1", dataProviderClass = YamlDataHelper.class, description = "标量和向量索引测试")
    public void testIndex1(LinkedHashMap<String,String> param) throws SQLException, IOException, InterruptedException, ClassNotFoundException {
//        JDBCUtils jdbcUtils = new JDBCUtils();
//        Connection connection = jdbcUtils.getDingoConnectionInstance();
        
        if (param.get("Testable").trim().equals("n") || param.get("Testable").trim().equals("N")) {
            throw new SkipException("skip this test case");
        }
        
        List<String> tableList = new ArrayList<>();
        String sql = param.get("Sql_state").trim();
        if (param.get("Table_schema_ref").trim().length() > 0) {
            List<String> schemaList = CastUtils.construct1DListIncludeBlank(param.get("Table_schema_ref"),",");
            for (int i = 0; i < schemaList.size(); i++) {
                String tableName = "";
                if (!schemaList.get(i).trim().contains("_")) {
                    if (param.get("Case_table_dependency").trim().length() > 0) {
                        tableName = param.get("Case_table_dependency").trim() + "_0" + i + schemaList.get(i).trim();
                        sql = sql.replace("$" + schemaList.get(i).trim(), tableName);
                        tableList.add(tableName);
                    } else {
                        tableName = param.get("TestID").trim() + "_0" + i + schemaList.get(i).trim();
                        if (param.get("TestID").contains("txnbt")) {
                            sqlHelper.execFile(connection, TestIndex.class.getClassLoader().getResourceAsStream(iniReaderTXNBTREE.getValue("TableSchema",schemaList.get(i).trim())), tableName);
                        } else if (param.get("TestID").contains("btree")) {
                            sqlHelper.execFile(connection, TestIndex.class.getClassLoader().getResourceAsStream(iniReaderBTREE.getValue("TableSchema",schemaList.get(i).trim())), tableName);
                        } else {
                            sqlHelper.execFile(connection, TestIndex.class.getClassLoader().getResourceAsStream(iniReader.getValue("TableSchema",schemaList.get(i).trim())), tableName);
                        }
                        tableList.add(tableName);
                        sql = sql.replace("$" + schemaList.get(i).trim(), tableName);
                    }
                } else {
                    String schemaName = schemaList.get(i).trim().substring(0,schemaList.get(i).trim().indexOf("_"));
                    if (param.get("Case_table_dependency").trim().length() > 0) {
                        tableName = param.get("Case_table_dependency").trim() + "_0" + i + schemaName;
                        sql = sql.replace("$" + schemaList.get(i).trim(), tableName);
                        tableList.add(tableName);
                    } else {
                        tableName = param.get("TestID").trim() + "_0" + i + schemaName;
                        if (param.get("TestID").contains("txnbt")) {
                            sqlHelper.execFile(connection, TestIndex.class.getClassLoader().getResourceAsStream(iniReaderTXNBTREE.getValue("TableSchema",schemaName)), tableName);
                        } else if (param.get("TestID").contains("btree")) {
                            sqlHelper.execFile(connection, TestIndex.class.getClassLoader().getResourceAsStream(iniReaderBTREE.getValue("TableSchema",schemaName)), tableName);
                        } else {
                            sqlHelper.execFile(connection, TestIndex.class.getClassLoader().getResourceAsStream(iniReader.getValue("TableSchema",schemaName)), tableName);
                        }
                        tableList.add(tableName);
                        sql = sql.replace("$" + schemaList.get(i).trim(), tableName);
                    }
                }
            }
            createTableSet.addAll(tableList);
            if (param.get("Case_table_dependency").trim().length() == 0) {
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

                        if (param.get("TestID").contains("txnbt")) {
                            sqlHelper.execFile(connection, TestIndex.class.getClassLoader().getResourceAsStream(iniReaderTXNBTREE.getValue("IndexValues", value_List.get(j).trim())), tableName);
                        } else if (param.get("TestID").contains("btree")) {
                            sqlHelper.execFile(connection, TestIndex.class.getClassLoader().getResourceAsStream(iniReaderBTREE.getValue("IndexValues", value_List.get(j).trim())), tableName);
                        } else {
                            sqlHelper.execFile(connection, TestIndex.class.getClassLoader().getResourceAsStream(iniReader.getValue("IndexValues", value_List.get(j).trim())), tableName);
                        }
                    }
                }
            }
            if (param.get("Case_table_dependency").trim().length() > 0) {
                if (param.get("Table_value_ref").trim().length() > 0) {
                    List<String> value_List = CastUtils.construct1DListIncludeBlank(param.get("Table_value_ref").trim(),",");
                    for (int j = 0; j < value_List.size(); j++) {
                        String tableName = param.get("Case_table_dependency").trim() + "_0" + j + schemaList.get(j).trim();
                        if (param.get("TestID").contains("txnbt")) {
                            sqlHelper.execFile(connection, TestIndex.class.getClassLoader().getResourceAsStream(iniReaderTXNBTREE.getValue("IndexValues", value_List.get(j).trim())), tableName);
                        } else if (param.get("TestID").contains("btree")) {
                            sqlHelper.execFile(connection, TestIndex.class.getClassLoader().getResourceAsStream(iniReaderBTREE.getValue("IndexValues", value_List.get(j).trim())), tableName);
                        } else {
                            sqlHelper.execFile(connection, TestIndex.class.getClassLoader().getResourceAsStream(iniReader.getValue("IndexValues", value_List.get(j).trim())), tableName);
                        }
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
            List<List<String>> actualResult = sqlHelper.statementQueryWithHead(connection, sql);
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
            List<List<String>> actualResult = sqlHelper.statementQueryWithHead(connection, sql);
            System.out.println("Actual: " + actualResult);
            Assert.assertTrue(actualResult.containsAll(expectedResult));
            Assert.assertTrue(expectedResult.containsAll(actualResult));
        } else if (param.get("Validation_type").equalsIgnoreCase("similarity")) {
            String resultFile = param.get("Expected_result").trim();
            List<List<String>> expectedResult = new ArrayList<>();
            if (!param.get("Component").equalsIgnoreCase("ComplexDataType")){
                expectedResult = ParseCsv.splitCsvString(resultFile,",");
            } else {
                expectedResult = ParseCsv.splitCsvString(resultFile,"&");
            }
            System.out.println("Expected: " + expectedResult);
            List<List<String>> actualResult = sqlHelper.statementQueryWithHead(connection, sql);
            System.out.println("Actual: " + actualResult);
            Assert.assertTrue(assertSimilarity(actualResult, expectedResult, param.get("Sub_component")));
        } else if (param.get("Validation_type").equalsIgnoreCase("similarityId")) {
            String resultFile = param.get("Expected_result").trim();
            List<List<String>> expectedResult = new ArrayList<>();
            if (!param.get("Component").equalsIgnoreCase("ComplexDataType")){
                expectedResult = ParseCsv.splitCsvString(resultFile,",");
            } else {
                expectedResult = ParseCsv.splitCsvString(resultFile,"&");
            }
            System.out.println("Expected: " + expectedResult);
            List<List<String>> actualResult = sqlHelper.statementQueryWithHead(connection, sql);
            System.out.println("Actual: " + actualResult);
            Assert.assertTrue(assertSimilarityID(actualResult, expectedResult, param.get("Sub_component")));
        } else if (param.get("Validation_type").equalsIgnoreCase("similarityDistance")) {
            String resultFile = param.get("Expected_result").trim();
            List<List<String>> expectedResult = new ArrayList<>();
            if (!param.get("Component").equalsIgnoreCase("ComplexDataType")){
                expectedResult = ParseCsv.splitCsvString(resultFile,",");
            } else {
                expectedResult = ParseCsv.splitCsvString(resultFile,"&");
            }
            System.out.println("Expected: " + expectedResult);
            List<List<String>> actualResult = sqlHelper.statementQueryWithHead(connection, sql);
            System.out.println("Actual: " + actualResult);
            Assert.assertTrue(assertSimilarityDistance(actualResult, expectedResult, param.get("Sub_component")));
        } else if (param.get("Validation_type").equals("string_equals")) {
            String expectedResult = param.get("Expected_result");
            System.out.println("Expected: " + expectedResult);
            String actualResult = sqlHelper.queryWithStrReturn(connection, sql);
            System.out.println("Actual: " + actualResult);
            Assert.assertEquals(actualResult, expectedResult);
        } else if (param.get("Validation_type").equals("double_equals")) {
            Double expectedResult = Double.parseDouble(param.get("Expected_result"));
            System.out.println("Expected: " + expectedResult);
            Double actualResult = sqlHelper.queryWithDoubleReturn(connection, sql);
            System.out.println("Actual: " + actualResult);
            Assert.assertEquals(actualResult, expectedResult);
        } else if (param.get("Validation_type").equals("assertNull")) {
            Assert.assertNull(sqlHelper.queryWithObjReturn(connection, sql));
        } else if (param.get("Validation_type").equals("justExec")) {
            if (param.get("Component").equalsIgnoreCase("Explain")) {
                Thread.sleep(330000);
                sqlHelper.execSql(connection, sql);
            } else {
                sqlHelper.execSql(connection, sql);
            }
        }
        if (tableList.size() > 0) {
            if (param.get("Table_deletable").equalsIgnoreCase("yes")) {
                for (String s : tableList) {
                    sqlHelper.doDropTable(connection, s);
                }
            }
        }
        
//        JDBCUtils.closeResource(connection);
    }

    @Test(priority = 1, enabled = true, dataProvider = "indexData2", dataProviderClass = YamlDataHelper.class, description = "标量和向量混合索引测试")
    public void testIndex2(LinkedHashMap<String,String> param) throws SQLException, IOException, InterruptedException, ClassNotFoundException {
        if (param.get("Testable").trim().equals("n") || param.get("Testable").trim().equals("N")) {
            throw new SkipException("skip this test case");
        }

        List<String> tableList = new ArrayList<>();
        String sql = param.get("Sql_state").trim();
        String explainSql = param.get("Explain_state").trim();
        if (param.get("Table_schema_ref").trim().length() > 0) {
            List<String> schemaList = CastUtils.construct1DListIncludeBlank(param.get("Table_schema_ref"),",");
            for (int i = 0; i < schemaList.size(); i++) {
                String tableName = "";
                if (!schemaList.get(i).trim().contains("_")) {
                    if (param.get("Case_table_dependency").trim().length() > 0) {
                        tableName = param.get("Case_table_dependency").trim() + "_0" + i + schemaList.get(i).trim();
                        sql = sql.replace("$" + schemaList.get(i).trim(), tableName);
                        explainSql = explainSql.replace("$" + schemaList.get(i).trim(), tableName);
                    } else {
                        tableName = param.get("TestID").trim() + "_0" + i + schemaList.get(i).trim();
                        if (param.get("TestID").contains("txnbt")) {
                            sqlHelper.execFile(connection, TestIndex.class.getClassLoader().getResourceAsStream(iniReaderTXNBTREE.getValue("TableSchema",schemaList.get(i).trim())), tableName);
                        } else if (param.get("TestID").contains("btree")) {
                            sqlHelper.execFile(connection, TestIndex.class.getClassLoader().getResourceAsStream(iniReaderBTREE.getValue("TableSchema",schemaList.get(i).trim())), tableName);
                        } else {
                            sqlHelper.execFile(connection, TestIndex.class.getClassLoader().getResourceAsStream(iniReader.getValue("TableSchema",schemaList.get(i).trim())), tableName);
                        }
                        tableList.add(tableName);
                        sql = sql.replace("$" + schemaList.get(i).trim(), tableName);
                        explainSql = explainSql.replace("$" + schemaList.get(i).trim(), tableName);
                    }
                } else {
                    String schemaName = schemaList.get(i).trim().substring(0,schemaList.get(i).trim().indexOf("_"));
                    if (param.get("Case_table_dependency").trim().length() > 0) {
                        tableName = param.get("Case_table_dependency").trim() + "_0" + i + schemaName;
                        sql = sql.replace("$" + schemaList.get(i).trim(), tableName);
                        explainSql = explainSql.replace("$" + schemaList.get(i).trim(), tableName);
                    } else {
                        tableName = param.get("TestID").trim() + "_0" + i + schemaName;
                        if (param.get("TestID").contains("txnbt")) {
                            sqlHelper.execFile(connection, TestIndex.class.getClassLoader().getResourceAsStream(iniReaderTXNBTREE.getValue("TableSchema",schemaName)), tableName);
                        } else if (param.get("TestID").contains("btree")) {
                            sqlHelper.execFile(connection, TestIndex.class.getClassLoader().getResourceAsStream(iniReaderBTREE.getValue("TableSchema",schemaName)), tableName);
                        } else {
                            sqlHelper.execFile(connection, TestIndex.class.getClassLoader().getResourceAsStream(iniReader.getValue("TableSchema",schemaName)), tableName);
                        }
                        tableList.add(tableName);
                        sql = sql.replace("$" + schemaList.get(i).trim(), tableName);
                        explainSql = explainSql.replace("$" + schemaList.get(i).trim(), tableName);
                    }
                }
            }
            createTableSet.addAll(tableList);
            if (param.get("Case_table_dependency").trim().length() == 0) {
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

                        if (param.get("TestID").contains("txnbt")) {
                            sqlHelper.execFile(connection, TestIndex.class.getClassLoader().getResourceAsStream(iniReaderTXNBTREE.getValue("IndexValues", value_List.get(j).trim())), tableName);
                        } else if (param.get("TestID").contains("btree")) {
                            sqlHelper.execFile(connection, TestIndex.class.getClassLoader().getResourceAsStream(iniReaderBTREE.getValue("IndexValues", value_List.get(j).trim())), tableName);
                        } else {
                            sqlHelper.execFile(connection, TestIndex.class.getClassLoader().getResourceAsStream(iniReader.getValue("IndexValues", value_List.get(j).trim())), tableName);
                        }
                        
                    }
                }
            }
            if (param.get("Case_table_dependency").trim().length() > 0) {
                if (param.get("Table_value_ref").trim().length() > 0) {
                    List<String> value_List = CastUtils.construct1DListIncludeBlank(param.get("Table_value_ref").trim(),",");
                    for (int j = 0; j < value_List.size(); j++) {
                        String tableName = param.get("Case_table_dependency").trim() + "_0" + j + schemaList.get(j).trim();
                        if (param.get("TestID").contains("txnbt")) {
                            sqlHelper.execFile(connection, TestIndex.class.getClassLoader().getResourceAsStream(iniReaderTXNBTREE.getValue("IndexValues", value_List.get(j).trim())), tableName);
                        } else if (param.get("TestID").contains("btree")) {
                            sqlHelper.execFile(connection, TestIndex.class.getClassLoader().getResourceAsStream(iniReaderBTREE.getValue("IndexValues", value_List.get(j).trim())), tableName);
                        } else {
                            sqlHelper.execFile(connection, TestIndex.class.getClassLoader().getResourceAsStream(iniReader.getValue("IndexValues", value_List.get(j).trim())), tableName);
                        }
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
            List<List<String>> actualResult = sqlHelper.statementQueryWithHead(connection, sql);
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
            List<List<String>> actualResult = sqlHelper.statementQueryWithHead(connection, sql);
            System.out.println("Actual: " + actualResult);
            Assert.assertTrue(actualResult.containsAll(expectedResult));
            Assert.assertTrue(expectedResult.containsAll(actualResult));
        } else if (param.get("Validation_type").equalsIgnoreCase("similarity")) {
            String resultFile = param.get("Expected_result").trim();
            List<List<String>> expectedResult = new ArrayList<>();
            if (!param.get("Component").equalsIgnoreCase("ComplexDataType")){
                expectedResult = ParseCsv.splitCsvString(resultFile,",");
            } else {
                expectedResult = ParseCsv.splitCsvString(resultFile,"&");
            }
            System.out.println("Expected: " + expectedResult);
            List<List<String>> actualResult = sqlHelper.statementQueryWithHead(connection, sql);
            System.out.println("Actual: " + actualResult);
            Assert.assertTrue(assertSimilarity(actualResult, expectedResult, param.get("Sub_component")));
        } else if (param.get("Validation_type").equalsIgnoreCase("similarityId")) {
            String resultFile = param.get("Expected_result").trim();
            List<List<String>> expectedResult = new ArrayList<>();
            if (!param.get("Component").equalsIgnoreCase("ComplexDataType")){
                expectedResult = ParseCsv.splitCsvString(resultFile,",");
            } else {
                expectedResult = ParseCsv.splitCsvString(resultFile,"&");
            }
            System.out.println("Expected: " + expectedResult);
            List<List<String>> actualResult = sqlHelper.statementQueryWithHead(connection, sql);
            System.out.println("Actual: " + actualResult);
            Assert.assertTrue(assertSimilarityID(actualResult, expectedResult, param.get("Sub_component")));
        } else if (param.get("Validation_type").equalsIgnoreCase("similarityDistance")) {
            String resultFile = param.get("Expected_result").trim();
            List<List<String>> expectedResult = new ArrayList<>();
            if (!param.get("Component").equalsIgnoreCase("ComplexDataType")){
                expectedResult = ParseCsv.splitCsvString(resultFile,",");
            } else {
                expectedResult = ParseCsv.splitCsvString(resultFile,"&");
            }
            System.out.println("Expected: " + expectedResult);
            List<List<String>> actualResult = sqlHelper.statementQueryWithHead(connection, sql);
            System.out.println("Actual: " + actualResult);
            Assert.assertTrue(assertSimilarityDistance(actualResult, expectedResult, param.get("Sub_component")));
        } else if (param.get("Validation_type").equals("string_equals")) {
            String expectedResult = param.get("Expected_result");
            System.out.println("Expected: " + expectedResult);
            String actualResult = sqlHelper.queryWithStrReturn(connection, sql);
            System.out.println("Actual: " + actualResult);
            Assert.assertEquals(actualResult, expectedResult);
        } else if (param.get("Validation_type").equals("double_equals")) {
            Double expectedResult = Double.parseDouble(param.get("Expected_result"));
            System.out.println("Expected: " + expectedResult);
            Double actualResult = sqlHelper.queryWithDoubleReturn(connection, sql);
            System.out.println("Actual: " + actualResult);
            Assert.assertEquals(actualResult, expectedResult);
        } else if (param.get("Validation_type").equals("assertNull")) {
            Assert.assertNull(sqlHelper.queryWithObjReturn(connection, sql));
        } else if (param.get("Validation_type").equals("justExec")) {
            if (param.get("Component").equalsIgnoreCase("Explain")) {
                Thread.sleep(330000);
                sqlHelper.execSql(connection, sql);
            } else {
                sqlHelper.execSql(connection, sql);
            }
        }
        
        if (explainSql.trim().length() > 0) {
            if (param.get("Sub_component").equalsIgnoreCase("scalar_explain")) {
                Thread.sleep(330000);
            }
            JDBCUtils jdbcUtils = new JDBCUtils();
            Connection connection = jdbcUtils.getDingoConnectionInstance();
            String explainFile = param.get("Explain_result").trim();
            List<String> expectedExplainList = ParseCsv.splitCsvToList(explainFile);
            System.out.println("Expected explain list: " + expectedExplainList);
            String actualExplainStr = sqlHelper.queryWithStrReturn(connection, explainSql);
            for (int i = 0; i < expectedExplainList.size(); i++) {
                Assert.assertTrue(actualExplainStr.contains(expectedExplainList.get(i)));
            }
            JDBCUtils.closeResource(connection);
        }

        if (tableList.size() > 0) {
            for (String s : tableList) {
                sqlHelper.doDropTable(connection, s);
            }
        }
    }

    private Boolean assertSimilarityID(List<List<String>> actualList, List<List<String>> expectedList, String algorithm) {
        List actualIdList = new ArrayList<>();
        int actualColNum = actualList.get(0).size();
        for (int i = 1; i< actualList.size(); i++) {
            String actualVectorId = actualList.get(i).get(actualColNum - 1);
            actualIdList.add(actualVectorId);
        }

        List expectedIdList = new ArrayList<>();
        int expectedColNum = expectedList.get(0).size();
        for (int i = 1; i< expectedList.size(); i++) {
            String expectedVectorId = expectedList.get(i).get(expectedColNum - 1);
            expectedIdList.add(expectedVectorId);
        }

        //计算向量id相似度
        int similarCount = 0;
        for (int j = 0; j < actualIdList.size(); j++) {
            if (expectedIdList.contains(actualIdList.get(j))) {
                similarCount += 1;
            }
        }
        System.out.println("similarCount: " + similarCount);
        Double similarRatio = (double) similarCount / (double) actualIdList.size();
        System.out.println("similarKeyRatio: " + similarRatio);
        if (algorithm.contains("hnsw") || algorithm.contains("ivfflat") || algorithm.contains("ivfpq")) {
            if (similarRatio >= 0.8) {
                return true;
            } else {
                return false;
            } 
        } else if (algorithm.contains("flat")) {
            if (similarRatio == 1.0) {
                return true;
            } else {
                return false;
            }
        } else {
            if (similarRatio >= 0.6) {
                return true;
            } else {
               return false;
            }
        }
    }

    private Boolean assertSimilarityDistance(List<List<String>> actualList, List<List<String>> expectedList, String algorithm) {
        List actualDistanceList = new ArrayList<>();
        int actualColNum = actualList.get(0).size();
        for (int i = 1; i< actualList.size(); i++) {
            String actualVectorDistance = actualList.get(i).get(actualColNum - 1);
            actualDistanceList.add(Double.parseDouble(actualVectorDistance));
        }
        
        List expectedDistanceList = new ArrayList<>();
        int expectedColNum = expectedList.get(0).size();
        for (int i = 1; i< expectedList.size(); i++) {
            String expectedVectorDistance = expectedList.get(i).get(expectedColNum - 1);
            expectedDistanceList.add(Double.parseDouble(expectedVectorDistance));
        }
        
        if (algorithm.contains("hnsw") || algorithm.contains("ivfflat") || algorithm.contains("ivfpq")) {
            double similarity = calculateSimilarity(actualDistanceList, expectedDistanceList);
            System.out.println("similarity: " + similarity);
            if (similarity > 0.5) {
                return true;
            } else {
                return false;
            }
        } else if (algorithm.contains("flat")) {
            double similarity = calculateSimilarity(actualDistanceList, expectedDistanceList);
            System.out.println("similarity: " + similarity);
            if (similarity > 0.9) {
                return true;
            } else {
                return false;
            }
        } else {
            double similarity = calculateSimilarity(actualDistanceList, expectedDistanceList);
            System.out.println("similarity: " + similarity);
            if (similarity >= 0.6) {
                return true;
            } else {
                return false;
            }
        }
    }
    
    private Boolean assertSimilarity(List<List<String>> actualList, List<List<String>> expectedList, String algorithm) {
        List actualIdList = new ArrayList<>();
        List actualDistanceList = new ArrayList<>();
        int actualColNum = actualList.get(0).size();
        for (int i = 1; i < actualList.size(); i++) {
            String actualVectorId = actualList.get(i).get(actualColNum - 2);
            actualIdList.add(actualVectorId);
            String actualVectorDistance = actualList.get(i).get(actualColNum - 1);
            actualDistanceList.add(Double.parseDouble(actualVectorDistance));
        }
        System.out.println("ActualIdList: " + actualIdList);
        System.out.println("ActualDistanceList: " + actualDistanceList);

        List expectedIdList = new ArrayList<>();
        List expectedDistanceList = new ArrayList<>();
        int expectedColNum = expectedList.get(0).size();
        for (int i = 1; i < expectedList.size(); i++) {
            String expectedVectorId = expectedList.get(i).get(expectedColNum - 2);
            expectedIdList.add(expectedVectorId);
            String expectedVectorDistance = expectedList.get(i).get(expectedColNum - 1);
            expectedDistanceList.add(Double.parseDouble(expectedVectorDistance));
        }
        System.out.println("ExpectedIdList: " + expectedIdList);
        System.out.println("ExpectedDistanceList: " + actualDistanceList);
        
        //计算向量id相似度
        int similarCount = 0;
        for (int j = 0; j < actualIdList.size(); j++) {
            if (expectedIdList.contains(actualIdList.get(j))) {
                similarCount += 1;
            } 
        }
        System.out.println("similarCount: " + similarCount);
        Double similarRatio = (double) similarCount / (double) actualIdList.size();
        System.out.println("similarKeyRatio: " + similarRatio);
        if (algorithm.contains("hnsw") || algorithm.contains("ivfflat") || algorithm.contains("ivfpq")) {
            if (similarRatio >= 0.8) {
                return true;
            } else {
                double similarity = calculateSimilarity(actualDistanceList, expectedDistanceList);
                System.out.println("similarity: " + similarity);
                if (similarity > 0.5) {
                    return true;
                } else {
                    return false;
                }
            }
        } else if (algorithm.contains("flat")) {
            if (similarRatio == 1.0) {
                return true;
            } else {
                double similarity = calculateSimilarity(actualDistanceList, expectedDistanceList);
                System.out.println("similarity: " + similarity);
                if (similarity > 0.9) {
                    return true;
                } else {
                    return false;
                }
            }
        } else {
            if (similarRatio >= 0.6) {
                return true;
            } else {
                double similarity = calculateSimilarity(actualDistanceList, expectedDistanceList);
                System.out.println("similarity: " + similarity);
                if (similarity >= 0.6) {
                    return true;
                } else {
                    return false;
                }
            }
        }
    }
    
    private static double calculateSimilarity(List<Double> actualDistance, List<Double> expectedDistance) {
        double sum = 0;
        for (int i = 0; i < actualDistance.size(); i++) {
            double diff = actualDistance.get(i) - expectedDistance.get(i);
            sum += diff * diff;
        }
        return 1/(1 + Math.sqrt(sum));
    }
    
}
