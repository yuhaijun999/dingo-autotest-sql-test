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

import io.dingodb.common.utils.JDBCUtils;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import utils.IniReader;

import java.sql.Connection;

public class BaseTestSuite {
    public static IniReader iniReader;
    public static IniReader iniReaderBTREE;
    public static IniReader iniReaderTXNBTREE;
//    public static SQLHelper sqlHelper;
    @BeforeSuite(alwaysRun = true, description = "所有测试开始前的准备工作")
    public static void beforeSuite() {
//        sqlHelper = new SQLHelper();
        System.out.println("所有测试开始前，验证数据库连接正常");
//        Assert.assertNotNull(SQLHelper.connection);
        Connection connection = null;
        try {
//            connection = DruidUtilsDingo.getDruidDingoConnection();
            connection = JDBCUtils.getConnection();
            Assert.assertNotNull(connection);
            iniReader = new IniReader("src/test/resources/io.dingodb.test/ini/dingo_lsm.ini");
            iniReaderBTREE = new IniReader("src/test/resources/io.dingodb.test/ini/dingo_btree.ini");
            iniReaderTXNBTREE = new IniReader("src/test/resources/io.dingodb.test/ini/dingo_txn_btree.ini");
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            JDBCUtils.closeResource(connection);
        }
    }

    @AfterSuite(alwaysRun = true, description = "所有测试结束后的操作")
    public static void afterSuite() {
        System.out.println("所有测试结束后，关闭数据库连接");
//        JDBCUtils.closeResource(SQLHelper.connection);
    }
    
//    public void dropTableAfterMethod(List<String> tableList) throws SQLException {
//        for (String s : tableList) {
//            sqlHelper.doDropTable(SQLHelper.connection, s);
//        }
//    }
    
}
