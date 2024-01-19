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

package io.dingodb.dailytest;

import io.dingodb.common.utils.DruidUtilsMySQL;
import io.dingodb.common.utils.GetRandomValue;
import org.apache.commons.io.IOUtils;

import javax.annotation.Nonnull;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MySQLHelperDruid {
    public MySQLHelperDruid() {}
    
    public void execFile(@Nonnull InputStream stream, String replaceTableName) throws IOException, SQLException {
        Connection connection = null;
        Statement statement = null;
        String sql = IOUtils.toString(stream, StandardCharsets.UTF_8);
        String exeSql = sql.replace("$table", replaceTableName);
        try{
            connection = DruidUtilsMySQL.getDruidMySQLConnection();
            statement = connection.createStatement();
            execSql(statement, exeSql);
        } finally {
            DruidUtilsMySQL.closeResource(connection, null, statement);
        }
    }

    public static void execSql(Statement statement, @Nonnull String sql) throws SQLException {
        for (String s : sql.split(";")) {
            if (!s.trim().isEmpty()) {
                statement.execute(s);
            }
        }
    }

    public void execSql(String sql) throws SQLException {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = DruidUtilsMySQL.getDruidMySQLConnection();
            statement = connection.createStatement();
            if (!sql.trim().isEmpty()) {
                statement.execute(sql);
            }
        } finally {
            DruidUtilsMySQL.closeResource(connection, null, statement);
        }
    }

    public void execBatchSqlWithState(String sql) throws SQLException {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = DruidUtilsMySQL.getDruidMySQLConnection();
            statement = connection.createStatement();
            for (String s : sql.split(";")) {
                if (!s.trim().isEmpty()) {
                    statement.execute(s);
                }
            }
        } finally {
            DruidUtilsMySQL.closeResource(connection, null, statement);
        }
    }

    public void execBatchSql(Statement statement, String sql) throws SQLException {
        for (String s : sql.split(";")) {
            if (!s.trim().isEmpty()) {
                statement.execute(s);
            }
        }
    }
    

    //dml操作只返回影响行数
    public int doDMLReturnRows(String sql) throws SQLException {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = DruidUtilsMySQL.getDruidMySQLConnection();
            statement = connection.createStatement();
            int rowCount = statement.executeUpdate(sql);
            return rowCount;
        } finally {
            DruidUtilsMySQL.closeResource(connection, null, statement);
        }
    }

    //更新或删除表数据并查询变更后数据，输出包含表头
    public List<List<String>> doDMLAndQueryWithHead(String dmlSql, String querySql) throws SQLException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DruidUtilsMySQL.getDruidMySQLConnection();
            statement = connection.createStatement();
            List<List<String>> rowList = new ArrayList<>();
            List<String> effectRows = new ArrayList<>();
            int effectCnt = statement.executeUpdate(dmlSql);
            effectRows.add(String.valueOf(effectCnt));
            rowList.add(effectRows);

            //更新后查询表数据
            resultSet = statement.executeQuery(querySql);
            List<List<String>> resultList = getResultListWithLabel(resultSet, rowList);
            return resultList;
        } finally {
            DruidUtilsMySQL.closeResource(connection, resultSet, statement);
        }
    }
    
    //查询返回结果行数
    public int queryWithRowsNumReturn(String sql) throws SQLException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DruidUtilsMySQL.getDruidMySQLConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            int rowCount = 0;
            while (resultSet.next()) {
                rowCount ++;
            }
            return rowCount;
        } finally {
            DruidUtilsMySQL.closeResource(connection, resultSet, statement);
        }
    }


    //通过statement查询表数据, 返回Object
    public Object queryWithObjReturn(String sql) throws SQLException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DruidUtilsMySQL.getDruidMySQLConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            Object queryResult = null;
            while (resultSet.next()) {
                queryResult = resultSet.getObject(1);
            }
            return queryResult;
        } finally {
            DruidUtilsMySQL.closeResource(connection, resultSet, statement);
        }
    }

    //通过statement查询表数据, 返回String
    public String queryWithStrReturn(String sql) throws SQLException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DruidUtilsMySQL.getDruidMySQLConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            String queryStr = null;
            while (resultSet.next()) {
                if (resultSet.getObject(1) == null) {
                    queryStr = null;
                } else {
                    queryStr = resultSet.getObject(1).toString();
                }
            }
            return queryStr;
        } finally {
            DruidUtilsMySQL.closeResource(connection, resultSet, statement);
        }
    }

    //通过statement查询表数据, 返回Double
    public Double queryWithDoubleReturn(String sql) throws SQLException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DruidUtilsMySQL.getDruidMySQLConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            Double queryValue = null;
            while (resultSet.next()) {
                if (resultSet.getObject(1) == null) {
                    queryValue = null;
                } else {
                    queryValue = resultSet.getDouble(1);
                }
            }
            return queryValue;
        } finally {
            DruidUtilsMySQL.closeResource(connection, resultSet, statement);
        }
    }

    //通过statement查询表数据, 返回List<List>,不含表头
    public List<List<String>> statementQuery(String sql) throws SQLException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DruidUtilsMySQL.getDruidMySQLConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            List<List<String>> resultList = getResultListWithoutLabel(resultSet);
            return resultList;
        } finally {
            DruidUtilsMySQL.closeResource(connection, resultSet, statement);
        }
    }

    //通过statement查询表数据, 返回List<List<String>>,包含输出表头
    public List<List<String>> statementQueryWithHead(String sql) throws SQLException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DruidUtilsMySQL.getDruidMySQLConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            List<List<String>> resultList = getResultListWithLabel(resultSet);
            return resultList;
        } finally {
            DruidUtilsMySQL.closeResource(connection, resultSet, statement);
        }
    }

    //通过preparedStatement查询表数据,包含表头
    public List<List<String>> preparedStatementQuery(String sql, String[] value_type, Object ... values) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try {
            connection = DruidUtilsMySQL.getDruidMySQLConnection();
            ps = connection.prepareStatement(sql);
            for (int i = 0; i < values.length; i++) {
                switch (value_type[i].trim()) {
                    case "Varchar": {
                        ps.setObject(i + 1, values[i]);
                        break;
                    }
                    case "Char": {
                        ps.setObject(i + 1, values[i]);
                        break;
                    }
                    case "Integer": {
                        ps.setObject(i + 1, Integer.parseInt((String) values[i]));
                        break;
                    }
                    case "Bigint": {
                        ps.setObject(i + 1, Long.parseLong((String) values[i]));
                        break;
                    }
                    case "Float": {
                        ps.setObject(i + 1, Float.parseFloat((String) values[i]));
                        break;
                    }
                    case "Double": {
                        ps.setObject(i + 1, Double.parseDouble((String) values[i]));
                        break;
                    }
                    case "Date": {
                        String yearNum = ((String) values[i]).substring(0,4);
                        String monthNum = ((String) values[i]).substring(4,6);
                        String dayNum = ((String) values[i]).substring(6,8);
                        LocalDate localDate = LocalDate.of(Integer.parseInt(yearNum),Integer.parseInt(monthNum),Integer.parseInt(dayNum));
                        ps.setObject(i + 1, Date.valueOf(localDate));
                        break;
                    }
                    case "Time": {
                        String hourNum = ((String) values[i]).substring(0,2);
                        String minuteNum = ((String) values[i]).substring(2,4);
                        String secondNum = ((String) values[i]).substring(4,6);
                        LocalTime localTime = LocalTime.of(Integer.parseInt(hourNum),Integer.parseInt(minuteNum),Integer.parseInt(secondNum));
                        ps.setObject(i + 1, Time.valueOf(localTime));
                        break;
                    }
                    case "Timestamp": {
                        String yearNum = ((String) values[i]).substring(0,4);
                        String monthNum = ((String) values[i]).substring(4,6);
                        String dayNum = ((String) values[i]).substring(6,8);
                        String hourNum = ((String) values[i]).substring(8,10);
                        String minuteNum = ((String) values[i]).substring(10,12);
                        String secondNum = ((String) values[i]).substring(12,14);
                        LocalDateTime localDateTime = LocalDateTime.of(
                                Integer.parseInt(yearNum),Integer.parseInt(monthNum),Integer.parseInt(dayNum),
                                Integer.parseInt(hourNum),Integer.parseInt(minuteNum),Integer.parseInt(secondNum));
                        ps.setObject(i + 1, Timestamp.valueOf(localDateTime));
                        break;
                    }
                    case "Boolean": {
                        ps.setObject(i + 1, Boolean.parseBoolean((String) values[i]));
                        break;
                    }
                }
                
            }
            resultSet = ps.executeQuery();
            List<List<String>> resultList = getResultListWithLabel(resultSet);
            return resultList;
        } finally {
            DruidUtilsMySQL.closeResourcePS(connection, resultSet, ps);
        }
    }

    //通过preparedStatement对表进行dml操作,只返回dml影响的行数
    public int preparedStatementDMLGetRows(String dml_sql, String[] value_type, Object ... values) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = DruidUtilsMySQL.getDruidMySQLConnection();
            ps = connection.prepareStatement(dml_sql);
            for (int i = 0; i < values.length; i++) {
                switch (value_type[i].trim()) {
                    case "Varchar": {
                        ps.setObject(i + 1, values[i]);
                        break;
                    }
                    case "Char": {
                        ps.setObject(i + 1, values[i]);
                        break;
                    }
                    case "Integer": {
                        ps.setObject(i + 1, Integer.parseInt((String) values[i]));
                        break;
                    }
                    case "Bigint": {
                        ps.setObject(i + 1, Long.parseLong((String) values[i]));
                        break;
                    }
                    case "Float": {
                        ps.setObject(i + 1, Float.parseFloat((String) values[i]));
                        break;
                    }
                    case "Double": {
                        ps.setObject(i + 1, Double.parseDouble((String) values[i]));
                        break;
                    }
                    case "Date": {
                        String yearNum = ((String) values[i]).substring(0, 4);
                        String monthNum = ((String) values[i]).substring(4, 6);
                        String dayNum = ((String) values[i]).substring(6, 8);
                        LocalDate localDate = LocalDate.of(Integer.parseInt(yearNum), Integer.parseInt(monthNum), Integer.parseInt(dayNum));
                        ps.setObject(i + 1, Date.valueOf(localDate));
                        break;
                    }
                    case "Time": {
                        String hourNum = ((String) values[i]).substring(0, 2);
                        String minuteNum = ((String) values[i]).substring(2, 4);
                        String secondNum = ((String) values[i]).substring(4, 6);
                        LocalTime localTime = LocalTime.of(Integer.parseInt(hourNum), Integer.parseInt(minuteNum), Integer.parseInt(secondNum));
                        ps.setObject(i + 1, Time.valueOf(localTime));
                        break;
                    }
                    case "Timestamp": {
                        String yearNum = ((String) values[i]).substring(0, 4);
                        String monthNum = ((String) values[i]).substring(4, 6);
                        String dayNum = ((String) values[i]).substring(6, 8);
                        String hourNum = ((String) values[i]).substring(8, 10);
                        String minuteNum = ((String) values[i]).substring(10, 12);
                        String secondNum = ((String) values[i]).substring(12, 14);
                        LocalDateTime localDateTime = LocalDateTime.of(
                                Integer.parseInt(yearNum), Integer.parseInt(monthNum), Integer.parseInt(dayNum),
                                Integer.parseInt(hourNum), Integer.parseInt(minuteNum), Integer.parseInt(secondNum));
                        ps.setObject(i + 1, Timestamp.valueOf(localDateTime));
                        break;
                    }
                    case "Boolean": {
                        ps.setObject(i + 1, Boolean.parseBoolean((String) values[i]));
                        break;
                    }
                }
            }
            int effect_rows = ps.executeUpdate();
            return effect_rows;
        } finally {
            DruidUtilsMySQL.closeResourcePS(connection, null, ps);
        }
    }
    

    //通过preparedStatement对表进行dml操作,返回dml影响的行数和dml后的表数据
    public List<List<String>> preparedStatementDMLGetData(String dml_sql, String query_sql, String[] value_type, Object ... values) throws SQLException {
        Connection connection1 = null;
        PreparedStatement ps = null;
        List<List<String>> dml_result = new ArrayList<List<String>>();
        try {
            connection1 = DruidUtilsMySQL.getDruidMySQLConnection();
            ps = connection1.prepareStatement(dml_sql);
            for (int i = 0; i < values.length; i++) {
                switch (value_type[i].trim()) {
                    case "Varchar": {
                        ps.setObject(i + 1, values[i]);
                        break;
                    }
                    case "Char": {
                        ps.setObject(i + 1, values[i]);
                        break;
                    }
                    case "Integer": {
                        ps.setObject(i + 1, Integer.parseInt((String) values[i]));
                        break;
                    }
                    case "Bigint": {
                        ps.setObject(i + 1, Long.parseLong((String) values[i]));
                        break;
                    }
                    case "Float": {
                        ps.setObject(i + 1, Float.parseFloat((String) values[i]));
                        break;
                    }
                    case "Double": {
                        ps.setObject(i + 1, Double.parseDouble((String) values[i]));
                        break;
                    }
                    case "Date": {
                        String yearNum = ((String) values[i]).substring(0, 4);
                        String monthNum = ((String) values[i]).substring(4, 6);
                        String dayNum = ((String) values[i]).substring(6, 8);
                        LocalDate localDate = LocalDate.of(Integer.parseInt(yearNum), Integer.parseInt(monthNum), Integer.parseInt(dayNum));
                        ps.setObject(i + 1, Date.valueOf(localDate));
                        break;
                    }
                    case "Time": {
                        String hourNum = ((String) values[i]).substring(0, 2);
                        String minuteNum = ((String) values[i]).substring(2, 4);
                        String secondNum = ((String) values[i]).substring(4, 6);
                        LocalTime localTime = LocalTime.of(Integer.parseInt(hourNum), Integer.parseInt(minuteNum), Integer.parseInt(secondNum));
                        ps.setObject(i + 1, Time.valueOf(localTime));
                        break;
                    }
                    case "Timestamp": {
                        String yearNum = ((String) values[i]).substring(0, 4);
                        String monthNum = ((String) values[i]).substring(4, 6);
                        String dayNum = ((String) values[i]).substring(6, 8);
                        String hourNum = ((String) values[i]).substring(8, 10);
                        String minuteNum = ((String) values[i]).substring(10, 12);
                        String secondNum = ((String) values[i]).substring(12, 14);
                        LocalDateTime localDateTime = LocalDateTime.of(
                                Integer.parseInt(yearNum), Integer.parseInt(monthNum), Integer.parseInt(dayNum),
                                Integer.parseInt(hourNum), Integer.parseInt(minuteNum), Integer.parseInt(secondNum));
                        ps.setObject(i + 1, Timestamp.valueOf(localDateTime));
                        break;
                    }
                    case "Boolean": {
                        ps.setObject(i + 1, Boolean.parseBoolean((String) values[i]));
                        break;
                    }
                }
            }
            int effect_rows = ps.executeUpdate();
            List<String> effect_rows_arr = new ArrayList<>();
            effect_rows_arr.add(String.valueOf(effect_rows));
            dml_result.add(effect_rows_arr);
        } finally {
            DruidUtilsMySQL.closeResourcePS(connection1, null, ps);
        }

        Connection connection2 = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection2 = DruidUtilsMySQL.getDruidMySQLConnection();
            statement = connection2.createStatement();
            resultSet = statement.executeQuery(query_sql);
            List<List<String>> resultList = getResultListWithLabel(resultSet, dml_result);
            return resultList;
        } finally {
            DruidUtilsMySQL.closeResource(connection2, resultSet, statement);
        }
    }

    //通过preparedStatement对表进行批量插入操作
    public void preparedStatementBatchInsert( 
            String insert_sql, int insert_count, String[] value_type, int strLength, int integerMax, int floatMin,
            int floatMax, int floatScale, int doubleMin, int doubleMax, int doubleScale, String datePattern, 
            String startDateStr, String endDateStr, String timePattern, String startTimeStr, String endTimeStr,
            String timestampPattern, String startTimestampStr, String endTimestampStr
            ) throws SQLException, ParseException {
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = DruidUtilsMySQL.getDruidMySQLConnection();
            ps = connection.prepareStatement(insert_sql);
            GetRandomValue getRandomValue = new GetRandomValue();
            for (int i = 1; i <= insert_count; i++) {
                ps.setObject(1, i);
                for (int j = 1; j< value_type.length; j++) {
                    switch (value_type[j].trim()) {
                        case "Varchar": {
                            String randStr = GetRandomValue.getRandStr(strLength);
                            ps.setObject(j + 1, randStr);
                            break;
                        }
                        case "Char": {
                            String randStr = GetRandomValue.getRandNumStr(strLength);
                            ps.setObject(j + 1, randStr);
                            break;
                        }
                        case "Integer": {
                            int randInt = GetRandomValue.getRandInt(integerMax);
                            ps.setObject(j + 1, randInt);
                            break;
                        }
                        case "Bigint": {
                            long randLong = GetRandomValue.getRandLong();
                            ps.setObject(j + 1, randLong);
                            break;
                        }
                        case "Float": {
                            float randFloat = GetRandomValue.getRandFloat(floatMin, floatMax, floatScale);
                            ps.setObject(j + 1, randFloat);
                            break;
                        }
                        case "Double": {
                            double randDouble = GetRandomValue.getRandDouble(doubleMin, doubleMax, doubleScale);
                            ps.setObject(j + 1, randDouble);
                            break;
                        }
                        case "Date": {
                            Date randDate = GetRandomValue.getRandomDate(datePattern, startDateStr, endDateStr);
                            ps.setObject(j + 1, randDate);
                            break;
                        }
                        case "Time": {
                            Time randTime = GetRandomValue.getRandomTime(timePattern, startTimeStr, endTimeStr);
                            ps.setObject(j + 1, randTime);
                            break;
                        }
                        case "Timestamp": {
                            Timestamp randTimestamp = GetRandomValue.getRandomTimestamp(timestampPattern, startTimestampStr, endTimestampStr);
                            ps.setObject(j + 1, randTimestamp);
                            break;
                        }
                        case "Boolean": {
                            ps.setObject(j + 1, GetRandomValue.getRandBoolean());
                            break;
                        }
                    }
                }
                
                //批量添加sql
                ps.addBatch();
                
                if (i % 500 == 0) {
                    //执行batch
                    ps.executeBatch();
                    
                    //清空batch
                    ps.clearBatch();
                    System.out.println("已写入数据：" + i);
                }
            }
        } finally {
            DruidUtilsMySQL.closeResourcePS(connection, null, ps);
        }
    }
    

    public void doDropTable(String tableName) throws SQLException {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = DruidUtilsMySQL.getDruidMySQLConnection();
            statement = connection.createStatement();
            String sql = "drop table if exists " + tableName;
            statement.execute(sql);
        } finally {
            DruidUtilsMySQL.closeResource(connection, null, statement);
        }
    }

    public void doDropSchema(String schemaName) throws SQLException {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = DruidUtilsMySQL.getDruidMySQLConnection();
            statement = connection.createStatement();
            String sql = "drop schema if exists " + schemaName;
            statement.execute(sql);
        } finally {
            DruidUtilsMySQL.closeResource(connection, null, statement);
        }
    }

    public void doDropDatabase(String databaseName) throws SQLException {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = DruidUtilsMySQL.getDruidMySQLConnection();
            statement = connection.createStatement();
            String sql = "drop database if exists " + databaseName;
            statement.execute(sql);
        } finally {
            DruidUtilsMySQL.closeResource(connection, null, statement);
        }
    }

    //通过指定列索引查询表数据, 返回List<List<String>>,包含输出表头
    public List<List<String>> statementQueryWithSpecifiedColIndex(String sql, List<Integer> colIndexList) throws SQLException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DruidUtilsMySQL.getDruidMySQLConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            List<List<String>> resultList = getResultListWithColumnIndex(resultSet, colIndexList);
            return resultList;
        } finally {
            DruidUtilsMySQL.closeResource(connection, resultSet, statement);
        }
    }

    //插入Blob类型字段的数据
    public int preparedStatementInsertBlobData(String insert_sql, String[] insert_value_type, List insert_values) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = DruidUtilsMySQL.getDruidMySQLConnection();
            ps = connection.prepareStatement(insert_sql);
            for (int i = 0; i < insert_values.size(); i++) {
                switch (insert_value_type[i].trim()) {
                    case "Varchar": {
                        ps.setString(i + 1, (String) insert_values.get(i));
                        break;
                    }
                    case "Integer": {
                        ps.setInt(i + 1, Integer.parseInt((String) insert_values.get(i)));
                        break;
                    }
                    case "Bigint": {
                        ps.setLong(i + 1, Long.parseLong((String) insert_values.get(i)));
                        break;
                    }
                    case "Blob": {
                        ps.setBlob(i + 1, (FileInputStream) insert_values.get(i));
                        break;
                    }
                }
            }
            int effect_rows = ps.executeUpdate();
            return effect_rows;
        } finally {
            DruidUtilsMySQL.closeResourcePS(connection, null, ps);
        }
    }

    //读取Blob类型字段的数据
    public FileOutputStream preparedStatementGetBlobData(String query_sql, String[] query_value_type, int blobIndex, String fileOutPath, Object ... query_values) throws SQLException, IOException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        InputStream inputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            connection = DruidUtilsMySQL.getDruidMySQLConnection();
            ps = connection.prepareStatement(query_sql);
            for (int i = 0; i < query_values.length; i++) {
                switch (query_value_type[i].trim()) {
                    case "Varchar": {
                        ps.setString(i + 1, (String) query_values[i]);
                        break;
                    }
                    case "Integer": {
                        ps.setInt(i + 1, Integer.parseInt((String) query_values[i]));
                        break;
                    }
                    case "Bigint": {
                        ps.setLong(i + 1, Long.parseLong((String) query_values[i]));
                        break;
                    }
                    case "Blob": {
                        ps.setBlob(i + 1, (FileInputStream) query_values[i]);
                        break;
                    }
                }
            }
            resultSet = ps.executeQuery();
            while (resultSet.next()) {
                Blob blob = resultSet.getBlob(blobIndex);
                inputStream = blob.getBinaryStream();
                fileOutputStream = new FileOutputStream(fileOutPath);
                byte[] buffer = new byte[1024];
                int len;
                while ((len = inputStream.read(buffer)) != -1) {
                    fileOutputStream.write(buffer,0, len);
                }
            }
            inputStream.close();
            fileOutputStream.close();
            return fileOutputStream;
        } finally {
            DruidUtilsMySQL.closeResourcePS(connection, resultSet, ps);
        }
    }

    //获取查询结果集，包含表头
    public List<List<String>> getResultListWithLabel(ResultSet resultSet, List<List<String>> resultList) throws SQLException {
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        List<String> lableList = new ArrayList<>();
//        List<String> typeList = new ArrayList<>();
        int columnCount = resultSetMetaData.getColumnCount();
        for (int l = 1; l <= columnCount; l++) {
            lableList.add(resultSetMetaData.getColumnLabel(l));
//            typeList.add(resultSetMetaData.getColumnTypeName(l));
        }
        resultList.add(lableList);

        while (resultSet.next()) {
            List rowList = new ArrayList();
            for (int i = 1; i <= columnCount; i++) {
                String columnLabel = resultSetMetaData.getColumnLabel(i);
                String columnTypeName = resultSetMetaData.getColumnTypeName(i);
                if (resultSet.getObject(columnLabel) == null) {
                    rowList.add(String.valueOf(resultSet.getObject(columnLabel)));
                } else if (resultSet.getObject(columnLabel).getClass().toString().equalsIgnoreCase("class [B")) {
                    rowList.add(Arrays.toString((byte[]) resultSet.getObject(columnLabel)));
                } else if (columnTypeName.equalsIgnoreCase("ARRAY")) {
                    rowList.add(resultSet.getArray(columnLabel).toString());
                } else if (columnTypeName.equalsIgnoreCase("DATE")) {
                    rowList.add(resultSet.getDate(columnLabel).toString().substring(0,10));
                } else if (columnTypeName.equalsIgnoreCase("TIME")) {
                    rowList.add(resultSet.getTime(columnLabel).toString().substring(0,8));
                } else if (columnTypeName.equalsIgnoreCase("TIMESTAMP")) {
                    rowList.add(resultSet.getTimestamp(columnLabel).toString().substring(0,19));
                } else {
                    rowList.add(resultSet.getObject(columnLabel).toString());
                }
            }
            resultList.add(rowList);
        }
        return resultList;
    }
    
    
    //获取查询结果集，包含表头
    public List<List<String>> getResultListWithLabel(ResultSet resultSet) throws SQLException {
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        List<List<String>> resultList = new ArrayList<>();
        List<String> lableList = new ArrayList<>();
//        List<String> typeList = new ArrayList<>();
        int columnCount = resultSetMetaData.getColumnCount();
        for (int l = 1; l <= columnCount; l++) {
            lableList.add(resultSetMetaData.getColumnLabel(l));
//            typeList.add(resultSetMetaData.getColumnTypeName(l));
        }
        resultList.add(lableList);
        
        while (resultSet.next()) {
            List rowList = new ArrayList();
            for (int i = 1; i <= columnCount; i++) {
                String columnLabel = resultSetMetaData.getColumnLabel(i);
                String columnTypeName = resultSetMetaData.getColumnTypeName(i);
                if (resultSet.getObject(columnLabel) == null) {
                    rowList.add(String.valueOf(resultSet.getObject(columnLabel)));
                } else if (resultSet.getObject(columnLabel).getClass().toString().equalsIgnoreCase("class [B")) {
                    rowList.add(Arrays.toString((byte[]) resultSet.getObject(columnLabel)));
                } else if (columnTypeName.equalsIgnoreCase("ARRAY")) {
                    rowList.add(resultSet.getArray(columnLabel).toString());
                } else if (columnTypeName.equalsIgnoreCase("DATE")) {
                    rowList.add(resultSet.getDate(columnLabel).toString().substring(0,10));
                } else if (columnTypeName.equalsIgnoreCase("TIME")) {
                    rowList.add(resultSet.getTime(columnLabel).toString().substring(0,8));
                } else if (columnTypeName.equalsIgnoreCase("TIMESTAMP")) {
                    rowList.add(resultSet.getTimestamp(columnLabel).toString().substring(0,19));
                } else {
                    rowList.add(resultSet.getObject(columnLabel).toString());
                }
            }
            resultList.add(rowList);
        }
        return resultList;
    }

    //获取查询结果集，不包含表头
    public List<List<String>> getResultListWithoutLabel(ResultSet resultSet) throws SQLException {
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        List<List<String>> resultList = new ArrayList<>();
        List<String> lableList = new ArrayList<>();
//        List<String> typeList = new ArrayList<>();
        int columnCount = resultSetMetaData.getColumnCount();
        for (int l = 1; l <= columnCount; l++) {
            lableList.add(resultSetMetaData.getColumnLabel(l));
//            typeList.add(resultSetMetaData.getColumnTypeName(l));
        }
        
        while (resultSet.next()) {
            List rowList = new ArrayList();
            for (int i = 1; i <= columnCount; i++) {
                String columnLabel = resultSetMetaData.getColumnLabel(i);
                String columnTypeName = resultSetMetaData.getColumnTypeName(i);
                if (resultSet.getObject(columnLabel) == null) {
                    rowList.add(String.valueOf(resultSet.getObject(columnLabel)));
                } else if (resultSet.getObject(columnLabel).getClass().toString().equalsIgnoreCase("class [B")) {
                    rowList.add(Arrays.toString((byte[]) resultSet.getObject(columnLabel)));
                } else if (columnTypeName.equalsIgnoreCase("ARRAY")) {
                    rowList.add(resultSet.getArray(columnLabel).toString());
                } else if (columnTypeName.equalsIgnoreCase("DATE")) {
                    rowList.add(resultSet.getDate(columnLabel).toString().substring(0,10));
                } else if (columnTypeName.equalsIgnoreCase("TIME")) {
                    rowList.add(resultSet.getTime(columnLabel).toString().substring(0,8));
                } else if (columnTypeName.equalsIgnoreCase("TIMESTAMP")) {
                    rowList.add(resultSet.getTimestamp(columnLabel).toString().substring(0,19));
                } else {
                    rowList.add(resultSet.getObject(columnLabel).toString());
                }
            }
            resultList.add(rowList);
        }
        return resultList;
    }

    //通过指定列索引获取查询结果集，包含表头
    public List<List<String>> getResultListWithColumnIndex(ResultSet resultSet, List<Integer> colIndexList) throws SQLException {
        List<List<String>> resultList = new ArrayList<>();
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        List<String> lableList = new ArrayList<>();
//        int columnCount = resultSetMetaData.getColumnCount();
        for (int l = 0; l < colIndexList.size(); l++) {
            lableList.add(resultSetMetaData.getColumnLabel(colIndexList.get(l)));
        }
        resultList.add(lableList);

        while (resultSet.next()) {
            List rowList = new ArrayList();
            for (int i = 0; i < colIndexList.size(); i++) {
                String columnTypeName = resultSetMetaData.getColumnTypeName(colIndexList.get(i));
                if (resultSet.getObject(colIndexList.get(i)) == null) {
                    rowList.add(String.valueOf(resultSet.getObject(colIndexList.get(i))));
                } else if (resultSet.getObject(colIndexList.get(i)).getClass().toString().equalsIgnoreCase("class [B")) {
                    rowList.add(Arrays.toString((byte[]) resultSet.getObject(colIndexList.get(i))));
                } else if (columnTypeName.equalsIgnoreCase("ARRAY")) {
                    rowList.add(resultSet.getArray(colIndexList.get(i)).toString());
                } else if (columnTypeName.equalsIgnoreCase("DATE")) {
                    rowList.add(resultSet.getDate(colIndexList.get(i)).toString().substring(0,10));
                } else if (columnTypeName.equalsIgnoreCase("TIME")) {
                    rowList.add(resultSet.getTime(colIndexList.get(i)).toString().substring(0,8));
                } else if (columnTypeName.equalsIgnoreCase("TIMESTAMP")) {
                    rowList.add(resultSet.getTimestamp(colIndexList.get(i)).toString().substring(0,19));
                } else {
                    rowList.add(resultSet.getObject(colIndexList.get(i)).toString());
                }
            }
            resultList.add(rowList);
        }
        return resultList;
    }
    
}