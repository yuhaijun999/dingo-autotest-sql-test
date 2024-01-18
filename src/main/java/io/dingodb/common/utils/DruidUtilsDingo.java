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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DruidUtilsDingo {
    static final String USER = CommonArgs.getDefaultConnectUser();
    static final String PASS = CommonArgs.getDefaultConnectPass();
    static final String SCHEMANAME = "DINGO";
    private static DataSource source;
    static {
        try {
            Properties properties = new Properties();
            InputStream inputStream = ClassLoader.getSystemClassLoader().getResourceAsStream("druid_dingo.properties");
            properties.load(inputStream);
            String defaultConnectIP = CommonArgs.getDefaultDingoClusterIP();
            String port = CommonArgs.getDefaultExecutorPort();
            String jdbcUrl = "jdbc:dingo:thin://" + defaultConnectIP + ":" + port + "/" + SCHEMANAME + "?timeout=600";
            properties.setProperty("url", jdbcUrl);
            properties.setProperty("username", USER);
            properties.setProperty("password", PASS);
            String poolSize = String.valueOf(Runtime.getRuntime().availableProcessors() * 1);
            System.out.println("Dingo connection pool size: " + poolSize);
            properties.setProperty("initialSize", poolSize);
            properties.setProperty("maxActive", poolSize);
            source = DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static Connection getDruidDingoConnection() throws SQLException {
        Connection connection = source.getConnection();
        return connection;
    }
    
    public static void closeResource(Connection connection, ResultSet rs, Statement stm) {
        try{
            if(rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try{
            if(stm != null) {
                stm.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try{
            if(connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void closeResourcePS(Connection connection, ResultSet rs, PreparedStatement ps) {
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
            if(connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
