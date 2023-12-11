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

import datahelper.YamlDataHelper;
import io.dingodb.common.utils.JDBCUtils;
import io.dingodb.dailytest.SQLHelper;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.CastUtils;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;

public class TestNegative extends BaseTestSuite {
    private static SQLHelper sqlHelper;
    private static HashSet<String> createTableSet = new HashSet<>();
    private static HashSet<String> createSchemaSet = new HashSet<>();
    private static HashSet<String> schemaTableSet = new HashSet<>();


    @BeforeClass (alwaysRun = true)
    public static void setupAll() {
        sqlHelper = new SQLHelper();
    }

    @AfterClass (alwaysRun = true)
    public static void tearDownAll() throws SQLException, IOException, ClassNotFoundException {
//        System.out.println(createTableSet);
        if(createTableSet.size() > 0) {
            List<String> finalTableList = JDBCUtils.getTableList();
            for (String s : createTableSet) {
                if (finalTableList.contains(s.toUpperCase())) {
                    sqlHelper.doDropTable(s);
                }
            }
        }
        System.out.println(schemaTableSet);
        for (String s : schemaTableSet) {
            sqlHelper.doDropTable(s);
        }
        System.out.println(createSchemaSet);
        for (String s : createSchemaSet) {
            sqlHelper.doDropSchema(s);
        }
    }

    @BeforeMethod (alwaysRun = true)
    public void setup() throws Exception {
    }

    @AfterMethod (alwaysRun = true)
    public void cleanUp() throws Exception {
//        if(createTableSet.size() > 0) {
//            for (String s : createTableSet) {
//                sqlHelper.doDropTable(s);
//            }
//        }
//        createTableSet.clear();
    }

    @Test(priority = 0, enabled = true, dataProvider = "negativeData", dataProviderClass = YamlDataHelper.class, 
            expectedExceptions = SQLException.class, description = "预期失败用例测试")
    public void testException(LinkedHashMap<String,String> param) throws SQLException, IOException {
        if (param.get("Testable").trim().equals("n") || param.get("Testable").trim().equals("N")) {
            throw new SkipException("skip this test case");
        }
        
        List<String> tableList = new ArrayList<>();
        String sql = param.get("Sql_state").trim();
        if (param.get("Table_schema_ref").trim().length() > 0) {
            List<String> schemaList = CastUtils.construct1DListIncludeBlank(param.get("Table_schema_ref").trim(),",");
            for (int i = 0; i < schemaList.size(); i++) {
                String tableName = "";
                if (!schemaList.get(i).trim().contains("_")) {
                    if (param.get("Case_table_dependency").trim().length() > 0) {
                        tableName = param.get("Case_table_dependency").trim() + "_0" + i + schemaList.get(i).trim();
                        sql = sql.replace("$" + schemaList.get(i).trim(), tableName);
                    } else {
                        tableName = param.get("TestID").trim() + "_0" + i + schemaList.get(i).trim();
                        sqlHelper.execFile(TestNegative.class.getClassLoader().getResourceAsStream(iniReader.getValue("TableSchema",schemaList.get(i).trim())), tableName);
                        tableList.add(tableName);
                        sql = sql.replace("$" + schemaList.get(i).trim(), tableName);
                    }
                } else {
                    String schemaName = schemaList.get(i).trim().substring(0,schemaList.get(i).trim().indexOf("_"));
                    if (param.get("Case_table_dependency").trim().length() > 0) {
                        tableName = param.get("Case_table_dependency").trim() + "_0" + i + schemaName;
                        sql = sql.replace("$" + schemaList.get(i).trim(), tableName);
                    } else {
                        tableName = param.get("TestID").trim() + "_0" + i + schemaName;
                        sqlHelper.execFile(TestNegative.class.getClassLoader().getResourceAsStream(iniReader.getValue("TableSchema",schemaName)), tableName);
                        tableList.add(tableName);
                        sql = sql.replace("$" + schemaList.get(i).trim(), tableName);
                    }
                }
            }
            createTableSet.addAll(tableList);
            if (param.get("Case_table_dependency").trim().length() == 0) {
                if (param.get("Table_value_ref").trim().length() > 0) {
                    List<String> value_List = CastUtils.construct1DListIncludeBlank(param.get("Table_value_ref").trim(),",");
                    for (int j = 0; j < value_List.size(); j++) {
                        String tableName = "";
                        if (!schemaList.get(j).trim().contains("_")) {
                            tableName = param.get("TestID").trim() + "_0" + j + schemaList.get(j).trim();
                        } else {
                            String schemaName = schemaList.get(j).trim().substring(0,schemaList.get(j).trim().indexOf("_"));
                            tableName = param.get("TestID").trim() + "_0" + j + schemaName;
                        }
                        sqlHelper.execFile(TestNegative.class.getClassLoader().getResourceAsStream(iniReader.getValue("NegativeValues", value_List.get(j).trim())), tableName);
                    }
                }
            }
        }
        System.out.println("sql: " + sql);
        if (param.get("Component").equalsIgnoreCase("Schema")) {
            if (param.get("Sub_component").equalsIgnoreCase("Database")) {
                int endNum = param.get("Sql_state").indexOf(";");
                String schemaName = param.get("Sql_state").substring(16, endNum).trim();
                if (param.get("Sql_state").contains("table")) {
                    int tableStartNum = param.get("Sql_state").indexOf("table") + 6;
                    int tableEndNum = param.get("Sql_state").indexOf("(");
                    String tableName = param.get("Sql_state").substring(tableStartNum, tableEndNum).trim();
                    schemaTableSet.add(tableName);
                }
                createSchemaSet.add(schemaName);
            }
            sqlHelper.execBatchSqlWithState(sql);
        } else {
            sqlHelper.execSql(sql);
        }
    }
}
