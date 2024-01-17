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
    public static Connection myConnection;
    private static HashSet<String> createTableSet = new HashSet<>();


    @BeforeClass (alwaysRun = true)
    public static void setupAll() throws SQLException, IOException, ClassNotFoundException {
        mySQLHelper = new MySQLHelper();
        MySQLUtils mySQLUtils = new MySQLUtils();
        myConnection = mySQLUtils.getMySQLConnectionInstance();
        Assert.assertNotNull(myConnection);
//        mySQLHelper = new MySQLHelper();
//        myConnection = DruidUtils.getDruidMySQLConnection();
//        Assert.assertNotNull(myConnection);
    }

    @AfterClass (alwaysRun = true)
    public static void tearDownAll() throws SQLException, IOException, ClassNotFoundException {
        if(createTableSet.size() > 0) {
            List<String> finalTableList = MySQLUtils.getTableList();
            for (String s : createTableSet) {
                if (finalTableList.contains(s.toUpperCase())) {
                    mySQLHelper.doDropTable(myConnection, s);
                }
            }
        }
        MySQLUtils.closeResource(myConnection);
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
        mySQLHelper.execSql(myConnection, createSql);
        String queryUser = param.get("Query_user");
        if (param.get("Validation_type").equals("csv_containsAll")) {
            String resultFile = param.get("Expected_user").trim();
            List<List<String>> expectedUser = ParseCsv.splitCsvString(resultFile,",");
            System.out.println("Expected: " + expectedUser);
            List<List<String>> actualUser = mySQLHelper.statementQueryWithHead(myConnection, queryUser);
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
            mySQLHelper.execSql(myConnection, "drop user '" + userName + "'@'" + hostStr + "'");
        } else {
            mySQLHelper.execSql(myConnection, "drop user '" + userName + "'");
        }
    }
}
