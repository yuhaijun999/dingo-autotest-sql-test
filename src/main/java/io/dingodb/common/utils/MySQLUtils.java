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

public class MySQLUtils {
    static final String USER = CommonArgs.getDefaultConnectUser();
    static final String PASS = CommonArgs.getDefaultConnectPass();
    public static Properties properties = new Properties();
    static {
        InputStream inputStream = MySQLUtils.class.getClassLoader().getResourceAsStream("mysql.properties");
        try {
            properties.load(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static Connection getMySQLConnection() throws IOException, ClassNotFoundException, SQLException {
        String schemaName=properties.getProperty("SCHEMA");
        String MySQL_JDBC_DRIVER = properties.getProperty("MySQL_JDBC_Driver");
//        String mysql_port = properties.getProperty("MySQL_Port");
        String mysql_port = CommonArgs.getDefaultMySQLPort();
        String timeout = properties.getProperty("MAX_EXECUTION_TIME");
        String timezone = properties.getProperty("SERVER_TIMEZONE");
        String defaultConnectIP = CommonArgs.getDefaultDingoClusterIP();
//        String defaultConnectIP = "172.20.61.101";
        Properties props = new Properties();
        props.setProperty("useServerPrepStmts", "true");
        props.setProperty("cachePrepStmts", "true");
        props.setProperty("user", USER);
        props.setProperty("password", PASS);
//        String connectUrl = "jdbc:mysql://" + defaultConnectIP + ":" + mysql_port + "/dingo?max_execution_time=" + timeout+"&wait_timeout=60&connectTimeout=60000";
        String connectUrl = "jdbc:mysql://" + defaultConnectIP + ":" + mysql_port + "/" + schemaName + "?serverTimezone=" + timezone;
        Class.forName(MySQL_JDBC_DRIVER);
        Connection myConnection = DriverManager.getConnection(connectUrl, props);
        return myConnection;
    }

    public Connection getMySQLConnectionInstance() throws IOException, ClassNotFoundException, SQLException {
        String schemaName=properties.getProperty("SCHEMA");
        String MySQL_JDBC_DRIVER = properties.getProperty("MySQL_JDBC_Driver");
//        String mysql_port = properties.getProperty("MySQL_Port");
        String mysql_port = CommonArgs.getDefaultMySQLPort();
        String timeout = properties.getProperty("MAX_EXECUTION_TIME");
        String timezone = properties.getProperty("SERVER_TIMEZONE");
        String defaultConnectIP = CommonArgs.getDefaultDingoClusterIP();
//        String defaultConnectIP = "172.20.61.101";
        Properties props = new Properties();
        props.setProperty("useServerPrepStmts", "true");
        props.setProperty("cachePrepStmts", "true");
        props.setProperty("user", USER);
        props.setProperty("password", PASS);
//        String connectUrl = "jdbc:mysql://" + defaultConnectIP + ":" + mysql_port + "/dingo?max_execution_time=" + timeout+"&wait_timeout=60&connectTimeout=60000";
        String connectUrl = "jdbc:mysql://" + defaultConnectIP + ":" + mysql_port + "/" + schemaName + "?serverTimezone=" + timezone;
        Class.forName(MySQL_JDBC_DRIVER);
        Connection myConnection = DriverManager.getConnection(connectUrl, props);
        return myConnection;
    }
    
    public static Connection getMySQLConnectionWithoutSchema() throws IOException, ClassNotFoundException, SQLException {
        String MySQL_JDBC_DRIVER = properties.getProperty("MySQL_JDBC_Driver");
        String mysql_port = CommonArgs.getDefaultMySQLPort();
        String timeout = properties.getProperty("MAX_EXECUTION_TIME");
        String timezone = properties.getProperty("SERVER_TIMEZONE");
        String defaultConnectIP = CommonArgs.getDefaultDingoClusterIP();
//        String defaultConnectIP = "172.20.61.101";
        Properties props = new Properties();
        props.setProperty("useServerPrepStmts", "true");
        props.setProperty("cachePrepStmts", "true");
        props.setProperty("user", USER);
        props.setProperty("password", PASS);
        String connectUrl = "jdbc:mysql://" + defaultConnectIP + ":" + mysql_port + "?serverTimezone=" + timezone;
        Class.forName(MySQL_JDBC_DRIVER);
        Connection myConnection = DriverManager.getConnection(connectUrl, props);
        return myConnection;
    }
    
    public static Connection getMySQLConnectionWithSchema(String schemaName) throws IOException, ClassNotFoundException, SQLException {
        String MySQL_JDBC_DRIVER = properties.getProperty("MySQL_JDBC_Driver");
        String mysql_port = CommonArgs.getDefaultMySQLPort();
        String timeout = properties.getProperty("MAX_EXECUTION_TIME");
        String timezone = properties.getProperty("SERVER_TIMEZONE");
        String defaultConnectIP = CommonArgs.getDefaultDingoClusterIP();
//        String defaultConnectIP = "172.20.61.101";
        Properties props = new Properties();
        props.setProperty("useServerPrepStmts", "true");
        props.setProperty("cachePrepStmts", "true");
        props.setProperty("user", USER);
        props.setProperty("password", PASS);
        String connectUrl = "jdbc:mysql://" + defaultConnectIP + ":" + mysql_port + "/" + schemaName + "?serverTimezone=" + timezone;
        Class.forName(MySQL_JDBC_DRIVER);
        Connection myConnection = DriverManager.getConnection(connectUrl, props);
        return myConnection;
    }
    
    public static Connection getConnectionWithNotRoot(String userName, String passwd) throws ClassNotFoundException, SQLException {
        String schemaName=properties.getProperty("SCHEMA");
        String MySQL_JDBC_DRIVER = properties.getProperty("MySQL_JDBC_Driver");
//        String mysql_port = properties.getProperty("MySQL_Port");
        String mysql_port = CommonArgs.getDefaultMySQLPort();
        String timeout = properties.getProperty("MAX_EXECUTION_TIME");
        String timezone = properties.getProperty("SERVER_TIMEZONE");
        String defaultConnectIP = CommonArgs.getDefaultDingoClusterIP();
//        String defaultConnectIP = "172.20.61.101";
        String connectUrl = "jdbc:mysql://" + defaultConnectIP + ":" + mysql_port + "/" + schemaName + "?serverTimezone=" + timezone;
        Class.forName(MySQL_JDBC_DRIVER);
        Connection myConnection = DriverManager.getConnection(connectUrl, userName, passwd);
        return myConnection;
    }
    
    public static List<String> getSchemaList() throws SQLException, ClassNotFoundException, IOException {
        Connection connection = getMySQLConnectionWithoutSchema();
        DatabaseMetaData dmd = connection.getMetaData();
        ResultSet resultSetSchema = dmd.getCatalogs();
        List<String> schemaList = new ArrayList<>();
        while (resultSetSchema.next()) {
            schemaList.add(resultSetSchema.getString(1).toUpperCase());
        }
        resultSetSchema.close();
        closeResource(connection);
        return schemaList;
    }
    
    public static List<String> getTableList() throws SQLException, ClassNotFoundException, IOException {
        Connection connection = getMySQLConnection();
        DatabaseMetaData dmd = connection.getMetaData();
        ResultSet resultSetSchema = dmd.getSchemas();
        List<String> schemaList = new ArrayList<>();
        while (resultSetSchema.next()) {
            schemaList.add(resultSetSchema.getString(1));
        }

        List<String> tableList = new ArrayList<String>();
        String[] types={"TABLE"};
        ResultSet rst = dmd.getTables(null, "DINGO", null, types);
        while (rst.next()) {
            tableList.add(rst.getString("TABLE_NAME").toUpperCase());
        }
        rst.close();
        resultSetSchema.close();
        closeResource(connection);
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
