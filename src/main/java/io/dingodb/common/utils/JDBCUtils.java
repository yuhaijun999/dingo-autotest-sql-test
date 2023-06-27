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

package io.dingodb.common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class JDBCUtils {
    static final String USER = CommonArgs.getDefaultConnectUser();
    static final String PASS = CommonArgs.getDefaultConnectPass();

    public static Properties properties = new Properties();
    static {
        InputStream inputStream = JDBCUtils.class.getClassLoader().getResourceAsStream("jdbc.properties");
        try {
            properties.load(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //获取数据库连接
    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        String timeout = properties.getProperty("timeout");
        String JDBC_DRIVER = properties.getProperty("JDBC_Driver");
//        String port = properties.getProperty("port");
        String port = CommonArgs.getDefaultExecutorPort();
        String defaultConnectIP = CommonArgs.getDefaultDingoClusterIP();
//        String defaultConnectIP = "172.20.3.13";
        String connectUrl = "jdbc:dingo:thin:url=" + defaultConnectIP + ":" + port + "/dingo?timeout=" + timeout;

        //加载驱动
        Class.forName(JDBC_DRIVER);

        //获取连接
        Connection connection = DriverManager.getConnection(connectUrl, USER, PASS);

        return connection;
    }

    //使用非root用户连接数据库，获取connection对象
    public static Connection getConnectionWithNotRoot(String userName, String passwd) throws ClassNotFoundException, SQLException {
        String JDBC_DRIVER = properties.getProperty("JDBC_Driver");
//        String port = properties.getProperty("port");
        String port = CommonArgs.getDefaultExecutorPort();
        String defaultConnectIP = CommonArgs.getDefaultDingoClusterIP();
        String connectUrl = "jdbc:dingo:thin:url=" + defaultConnectIP + ":" + port + "/dingo";

        //加载驱动
        Class.forName(JDBC_DRIVER);

        //获取连接
        Connection connection = DriverManager.getConnection(connectUrl, userName, passwd);

        return connection;
    }

    //获取Dingo数据库下的所有数据表
    public static List<String> getTableList() throws SQLException, ClassNotFoundException, IOException {
        Connection connection = getConnection();
        DatabaseMetaData dmd = connection.getMetaData();
        ResultSet resultSetSchema = dmd.getSchemas();
        List<String> schemaList = new ArrayList<>();
        while (resultSetSchema.next()) {
            schemaList.add(resultSetSchema.getString(1));
        }

        List<String> tableList = new ArrayList<String>();
//        ResultSet rst = dmd.getTables(null, schemaList.get(0), "%", null);
        String[] types={"TABLE"};
        ResultSet rst = dmd.getTables(null, "DINGO", "%", types);
        while (rst.next()) {
            tableList.add(rst.getString("TABLE_NAME").toUpperCase());
        }
        rst.close();
        resultSetSchema.close();
        return tableList;
    }

    //获取所有数据库下的所有数据表
    public static List<String> getAllTableList() throws SQLException, ClassNotFoundException, IOException {
        Connection connection = getConnection();
        DatabaseMetaData dmd = connection.getMetaData();
        ResultSet resultSetSchema = dmd.getSchemas();
        List<String> schemaList = new ArrayList<>();
        while (resultSetSchema.next()) {
            schemaList.add(resultSetSchema.getString(1));
        }

        List<String> tableList = new ArrayList<String>();
        String[] types={"TABLE"};
        ResultSet rst = dmd.getTables(null, null, "%", types);
        while (rst.next()) {
            tableList.add(rst.getString("TABLE_NAME").toUpperCase());
        }
        rst.close();
        resultSetSchema.close();
        return tableList;
    }

    //关闭资源: 连接
    public static void closeResource(Connection conn) {
        try{
            if(conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //关闭资源: 连接和statement
    public static void closeResource(Connection conn, Statement ps) {
        try{
            if(ps != null) {
                ps.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try{
            if(conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //关闭资源: resultSet, 连接和statement
    public static void closeResource(ResultSet rs, Connection conn, Statement ps) {
        try{
            if(rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try{
            if(ps != null) {
                ps.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try{
            if(conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
