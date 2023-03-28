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

package io.dingodb.test;

import io.dingodb.common.utils.JDBCUtils;
import io.dingodb.dailytest.SQLHelper;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import utils.IniReader;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class BaseTestSuite {
    public static IniReader iniReader;
    public static SQLHelper sqlHelper;
    @BeforeSuite(alwaysRun = true, description = "所有测试开始前的准备工作")
    public static void beforeSuite() {
        sqlHelper = new SQLHelper();
        System.out.println("所有测试开始前，验证数据库连接正常");
        Assert.assertNotNull(SQLHelper.connection);
        try {
            iniReader = new IniReader("src/test/resources/io.dingodb.test/ini/my.ini");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @AfterSuite(alwaysRun = true, description = "所有测试结束后的操作")
    public static void afterSuite() {
        System.out.println("所有测试结束后，关闭数据库连接");
        JDBCUtils.closeResource(SQLHelper.connection);
    }
    
    public void dropTableAfterMethod(List<String> tableList) throws SQLException {
        for (String s : tableList) {
            sqlHelper.doDropTable(s);
        }
    }
    
}
