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
import io.dingodb.common.utils.DruidUtilsDingo;
import io.dingodb.dailytest.DingoHelperDruid;
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
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class TestDCL extends BaseTestSuite {
    private static DingoHelperDruid dingoHelperDruid;
    private static String tableName1 = "studentdc";
    private static String tableName2 = "classesdc";

    @BeforeClass (alwaysRun = true)
    public static void setupAll() throws SQLException, ClassNotFoundException {
        dingoHelperDruid = new DingoHelperDruid();
        //权限测试类执行前，先创建若干表用于测试。
        String createSql1 = "create table if not exists " + tableName1 + " (\n" +
                "    sno varchar(6), \n" +
                "    sname varchar(20), \n" +
                "    sage int, \n" +
                "    ssex varchar(10),\n" +
                "    class_no varchar(6), \n" +
                "    primary key(sno)\n" +
                ")";
        String createSql2 = "create table if not exists " + tableName2 + " (\n" +
                "    class_no varchar(6),\n" +
                "    class_name varchar(12),\n" +
                "    primary key(class_no)\n" +
                ")";
        String insertSql1 = "insert into studentdc values " +
                "('1001', 'Li Bai', 15, 'Male', '901'),\n" +
                "('1002', 'Du Fu', 15, 'Male', '902'), \n" +
                "('1003', 'Bai Juyi', 13, 'Female','902')," +
                "('1004', 'Tian E', 18, 'Female','903'),\n" +
                "('1005','Wang Bo', 20, 'Male','904'),\n" +
                "('1006','Li Qingzhao', 33, 'Female', '903'),\n" +
                "('1007', 'Waner', 21, 'Female', '905'),\n" +
                "('1008', 'Li Yu', 47, 'Male', '905')";
        String insertSql2 = "insert into classesdc values " +
                "('901','class1-1'),\n" +
                "('902','class1-2'),\n" +
                "('903','class1-3'),\n" +
                "('904','class1-4'),\n" +
                "('906','class1-6')";
        dingoHelperDruid.doDropTable(tableName1);
        dingoHelperDruid.doDropTable(tableName2);
        dingoHelperDruid.execSql(createSql1);
        dingoHelperDruid.execSql(createSql2);
        dingoHelperDruid.execSql(insertSql1);
        dingoHelperDruid.execSql(insertSql2);
    }

    @AfterClass (alwaysRun = true)
    public static void tearDownAll() throws SQLException, IOException, ClassNotFoundException {
        dingoHelperDruid.doDropTable(tableName1);
        dingoHelperDruid.doDropTable(tableName2);
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
        
        String userName = param.get("User_name").trim();
        String passStr = param.get("Pass_str");
        String userStr;
        String hostStr;
        if (param.get("Host").trim().length() > 0) {
            hostStr = param.get("Host").trim();
            userStr = "'" + userName + "'@'" + hostStr + "'";
        } else {
            userStr = userName;
        }
//        dingoHelperDruid.doDropUser(userStr);
        String createSql = param.get("Create_state");
        dingoHelperDruid.execSql(createSql);
        
        //为用户赋权
        if (param.get("Grant_state").trim().length() > 0) {
            String grantState = param.get("Grant_state").trim();
            dingoHelperDruid.execSql(grantState);
        }
        //show grants断言
        if (param.get("Query_grants").trim().length() > 0) {
            String queryGrants = param.get("Query_grants");
            List<List<String>> actualGrantsResult = dingoHelperDruid.statementQueryWithHead(queryGrants);
            System.out.println("Actual show grants: " + actualGrantsResult);
            String grantsResultFile = param.get("Expected_grants");
            List<List<String>> expectedGrantsResult = ParseCsv.splitCsvString(grantsResultFile,",");
            System.out.println("Expected show grants: " + expectedGrantsResult);
            Assert.assertTrue(actualGrantsResult.containsAll(expectedGrantsResult));
            Assert.assertTrue(expectedGrantsResult.containsAll(actualGrantsResult));
        }
        
        //查询mysql.user表断言
        if (param.get("Query_user").trim().length() > 0) {
            String queryUser = param.get("Query_user");
            String userResultFile = param.get("Expected_user").trim();
            List<List<String>> expectedUser = ParseCsv.splitCsvString(userResultFile,",");
            System.out.println("Expected user table: " + expectedUser);
            List<List<String>> actualUser = dingoHelperDruid.statementQueryWithHead(queryUser);
            System.out.println("Actual user table: " + actualUser);
            Assert.assertTrue(actualUser.containsAll(expectedUser));
            Assert.assertTrue(expectedUser.containsAll(actualUser));
        }
        
        //查询mysql.db表断言
        if (param.get("Query_db").trim().length() > 0) {
            String queryDb = param.get("Query_db");
            String dbResultFile = param.get("Expected_db").trim();
            List<List<String>> expectedDb = ParseCsv.splitCsvString(dbResultFile,",");
            System.out.println("Expected db table: " + expectedDb);
            List<List<String>> actualDb = dingoHelperDruid.statementQueryWithHead(queryDb);
            System.out.println("Actual db table: " + actualDb);
            Assert.assertTrue(actualDb.containsAll(expectedDb));
            Assert.assertTrue(expectedDb.containsAll(actualDb));
        }
        
        //查询mysql.tables_priv表断言
        if (param.get("Query_tablesPriv").trim().length() > 0) {
            String queryTablesPriv = param.get("Query_tablesPriv");
            String tablesPrivResultFile = param.get("Expected_tablesPriv").trim();
            List<List<String>> expectedTablesPriv = ParseCsv.splitCsvString(tablesPrivResultFile,",");
            System.out.println("Expected tables_priv table: " + expectedTablesPriv);
            List<List<String>> actualTablesPriv = dingoHelperDruid.statementQueryWithHead(queryTablesPriv);
            System.out.println("Actual tables_priv table: " + actualTablesPriv);
            Assert.assertTrue(actualTablesPriv.containsAll(expectedTablesPriv));
            Assert.assertTrue(expectedTablesPriv.containsAll(actualTablesPriv));
        }
        
        if (param.get("Connection_verify").equalsIgnoreCase("yes")) {
            Connection connectionWithUser = DruidUtilsDingo.getDruidDingoConnectionWithNotRoot(userName, passStr);
            Assert.assertNotNull(connectionWithUser);

            try {
                Statement userstate = connectionWithUser.createStatement();
                String tableOperation = param.get("Table_operations");
                dingoHelperDruid.execBatchSql(userstate, tableOperation);

                //用户连接获取可以看到的tables
                DatabaseMetaData databaseMetaData = connectionWithUser.getMetaData();
                List<String> tableList = new ArrayList<String>();
                String[] types={"TABLE"};
                ResultSet rst = databaseMetaData.getTables(null, null, "%", types);
                while (rst.next()) {
                    tableList.add(rst.getString("TABLE_NAME").toUpperCase());
                }
                System.out.println("Actual getTables: " + tableList);

                if (param.get("Table_priv").length() > 0) {
                    List<String> expectedTableList = CastUtils.construct1DListIncludeBlank(param.get("Table_priv").toUpperCase(),",");
                    System.out.println("Expected getTables: " + expectedTableList);

                    Assert.assertTrue(tableList.containsAll(expectedTableList));
//                    Assert.assertTrue(expectedTableList.containsAll(tableList));
                } else {
                    Assert.assertEquals(tableList.size(), 10);
                }
                
            } finally {
                connectionWithUser.close();
            }
            
            if (param.get("Reset_pass_str").length() > 0) {
                String newPass = param.get("Reset_pass_str");
                String resetSql = "set password for " + userStr + " = password('" + newPass + "')";
                dingoHelperDruid.execSql(resetSql);
                Connection connectionWithNewPass = null;
                try {
                    connectionWithNewPass = DruidUtilsDingo.getDruidDingoConnectionWithNotRoot(userName, newPass);
                    Assert.assertNotNull(connectionWithUser);
                } finally {
                    DruidUtilsDingo.closeResource(connectionWithNewPass,null,null);
                }
            }
        }

        if (param.get("Revoke_state").trim().length() > 0) {
            String revokeState = param.get("Revoke_state").trim();
            dingoHelperDruid.execSql(revokeState);

            String queryGrantsAfterRevoke = param.get("Query_grants");
            List<List<String>> actualGrantsAfterRevoke = dingoHelperDruid.statementQueryWithHead(queryGrantsAfterRevoke);
            System.out.println("Actual grants after revoke: " + actualGrantsAfterRevoke);
            String grantsResultFile = param.get("Expected_grants_after_revoke");
            List<List<String>> expectedGrantsAfterRevoke = ParseCsv.splitCsvString(grantsResultFile,",");
            System.out.println("Expected grants after revoke: " + expectedGrantsAfterRevoke);
            Assert.assertTrue(actualGrantsAfterRevoke.containsAll(expectedGrantsAfterRevoke));
            Assert.assertTrue(expectedGrantsAfterRevoke.containsAll(actualGrantsAfterRevoke));
        }
        
        //测试完后，删除用户
        dingoHelperDruid.doDropUser(userStr);
        
    }
}
