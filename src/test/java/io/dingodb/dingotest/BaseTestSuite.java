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

import io.dingodb.common.utils.DruidUtilsDingo;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import utils.IniReader;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class BaseTestSuite {
    public static IniReader iniReader;
    public static IniReader iniReaderBTREE;
    public static IniReader iniReaderTXNBTREE;
//    public static SQLHelper sqlHelper;
    @BeforeSuite(alwaysRun = true, description = "所有测试开始前的准备工作")
    public static void beforeSuite() throws SQLException {
        System.out.println("所有测试开始前，验证数据库连接正常");
        Connection connection = null;
        try {
            connection = DruidUtilsDingo.getDruidDingoConnection();
            Assert.assertNotNull(connection);
            iniReader = new IniReader("src/test/resources/io.dingodb.test/ini/dingo_lsm.ini");
            iniReaderBTREE = new IniReader("src/test/resources/io.dingodb.test/ini/dingo_btree.ini");
            iniReaderTXNBTREE = new IniReader("src/test/resources/io.dingodb.test/ini/dingo_txn_btree.ini");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            DruidUtilsDingo.closeResource(connection,null,null);
        }
    }

    @AfterSuite(alwaysRun = true, description = "所有测试结束后的操作")
    public static void afterSuite() {
        System.out.println("所有测试结束后，关闭数据库连接");
    }
    
}
