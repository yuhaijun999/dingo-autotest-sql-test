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

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;

public class TestBatchSQLMySQL extends BaseTestSuiteMySQL {
    private static MySQLHelper mySQLHelper;
    private static HashSet<String> createTableSet = new HashSet<>();


    @BeforeClass (alwaysRun = true)
    public static void setupAll() {
        mySQLHelper = new MySQLHelper();
    }

    @AfterClass
    public static void tearDownAll() throws SQLException, IOException, ClassNotFoundException {
        if(createTableSet.size() > 0) {
            List<String> finalTableList = MySQLUtils.getTableList();
            for (String s : createTableSet) {
                if (finalTableList.contains("MYSQL" + s.toUpperCase())) {
                    mySQLHelper.doDropTable("mysql_" + s);
                }
            }
        }
    }

    @BeforeMethod(enabled = true)
    public void setup() throws Exception {
    }

    @AfterMethod(enabled = true)
    public void cleanUp() throws Exception {
    }

    @Test(priority = 0, enabled = true, dataProvider = "mysqlSQLBatchData", dataProviderClass = MySQLYamlDataHelper.class, description = "验证批量执行sql语句")
    public void testBatchSQL(LinkedHashMap<String,String> param) throws SQLException, IOException {
        if (param.get("Testable").trim().equals("n") || param.get("Testable").trim().equals("N")) {
            throw new SkipException("skip this test case");
        }

        List<String> tableList = CastUtils.construct1DListIncludeBlank(param.get("Table_name"),",");
        createTableSet.addAll(tableList);
        String querySql1 = param.get("Query_sql1");
        String querySql2 = param.get("Query_sql2");
        for ( int i = 0; i < 1; i++) {
            mySQLHelper.execFile(TestBatchSQLMySQL.class.getClassLoader().getResourceAsStream(mysqlIniReader.getValue("BatchSQLOp", 
                    param.get("Batch_sql"))), "mysql_" + tableList.get(i).trim());
            if (querySql1.trim().length() > 0) {
                querySql1 = querySql1.replace("$" + tableList.get(i).trim(), "mysql_" + tableList.get(i).trim());
            }

            if (querySql2.trim().length() > 0) {
                querySql2 = querySql2.replace("$" + tableList.get(i).trim(), "mysql_" + tableList.get(i).trim());
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
                List<List<String>> actualResult1 = mySQLHelper.statementQueryWithHead(querySql1);
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
                List<List<String>> actualResult2 = mySQLHelper.statementQueryWithHead(querySql2);
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
                List<List<String>> actualResult1 = mySQLHelper.statementQueryWithHead(querySql1);
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
                List<List<String>> actualResult2 = mySQLHelper.statementQueryWithHead(querySql2);
                System.out.println("Actual result2: " + actualResult2);
                Assert.assertTrue(actualResult2.containsAll(expectedResult2));
                Assert.assertTrue(expectedResult2.containsAll(actualResult2));
            }
        }

        if (tableList.size() > 0) {
            for (String s : tableList) {
                mySQLHelper.doDropTable("mysql_" + s);
            }
        }
    }
}
