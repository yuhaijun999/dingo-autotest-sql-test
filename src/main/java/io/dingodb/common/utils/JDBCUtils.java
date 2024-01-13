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
    
    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        String schemaName = properties.getProperty("SCHEMA");
        String timeout = properties.getProperty("timeout");
        String JDBC_DRIVER = properties.getProperty("JDBC_Driver");
//        String port = properties.getProperty("port");
        String port = CommonArgs.getDefaultExecutorPort();
        String defaultConnectIP = CommonArgs.getDefaultDingoClusterIP();
//        String defaultConnectIP = "172.20.61.101";
        String connectUrl = "jdbc:dingo:thin:url=" + defaultConnectIP + ":" + port + "/" + schemaName + "?timeout=" + timeout;
        Class.forName(JDBC_DRIVER);
        Connection connection = DriverManager.getConnection(connectUrl, USER, PASS);
        return connection;
    }

    public Connection getDingoConnectionInstance() throws ClassNotFoundException, SQLException {
        String schemaName = properties.getProperty("SCHEMA");
        String timeout = properties.getProperty("timeout");
        String JDBC_DRIVER = properties.getProperty("JDBC_Driver");
//        String port = properties.getProperty("port");
        String port = CommonArgs.getDefaultExecutorPort();
        String defaultConnectIP = CommonArgs.getDefaultDingoClusterIP();
//        String defaultConnectIP = "172.20.61.101";
        String connectUrl = "jdbc:dingo:thin:url=" + defaultConnectIP + ":" + port + "/" + schemaName + "?timeout=" + timeout;
        Class.forName(JDBC_DRIVER);
        Connection connection = DriverManager.getConnection(connectUrl, USER, PASS);
        return connection;
    }
    
    public static Connection getConnectionWithoutSchema() throws ClassNotFoundException, SQLException {
        String timeout = properties.getProperty("timeout");
        String JDBC_DRIVER = properties.getProperty("JDBC_Driver");
//        String port = properties.getProperty("port");
        String port = CommonArgs.getDefaultExecutorPort();
        String defaultConnectIP = CommonArgs.getDefaultDingoClusterIP();
//        String defaultConnectIP = "172.20.61.101";
        String connectUrl = "jdbc:dingo:thin:url=" + defaultConnectIP + ":" + port + "?timeout=" + timeout;
        Class.forName(JDBC_DRIVER);
        Connection connection = DriverManager.getConnection(connectUrl, USER, PASS);
        return connection;
    }
    
    public static Connection getConnectionWithSchema(String schemaName) throws ClassNotFoundException, SQLException {
        String timeout = properties.getProperty("timeout");
        String JDBC_DRIVER = properties.getProperty("JDBC_Driver");
        String port = CommonArgs.getDefaultExecutorPort();
        String defaultConnectIP = CommonArgs.getDefaultDingoClusterIP();
//        String defaultConnectIP = "172.20.61.101";
        String connectUrl = "jdbc:dingo:thin:url=" + defaultConnectIP + ":" + port + "/" + schemaName + "?timeout=" + timeout;
        Class.forName(JDBC_DRIVER);
        Connection connection = DriverManager.getConnection(connectUrl, USER, PASS);
        return connection;
    }
    
    public static Connection getConnectionWithNotRoot(String userName, String passwd) throws ClassNotFoundException, SQLException {
        String schemaName = properties.getProperty("SCHEMA");
        String JDBC_DRIVER = properties.getProperty("JDBC_Driver");
//        String port = properties.getProperty("port");
        String port = CommonArgs.getDefaultExecutorPort();
        String defaultConnectIP = CommonArgs.getDefaultDingoClusterIP();
//        String defaultConnectIP = "172.20.61.101";
        String connectUrl = "jdbc:dingo:thin:url=" + defaultConnectIP + ":" + port + "/" + schemaName;
        Class.forName(JDBC_DRIVER);
        Connection connection = DriverManager.getConnection(connectUrl, userName, passwd);
        return connection;
    }
    
    public static List<String> getSchemaList() throws SQLException, ClassNotFoundException, IOException {
        Connection connection = getConnectionWithoutSchema();
        DatabaseMetaData dmd = connection.getMetaData();
        ResultSet resultSetSchema = dmd.getSchemas();
        List<String> schemaList = new ArrayList<>();
        while (resultSetSchema.next()) {
            schemaList.add(resultSetSchema.getString(1).toUpperCase());
        }
        
        resultSetSchema.close();
        return schemaList;
    }
    
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
    
    public static List<String> getTableListWithSchema(String shemaName) throws SQLException, ClassNotFoundException, IOException {
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
        ResultSet rst = dmd.getTables(null, shemaName, "%", types);
        while (rst.next()) {
            tableList.add(rst.getString("TABLE_NAME").toUpperCase());
        }
        rst.close();
        resultSetSchema.close();
        return tableList;
    }
    
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
    
    public static void closeResource(Connection conn) {
        try{
            if(conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
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
