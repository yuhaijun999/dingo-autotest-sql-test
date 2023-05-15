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

public class TestMySQLBVT {
    public static Connection connection;
    public static String tableName = "mysqlbvttest";

    static {
        try {
            connection = MySQLUtils.getMySQLConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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
        Assert.assertNotNull(connection);
    }
    
    @Test(enabled = true, description = "测试创建表")
    public void test01TableCreate() throws SQLException, IOException, ClassNotFoundException {
        String sql = "create table " + tableName + "(id int, name varchar(20), age int, amount double, primary key(id))";
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
                "(1, 'Alice', 18, 3.5),\n" +
                "(2, 'Betty', 22, 4.1),\n" +
                "(3, 'Cindy', 39, 4.6),\n" +
                "(4, 'Doris', 25, 5.2),\n" +
                "(5, 'Emily', 24, 5.8),\n" +
                "(6, 'Alice', 32, 6.1),\n" +
                "(7, 'Betty', 18, 6.9),\n" +
                "(8, 'Alice', 22, 7.3),\n" +
                "(9, 'Cindy', 25, 3.5)";
        try(Statement statement = connection.createStatement()) {
            int effectedRows = statement.executeUpdate(sql);
            Assert.assertEquals(effectedRows, 9);
        }
    }

    @Test(enabled = true, description = "测试查询数据")
    public void test03Query() throws SQLException {
        String sql = "select id,name,age,amount from " + tableName + " where id < 5";
        String[][] dataArray = {
                {"1", "Alice", "18", "3.5"},
                {"2", "Betty", "22", "4.1"},
                {"3", "Cindy", "39", "4.6"},
                {"4", "Doris", "25", "5.2"}
        };
        List<List> expectedList = expectedOutData(dataArray);
        System.out.println("Expected: " + expectedList);
        
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
                    String s = resultSet.getString(columnName);
                    rowList.add(s);
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
        String updateSql = "update " + tableName + " set age=100 where id=1";
        String querySql = "select * from " + tableName + " where id=1";
        List<List> queryList = new ArrayList<>();
        try(Statement statement = connection.createStatement()) {
            int effectedRows = statement.executeUpdate(updateSql);
            Assert.assertEquals(effectedRows,1);
            
            ResultSet resultSet = statement.executeQuery(querySql);
            while (resultSet.next()) {
                List rowList = new ArrayList<>();
                rowList.add(resultSet.getString("id"));
                rowList.add(resultSet.getString("name"));
                rowList.add(resultSet.getString("age"));
                rowList.add(resultSet.getString("amount"));
                queryList.add(rowList);
            }
            resultSet.close();
        }
        System.out.println("Actual: " + queryList);
        String[][] dataArray = {
                {"1", "Alice", "100", "3.5"}
        };
        List<List> expectedList = expectedOutData(dataArray);
        System.out.println("Expected: " + expectedList);
        Assert.assertEquals(queryList,expectedList);
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
    
    @AfterClass(alwaysRun = true, description = "测试结束后，关闭数据库连接资源")
    public static void tearDownAll() {
        MySQLUtils.closeResource(connection);
    }
    
}
