package io.dingodb.mysqltest;

import datahelper.MySQLYamlDataHelper;
import io.dingodb.common.utils.CommonArgs;
import io.dingodb.common.utils.MySQLUtils;
import io.dingodb.dailytest.MySQLHelper;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.ParseCsv;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;

public class TestProtocolMySQL extends BaseTestSuiteMySQL {
    private static MySQLHelper mySQLHelper;
    private static HashSet<String> createTableSet = new HashSet<>();
    private static HashSet<String> createUserSet = new HashSet<>();


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

        if(createUserSet.size() > 0) {
            for (String s : createUserSet) {
                mySQLHelper.execSql("drop user " + s);
            }
        }
    }

    @BeforeMethod (alwaysRun = true)
    public void setup() throws Exception {
    }

    @AfterMethod (alwaysRun = true)
    public void cleanUp() throws Exception {
    }

    @Test(priority = 0, enabled = true, dataProvider = "mysqlProtocolData", dataProviderClass = MySQLYamlDataHelper.class, description = "mysql协议相关用例")
    public void test01DCL(LinkedHashMap<String,String> param) throws SQLException, ClassNotFoundException, IOException {
        if (param.get("Testable").trim().equals("n") || param.get("Testable").trim().equals("N")) {
            throw new SkipException("skip this test case");
        }
        List<String> tableList = new ArrayList<>();
        List<String> userList = new ArrayList<>();

        String operationSql = param.get("Op_sql");
        if (operationSql.length()>0) {
            mySQLHelper.execSql(operationSql);
        }
        
        if (param.get("Table_used").trim().length() > 0) {
            String tableUsed = param.get("Table_used");
            tableList.add(tableUsed);
        } 
        
        if (param.get("User_used").trim().length() > 0) {
            String userName;
            userName = param.get("User_used").trim();
            if(param.get("Host_used").trim().length() > 0) {
                String userStr = param.get("User_used").trim();
                String hostStr = param.get("Host_used").trim();
                userName = "'" + userStr + "'@'" + hostStr + "'";
            }
            userList.add(userName);
        } 

        createTableSet.addAll(tableList);
        createUserSet.addAll(userList);

        String querySql = param.get("Query_sql");
        if (param.get("Component").equals("Protocol")) {
            if (param.get("Validation_type").equals("csv_equals")) {
                String resultFile = param.get("Query_result").trim();
                List<List<String>> expectedResult = ParseCsv.splitCsvString(resultFile,"&");
                System.out.println("Expected: " + expectedResult);
                List<List<String>> actualResult = mySQLHelper.statementQueryWithHead(querySql);
                System.out.println("Actual: " + actualResult);
                Assert.assertEquals(actualResult, expectedResult);
            }

            if (param.get("Validation_type").equals("csv_containsAll")) {
                String resultFile = param.get("Query_result").trim();
                List<List<String>> expectedResult = ParseCsv.splitCsvString(resultFile,"&");
                System.out.println("Expected: " + expectedResult);
                List<List<String>> actualResult = mySQLHelper.statementQueryWithHead(querySql);
                System.out.println("Actual: " + actualResult);
                Assert.assertTrue(actualResult.containsAll(expectedResult));
                Assert.assertTrue(expectedResult.containsAll(actualResult));
            }
            
            if (param.get("Validation_type").equals("connection")) {
                Connection connectionCheck = MySQLUtils.getConnectionWithNotRoot(CommonArgs.getDefaultConnectUser(), CommonArgs.getDefaultConnectPass());
                Assert.assertNotNull(connectionCheck);
                connectionCheck.close();
            }
        } else if (param.get("Component").equalsIgnoreCase("Distribution")) {
            if (param.get("Validation_type").equals("csv_equals")) {
                String resultFile = param.get("Query_result").trim();
                List<List<String>> expectedResult = ParseCsv.splitCsvString(resultFile,"&");
                System.out.println("Expected: " + expectedResult);
                List<Integer> colIndexList = Arrays.asList(2,3);
                List<List<String>> actualResult = mySQLHelper.statementQueryWithSpecifiedColIndex(querySql, colIndexList);
                System.out.println("Actual: " + actualResult);
                Assert.assertEquals(actualResult, expectedResult);
            }
            if (param.get("Validation_type").equals("csv_containsAll")) {
                String resultFile = param.get("Query_result").trim();
                List<List<String>> expectedResult = ParseCsv.splitCsvString(resultFile,"&");
                System.out.println("Expected: " + expectedResult);
                List<Integer> colIndexList = Arrays.asList(2,3);
                List<List<String>> actualResult = mySQLHelper.statementQueryWithSpecifiedColIndex(querySql, colIndexList);
                System.out.println("Actual: " + actualResult);
                Assert.assertTrue(actualResult.containsAll(expectedResult));
                Assert.assertTrue(expectedResult.containsAll(actualResult));
            }
        } else if (param.get("Component").equalsIgnoreCase("HASH_Distribution")) {
            if (param.get("Validation_type").equals("csv_equals")) {
                String resultFile = param.get("Query_result").trim();
                List<List<String>> expectedResult = ParseCsv.splitCsvString(resultFile,"&");
                System.out.println("Expected: " + expectedResult);
                List<Integer> colIndexList = Arrays.asList(2);
                List<List<String>> actualResult = mySQLHelper.statementQueryWithSpecifiedColIndex(querySql, colIndexList);
                System.out.println("Actual: " + actualResult);
                Assert.assertEquals(actualResult, expectedResult);
            }
            if (param.get("Validation_type").equals("csv_containsAll")) {
                String resultFile = param.get("Query_result").trim();
                List<List<String>> expectedResult = ParseCsv.splitCsvString(resultFile,"&");
                System.out.println("Expected: " + expectedResult);
                List<Integer> colIndexList = Arrays.asList(2);
                List<List<String>> actualResult = mySQLHelper.statementQueryWithSpecifiedColIndex(querySql, colIndexList);
                System.out.println("Actual: " + actualResult);
                Assert.assertTrue(actualResult.containsAll(expectedResult));
                Assert.assertTrue(expectedResult.containsAll(actualResult));
            }
        }
    }
}
