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

public class TestBatchSQL extends BaseTestSuite {
    private static SQLHelper sqlHelper;
    public static Connection connection;
    private static HashSet<String> createTableSet = new HashSet<>();
    private static HashSet<String> createSchemaSet = new HashSet<>();


    @BeforeClass (alwaysRun = true)
    public static void setupAll() throws SQLException, ClassNotFoundException {
        sqlHelper = new SQLHelper();
        JDBCUtils jdbcUtils = new JDBCUtils();
        connection = jdbcUtils.getDingoConnectionInstance();
        Assert.assertNotNull(connection);
    }

    @AfterClass (alwaysRun = true)
    public static void tearDownAll() throws SQLException, IOException, ClassNotFoundException {
        System.out.println(createTableSet);
        if(createTableSet.size() > 0) {
            List<String> finalTableList = JDBCUtils.getTableList();
            for (String s : createTableSet) {
                if (finalTableList.contains(s.toUpperCase())) {
                    sqlHelper.doDropTable(connection, s);
                }
            }
        }

        System.out.println(createSchemaSet);
        if(createSchemaSet.size() > 0) {
            for (String sc: createSchemaSet) {
                List<String> finalSchemaTableList = JDBCUtils.getTableListWithSchema(sc);
                if (finalSchemaTableList.size() > 0) {
                    for (String t : finalSchemaTableList) {
                        sqlHelper.doDropTable(connection,sc.toUpperCase() + "." + t);
                    }
                }
                if (sc.trim().length() > 0) {
                    sqlHelper.doDropSchema(connection, sc.toUpperCase());
                }
            }
        }
        
        JDBCUtils.closeResource(connection);
    }

    @BeforeMethod(enabled = true)
    public void setup() throws Exception {
    }

    @AfterMethod(enabled = true)
    public void cleanUp() throws Exception {
    }

    @Test(priority = 0, enabled = true, dataProvider = "sqlBatchData", dataProviderClass = YamlDataHelper.class, description = "验证批量执行sql语句")
    public void testBatchSQL(LinkedHashMap<String,String> param) throws SQLException, IOException {
//        JDBCUtils jdbcUtils = new JDBCUtils();
//        Connection connection = jdbcUtils.getDingoConnectionInstance();
        if (param.get("Testable").trim().equals("n") || param.get("Testable").trim().equals("N")) {
            throw new SkipException("skip this test case");
        }

        if ((param.get("Schema").trim().length() > 0)) {
            List<String> schemaList = CastUtils.construct1DListIncludeBlank(param.get("Schema"),",");
            createSchemaSet.addAll(schemaList);
        }
        List<String> tableList = CastUtils.construct1DListIncludeBlank(param.get("Table_name"),",");
        createTableSet.addAll(tableList);
        String querySql1 = param.get("Query_sql1");
        String querySql2 = param.get("Query_sql2");
        for ( int i = 0; i < 1; i++) {
            if (param.get("TestID").contains("txnbt")) {
                sqlHelper.execFile(connection, TestBatchSQL.class.getClassLoader().getResourceAsStream(iniReaderTXNBTREE.getValue("BatchSQLOp",
                        param.get("Batch_sql"))), tableList.get(i).trim());
            } else if (param.get("TestID").contains("btree")) {
                sqlHelper.execFile(connection, TestBatchSQL.class.getClassLoader().getResourceAsStream(iniReaderBTREE.getValue("BatchSQLOp",
                        param.get("Batch_sql"))), tableList.get(i).trim());
            } else {
                sqlHelper.execFile(connection, TestBatchSQL.class.getClassLoader().getResourceAsStream(iniReader.getValue("BatchSQLOp",
                        param.get("Batch_sql"))), tableList.get(i).trim());
            }
            if (querySql1.trim().length() > 0) {
                querySql1 = querySql1.replace("$" + tableList.get(i).trim(), tableList.get(i).trim());
            }

            if (querySql2.trim().length() > 0) {
                querySql2 = querySql2.replace("$" + tableList.get(i).trim(), tableList.get(i).trim());
            }
        }
        
        if (param.get("Validation_type").equals("csv_equals")) {
            if (param.get("Query_result1").trim().length() > 0) {
                String resultFile1 = param.get("Query_result1").trim();
                List<List<String>> expectedResult1 = new ArrayList<>();
                if (!param.get("Component").equalsIgnoreCase("ComplexDataType")){
                    expectedResult1 = ParseCsv.splitCsvString(resultFile1,",");
                } else {
                    expectedResult1 = ParseCsv.splitCsvString(resultFile1,"&");
                }
                System.out.println("Expected result1: " + expectedResult1);
                List<List<String>> actualResult1 = sqlHelper.statementQueryWithHead(connection, querySql1);
                System.out.println("Actual result1: " + actualResult1);
                Assert.assertEquals(actualResult1, expectedResult1);
            }

            if (param.get("Query_result2").trim().length() > 0) {
                String resultFile2 = param.get("Query_result2").trim();
                List<List<String>> expectedResult2 = new ArrayList<>();
                if (!param.get("Component").equalsIgnoreCase("ComplexDataType")){
                    expectedResult2 = ParseCsv.splitCsvString(resultFile2,",");
                } else {
                    expectedResult2 = ParseCsv.splitCsvString(resultFile2,"&");
                }
                System.out.println("Expected result2: " + expectedResult2);
                List<List<String>> actualResult2 = sqlHelper.statementQueryWithHead(connection, querySql2);
                System.out.println("Actual result2: " + actualResult2);
                Assert.assertEquals(actualResult2, expectedResult2);
            }
            
        } else if (param.get("Validation_type").equals("csv_containsAll")) {
            if (param.get("Query_result1").trim().length() > 0) {
                String resultFile1 = param.get("Query_result1").trim();
                List<List<String>> expectedResult1 = new ArrayList<>();
                if (!param.get("Component").equalsIgnoreCase("ComplexDataType")){
                    expectedResult1 = ParseCsv.splitCsvString(resultFile1,",");
                } else {
                    expectedResult1 = ParseCsv.splitCsvString(resultFile1,"&");
                }
                System.out.println("Expected result1: " + expectedResult1);
                List<List<String>> actualResult1 = sqlHelper.statementQueryWithHead(connection, querySql1);
                System.out.println("Actual result1: " + actualResult1);
                Assert.assertTrue(actualResult1.containsAll(expectedResult1));
                Assert.assertTrue(expectedResult1.containsAll(actualResult1));
            }

            if (param.get("Query_result2").trim().length() > 0) {
                String resultFile2 = param.get("Query_result2").trim();
                List<List<String>> expectedResult2 = new ArrayList<>();
                if (!param.get("Component").equalsIgnoreCase("ComplexDataType")){
                    expectedResult2 = ParseCsv.splitCsvString(resultFile2,",");
                } else {
                    expectedResult2 = ParseCsv.splitCsvString(resultFile2,"&");
                }
                System.out.println("Expected result2: " + expectedResult2);
                List<List<String>> actualResult2 = sqlHelper.statementQueryWithHead(connection, querySql2);
                System.out.println("Actual result2: " + actualResult2);
                Assert.assertTrue(actualResult2.containsAll(expectedResult2));
                Assert.assertTrue(expectedResult2.containsAll(actualResult2));
            }
        }

        if (tableList.size() > 0) {
            for (String s : tableList) {
                sqlHelper.doDropTable(connection, s);
            }
        }

//        JDBCUtils.closeResource(connection);
    }
}
