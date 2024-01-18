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

import io.dingodb.common.utils.DruidUtilsMySQL;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import utils.IniReader;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class BaseTestSuiteMySQL {
    public static IniReader mysqlIniReader;
    public static IniReader mysqlIniReaderBTREE;
//    public static MySQLHelper mySQLHelper;
    @BeforeSuite(alwaysRun = true, enabled = true, description = "所有测试开始前的准备工作")
    public static void beforeSuite() throws SQLException {
//        mySQLHelper = new MySQLHelper();
        System.out.println("所有测试开始前，验证MySQL JDBC数据库连接正常");
//        Assert.assertNotNull(MySQLHelper.connection);
        Connection connection = null;
        Statement statement = null;
        try {
            connection = DruidUtilsMySQL.getDruidMySQLConnection();
            Assert.assertNotNull(connection);
            mysqlIniReader = new IniReader("src/test/resources/io.dingodb.test/ini/mysql_lsm.ini");
            mysqlIniReaderBTREE = new IniReader("src/test/resources/io.dingodb.test/ini/mysql_btree.ini");
            statement = connection.createStatement();
            statement.execute("set global connect_timeout=120");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }  finally {
            DruidUtilsMySQL.closeResource(connection, null, statement);
        }
    }

    @AfterSuite(alwaysRun = true, enabled = true, description = "所有测试结束后的操作")
    public static void afterSuite() {
        System.out.println("所有测试结束后，关闭MySQL JDBC数据库连接");
//        MySQLUtils.closeResource(MySQLHelper.connection);
    }
    
//    public void dropTableAfterMethod(List<String> tableList) throws SQLException {
//        for (String s : tableList) {
//            mySQLHelper.doDropTable(s);
//        }
//    }
    
}
