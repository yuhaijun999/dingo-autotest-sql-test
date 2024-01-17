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

import io.dingodb.common.utils.MySQLUtils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MySQLHelper extends BaseSQLHelper {
    public static Connection connection;

    static {
        try {
            connection = MySQLUtils.getMySQLConnection();
//            connection = DruidUtils.getDruidMySQLConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public MySQLHelper() {}

    public MySQLHelper(Connection connection) {
        this.connection = connection;
    }

    //通过指定列索引查询表数据, 返回List<List<String>>,包含输出表头
    public List<List<String>> statementQueryWithSpecifiedColIndex(Connection connection, String sql, List<Integer> colIndexList) throws SQLException {
        try(Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            List<List<String>> resultList = getResultListWithColumnIndex(resultSet, colIndexList);
            resultSet.close();
            return resultList;
        }
    }

    //插入Blob类型字段的数据
    public int preparedStatementInsertBlobData(Connection connection, String insert_sql, String[] insert_value_type, List insert_values) throws SQLException {
        try(PreparedStatement ps = connection.prepareStatement(insert_sql)) {
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
        }
    }

    //读取Blob类型字段的数据
    public FileOutputStream preparedStatementGetBlobData(Connection connection, String query_sql, String[] query_value_type, int blobIndex, String fileOutPath, Object ... query_values) throws SQLException, IOException {
        InputStream inputStream = null;
        FileOutputStream fileOutputStream = null;
        ResultSet resultSet = null;
        try(PreparedStatement ps = connection.prepareStatement(query_sql)) {
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
            resultSet.close();
            return fileOutputStream;
        }
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
