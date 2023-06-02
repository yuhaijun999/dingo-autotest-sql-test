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
import utils.ParseCsv;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;

public class TestDCL extends BaseTestSuite {
    private static SQLHelper sqlHelper;
    private static HashSet<String> createTableSet = new HashSet<>();


    @BeforeClass (alwaysRun = true)
    public static void setupAll() {
        sqlHelper = new SQLHelper();
    }

    @AfterClass (alwaysRun = true)
    public static void tearDownAll() throws SQLException, IOException, ClassNotFoundException {
        if(createTableSet.size() > 0) {
            List<String> finalTableList = JDBCUtils.getTableList();
            for (String s : createTableSet) {
                if (finalTableList.contains(s.toUpperCase())) {
                    sqlHelper.doDropTable(s);
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

    @Test(priority = 0, enabled = true, dataProvider = "dclData", dataProviderClass = YamlDataHelper.class, description = "dcl操作，正向用例")
    public void test01DCL(LinkedHashMap<String,String> param) throws SQLException, ClassNotFoundException {
        if (param.get("Testable").trim().equals("n") || param.get("Testable").trim().equals("N")) {
            throw new SkipException("skip this test case");
        }
        List<String> tableList = new ArrayList<>();
        String userName = param.get("User_name").trim();
        String passStr = param.get("Pass_str");
        String hostStr = param.get("Host").trim();
        String createSql = param.get("Create_state");
        sqlHelper.execSql(createSql);
        String queryUser = param.get("Query_user");
        if (param.get("Validation_type").equals("csv_containsAll")) {
            String resultFile = param.get("Expected_user").trim();
            List<List<String>> expectedUser = ParseCsv.splitCsvString(resultFile,",");
            System.out.println("Expected: " + expectedUser);
            List<List<String>> actualUser = sqlHelper.statementQueryWithHead(queryUser);
            System.out.println("Actual: " + actualUser);
            Assert.assertTrue(actualUser.containsAll(expectedUser));
            Assert.assertTrue(expectedUser.containsAll(actualUser));
        }
        
        if (param.get("Connection_verify").equalsIgnoreCase("yes")) {
            Connection connectionWithUser = JDBCUtils.getConnectionWithNotRoot(userName, passStr);
            Assert.assertNotNull(connectionWithUser);
            connectionWithUser.close();
        }
        if (hostStr.length() > 0) {
            sqlHelper.execSql("drop user '" + userName + "'@'" + hostStr + "'");
        } else {
            sqlHelper.execSql("drop user '" + userName + "'");
        }
    }
}
