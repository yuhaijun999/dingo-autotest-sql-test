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
import utils.ParseCsv;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;

public class TestDCLMySQL extends BaseTestSuiteMySQL {
    private static MySQLHelper mySQLHelper;
    private static HashSet<String> createTableSet = new HashSet<>();


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
    }

    @BeforeMethod (alwaysRun = true)
    public void setup() throws Exception {
    }

    @AfterMethod (alwaysRun = true)
    public void cleanUp() throws Exception {
    }

    @Test(priority = 0, enabled = true, dataProvider = "mysqlDCLData", dataProviderClass = MySQLYamlDataHelper.class, description = "dcl操作，正向用例")
    public void test01DCL(LinkedHashMap<String,String> param) throws SQLException, ClassNotFoundException {
        if (param.get("Testable").trim().equals("n") || param.get("Testable").trim().equals("N")) {
            throw new SkipException("skip this test case");
        }
        List<String> tableList = new ArrayList<>();
        String userName = param.get("User_name").trim();
        String passStr = param.get("Pass_str");
        String hostStr = param.get("Host").trim();
        String createSql = param.get("Create_state");
        mySQLHelper.execSql(createSql);
        String queryUser = param.get("Query_user");
        if (param.get("Validation_type").equals("csv_containsAll")) {
            String resultFile = param.get("Expected_user").trim();
            List<List<String>> expectedUser = ParseCsv.splitCsvString(resultFile,",");
            System.out.println("Expected: " + expectedUser);
            List<List<String>> actualUser = mySQLHelper.statementQueryWithHead(queryUser);
            System.out.println("Actual: " + actualUser);
            Assert.assertTrue(actualUser.containsAll(expectedUser));
            Assert.assertTrue(expectedUser.containsAll(actualUser));
        }
        
        if (param.get("Connection_verify").equalsIgnoreCase("yes")) {
            Connection connectionWithUser = MySQLUtils.getConnectionWithNotRoot(userName, passStr);
            Assert.assertNotNull(connectionWithUser);
            connectionWithUser.close();
        }
        
        if (hostStr.length() > 0) {
            mySQLHelper.execSql("drop user '" + userName + "'@'" + hostStr + "'");
        } else {
            mySQLHelper.execSql("drop user '" + userName + "'");
        }
    }
}
