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

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

public class DruidUtilsMySQL extends BaseJDBCUtils {
    static final String USER = CommonArgs.getDefaultConnectUser();
    static final String PASS = CommonArgs.getDefaultConnectPass();
    static final String SCHEMANAME = "DINGO";
    private static DataSource source;
    static {
        Properties properties = null;
        try {
            properties = new Properties();
            InputStream inputStream = ClassLoader.getSystemClassLoader().getResourceAsStream("druid_mysql.properties");
            properties.load(inputStream);
            String defaultConnectIP = CommonArgs.getDefaultDingoClusterIP();
            String mysql_port = CommonArgs.getDefaultMySQLPort();
            String timeZone = "Asia/Shanghai";
            String jdbcUrl = "jdbc:mysql://" + defaultConnectIP + ":" + mysql_port + "/" + SCHEMANAME + "?serverTimezone=" + timeZone + "&connectTimeout=120000&useServerPrepStmts=true&cachePrepStmts=true&useSSL=false";
            properties.setProperty("url", jdbcUrl);
            properties.setProperty("username", USER);
            properties.setProperty("password", PASS);
            String poolSize = String.valueOf(Runtime.getRuntime().availableProcessors() * 1);
            System.out.println("Mysql connection pool size: " + poolSize);
            properties.setProperty("initialSize", poolSize);
            properties.setProperty("maxActive", poolSize);
            source = DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static Connection getDruidMySQLConnection() throws SQLException {
        Connection myConnection = source.getConnection();
        return myConnection;
    }

    private static DataSource sourceNoSchema;
    public static Connection getDruidMySQLConnectionWithoutSchema() throws SQLException {
        Properties properties = null;
        try {
            properties = new Properties();
            InputStream inputStream = ClassLoader.getSystemClassLoader().getResourceAsStream("druid_mysql.properties");
            properties.load(inputStream);
            String defaultConnectIP = CommonArgs.getDefaultDingoClusterIP();
            String mysql_port = CommonArgs.getDefaultMySQLPort();
            String timeZone = "Asia/Shanghai";
            String jdbcUrl = "jdbc:mysql://" + defaultConnectIP + ":" + mysql_port + "?serverTimezone=" + timeZone + "&connectTimeout=120000&useServerPrepStmts=true&cachePrepStmts=true";
            properties.setProperty("url", jdbcUrl);
            properties.setProperty("username", USER);
            properties.setProperty("password", PASS);
            properties.setProperty("initialSize", "2");
            properties.setProperty("maxActive", "2");
            sourceNoSchema = DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Connection myConnection = sourceNoSchema.getConnection();
        return myConnection;
    }

    private static DataSource sourceWithSchema;
    public static Connection getDruidMySQLConnectionWithSchema(String schemaName) throws SQLException {
        Properties properties = null;
        try {
            properties = new Properties();
            InputStream inputStream = ClassLoader.getSystemClassLoader().getResourceAsStream("druid_mysql.properties");
            properties.load(inputStream);
            String defaultConnectIP = CommonArgs.getDefaultDingoClusterIP();
            String mysql_port = CommonArgs.getDefaultMySQLPort();
            String timeZone = "Asia/Shanghai";
            String jdbcUrl = "jdbc:mysql://" + defaultConnectIP + ":" + mysql_port + "/" + schemaName + "?serverTimezone=" + timeZone + "&connectTimeout=120000&useServerPrepStmts=true&cachePrepStmts=true";
            properties.setProperty("url", jdbcUrl);
            properties.setProperty("username", USER);
            properties.setProperty("password", PASS);
            properties.setProperty("initialSize", "2");
            properties.setProperty("maxActive", "2");
            sourceNoSchema = DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Connection myConnection = sourceWithSchema.getConnection();
        return myConnection;
    }

    private static DataSource sourceWithNotRoot;
    public static Connection getDruidMySQLConnectionWithNotRoot(String userName, String passwd) throws SQLException {
        Properties properties = null;
        try {
            properties = new Properties();
            InputStream inputStream = ClassLoader.getSystemClassLoader().getResourceAsStream("druid_mysql.properties");
            properties.load(inputStream);
            String defaultConnectIP = CommonArgs.getDefaultDingoClusterIP();
            String mysql_port = CommonArgs.getDefaultMySQLPort();
            String timeZone = "Asia/Shanghai";
            String jdbcUrl = "jdbc:mysql://" + defaultConnectIP + ":" + mysql_port + "/" + SCHEMANAME + "?serverTimezone=" + timeZone + "&useServerPrepStmts=true&cachePrepStmts=true";
            properties.setProperty("url", jdbcUrl);
            properties.setProperty("username", userName);
            properties.setProperty("password", passwd);
            properties.setProperty("initialSize", "1");
            properties.setProperty("maxActive", "1");
            sourceWithNotRoot = DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Connection myConnection = sourceWithNotRoot.getConnection();
        return myConnection;
    }

    public static List<String> getSchemaList() throws SQLException {
        Connection connection = getDruidMySQLConnection();
        return baseGetSchemaList(connection);
    }

    public static List<String> getTableList() throws SQLException {
        Connection connection = getDruidMySQLConnection();
        return baseGetTableList(connection, SCHEMANAME);
    }

    public static List<String> getTableListWithSchema(String schemaName) throws SQLException {
        Connection connection = getDruidMySQLConnection();
        return baseGetTableListWithSchema(connection, schemaName);
    }

    public static List<String> getAllTableList() throws SQLException {
        Connection connection = getDruidMySQLConnection();
        return baseGetAllTableList(connection);
    }
}
