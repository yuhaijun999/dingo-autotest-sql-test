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

import io.dingodb.common.utils.MySQLUtils;
import io.dingodb.dailytest.MySQLHelper;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TestBVTMySQL extends BaseTestSuiteMySQL {
    private static MySQLHelper mySQLHelper;
    public static Connection connection;
    public static String tableName = "mysqlbvttest";

    public static List<List> expectedOutData(String[][] dataArray) {
        List<List> expectedList = new ArrayList<List>();
        for(int i = 0; i < dataArray.length; i++) {
            List columnList = new ArrayList();
            for (int j = 0; j < dataArray[i].length; j++) {
                columnList.add(dataArray[i][j]);
            }
            expectedList.add(columnList);
        }
        return expectedList;
    }
    
    @BeforeClass(alwaysRun = true, description = "测试开始前验证数据库连接")
    public static void setUpAll() {
        mySQLHelper = new MySQLHelper();
        connection = MySQLHelper.connection;
        Assert.assertNotNull(connection);
    }

    @AfterClass(alwaysRun = true, description = "测试结束后，关闭数据库连接资源")
    public static void tearDownAll() {

    }
    
    @Test(enabled = true, description = "测试创建表")
    public void test01TableCreate() throws SQLException, IOException, ClassNotFoundException {
        String sql = "create table " + tableName + "(id int, name varchar(20), age int, amount double, birthday date, create_time time, update_time timestamp, is_delete boolean, primary key(id))";
        try(Statement statement = connection.createStatement();) {
            statement.execute(sql);
            List<String> tableList = MySQLUtils.getTableList();
            System.out.println("TableList: " + tableList);
            Assert.assertTrue(tableList.contains(tableName));
            System.out.println(tableList);
        }
    }

    @Test(enabled = true, description = "测试插入数据")
    public void test02InsertValue() throws SQLException {
        String sql = "insert into " + tableName + " values" + 
                "(1, 'Alice', 18, 3.5, '1998-04-06', '08:10:10', '2022-04-08 18:05:07', true),\n" +
                "(2, 'Betty', 22, 4.1, '1988-02-05', '16:15:08', '2000-02-29 00:00:00', true),\n" +
                "(3, 'Cindy', 39, 4.6, '2022-03-04', '07:03:15', '1999-02-28 23:59:59', false),\n" +
                "(4, 'Doris', 25, 5.2, '1967-07-16', '01:02:03', '1952-12-31 12:12:12', true),\n" +
                "(5, 'Emily', 24, 5.8, '1949-01-01', '00:30:08', '2022-12-01 01:02:03', false),\n" +
                "(6, 'Alice', 32, 6.1, '2015-09-10', '03:45:10', '2001-11-11 18:05:07', false),\n" +
                "(7, 'Betty', 18, 6.9, '2018-05-31', '21:00:00', '2000-01-01 00:00:00', true),\n" +
                "(8, 'Alice', 22, 7.3, '1987-12-11', '11:11:00', '1997-07-01 00:00:00', true),\n" +
                "(9, 'Cindy', 25, 3.5, '2007-08-15', '22:10:10', '2020-02-29 05:53:44', false)";
        try(Statement statement = connection.createStatement()) {
            int effectedRows = statement.executeUpdate(sql);
            Assert.assertEquals(effectedRows, 9);
        }
    }

    @Test(enabled = true, description = "测试查询数据")
    public void test03Query() throws SQLException {
        String[][] dataArray = {
                {"1", "Alice", "18", "3.5", "1998-04-06", "08:10:10", "2022-04-08 18:05:07", "true"},
                {"2", "Betty", "22", "4.1", "1988-02-05", "16:15:08", "2000-02-29 00:00:00", "true"},
                {"3", "Cindy", "39", "4.6", "2022-03-04", "07:03:15", "1999-02-28 23:59:59", "false"},
                {"4", "Doris", "25", "5.2", "1967-07-16", "01:02:03", "1952-12-31 12:12:12", "true"}
        };
        List<List> expectedList = expectedOutData(dataArray);
        System.out.println("Expected: " + expectedList);

        String sql = "select * from " + tableName + " where id < 5";
        List<List> queryList = new ArrayList<>();
        try(Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            while (resultSet.next()) {
                List rowList = new ArrayList<>();
                for (int i = 1; i <= columnCount; i++) {
                    String columnTypeName = metaData.getColumnTypeName(i);
//                    System.out.println(columnTypeName);
                    String columnName = metaData.getColumnName(i);
//                    System.out.println(columnName);
                    if (resultSet.getObject(columnName) == null) {
                        rowList.add(String.valueOf(resultSet.getObject(columnName)));
                    } else {
                        String s = resultSet.getObject(columnName).toString();
                        rowList.add(s);
                    }
                }
                queryList.add(rowList);
            }
            resultSet.close();
        }
        System.out.println("Actual: " + queryList);
        Assert.assertTrue(queryList.containsAll(expectedList));
        Assert.assertTrue(expectedList.containsAll(queryList));
    }

    @Test(enabled = true, description = "测试更新数据")
    public void test04Update() throws SQLException {
        String[][] dataArray = {
                {"1", "Alice", "100", "3.5", "1998-04-06", "08:10:10", "2022-04-08 18:05:07"},
                {"4", "Doris", "100", "5.2", "1967-07-16", "01:02:03", "1952-12-31 12:12:12"}
        };
        List<List> expectedList = expectedOutData(dataArray);
        System.out.println("Expected: " + expectedList);
        
        String updateSql = "update " + tableName + " set age=100 where id=1 or id=4";
        String querySql = "select id,name,age,amount,birthday,create_time,update_time from " + tableName + " where id=1 or id=4";
        List<List> queryList = new ArrayList<>();
        try(Statement statement = connection.createStatement()) {
            int effectedRows = statement.executeUpdate(updateSql);
            Assert.assertEquals(effectedRows,2);
            
            ResultSet resultSet = statement.executeQuery(querySql);
            while (resultSet.next()) {
                List rowList = new ArrayList<>();
                rowList.add(resultSet.getString("id"));
                rowList.add(resultSet.getString("name"));
                rowList.add(resultSet.getString("age"));
                rowList.add(resultSet.getString("amount"));
                rowList.add(resultSet.getDate("birthday").toString());
                rowList.add(resultSet.getTime("create_time").toString());
                rowList.add(resultSet.getTimestamp("update_time").toString());
                queryList.add(rowList);
            }
            resultSet.close();
        }
        System.out.println("Actual: " + queryList);
        Assert.assertTrue(queryList.containsAll(expectedList));
        Assert.assertTrue(expectedList.containsAll(queryList));
    }

    @Test(enabled = true, description = "测试删除数据")
    public void test05Delete() throws SQLException {
        String deleteSql = "delete from " + tableName;
        String querySql = "select * from " + tableName;
        try(Statement statement = connection.createStatement()) {
            int effectedRows = statement.executeUpdate(deleteSql);
            Assert.assertEquals(effectedRows,9);

            ResultSet resultSet = statement.executeQuery(querySql);
            Assert.assertFalse(resultSet.next());
            resultSet.close();
        }
    }
    

    @Test(enabled = true, description = "删除表")
    public void test06DropTable() throws SQLException, IOException, ClassNotFoundException {
        String sql = "drop table " + tableName;
        try(Statement statement = connection.createStatement();) {
            statement.execute(sql);
            List<String> tableList = MySQLUtils.getTableList();
            Assert.assertFalse(tableList.contains(tableName));
            System.out.println(tableList);
        }
    }
    
}