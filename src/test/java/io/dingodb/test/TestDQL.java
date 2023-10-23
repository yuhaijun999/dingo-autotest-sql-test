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
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.CastUtils;
import utils.ParseCsv;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;

public class TestDQL extends BaseTestSuite {
    private static SQLHelper sqlHelper;
    private static HashSet<String> createTableSet = new HashSet<>();
    
    @BeforeClass (alwaysRun = true)
    public void setupAll() {
        sqlHelper = new SQLHelper();
    }

    @AfterClass (alwaysRun = true)
    public void tearDownAll() throws SQLException, IOException, ClassNotFoundException {
        System.out.println("Create table set: " + createTableSet);
        if(createTableSet.size() > 0) {
            List<String> finalTableList = JDBCUtils.getTableList();
            System.out.println("Get table list: " + finalTableList);
            for (String s : createTableSet) {
                if (finalTableList.contains(s.toUpperCase())) {
                    sqlHelper.doDropTable(s);
                }
            }
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

    @Test(priority = 0, enabled = true, dataProvider = "dqlData1", dataProviderClass = YamlDataHelper.class, description = "查询测试，正向用例")
    public void testDQL1(LinkedHashMap<String,String> param) throws SQLException, IOException, InterruptedException {
        if (param.get("Testable").trim().equals("n") || param.get("Testable").trim().equals("N")) {
            throw new SkipException("skip this test case");
        }
        
        List<String> tableList = new ArrayList<>();
        String sql = param.get("Sql_state").trim();
        if (param.get("Table_schema_ref").trim().length() > 0) {
//            List<String> tableList = new ArrayList<>();
            List<String> schemaList = CastUtils.construct1DListIncludeBlank(param.get("Table_schema_ref"),",");
            for (int i = 0; i < schemaList.size(); i++) {
                String tableName = "";
                if (!schemaList.get(i).trim().contains("_")) {
                    if (param.get("Case_table_dependency").trim().length() > 0) {
                        tableName = param.get("Case_table_dependency").trim() + "_0" + i + schemaList.get(i).trim();
                        sql = sql.replace("$" + schemaList.get(i).trim(), tableName);
                    } else {
                        tableName = param.get("TestID").trim() + "_0" + i + schemaList.get(i).trim();
                        sqlHelper.execFile(TestDQL.class.getClassLoader().getResourceAsStream(iniReader.getValue("TableSchema",schemaList.get(i).trim())), tableName);
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
                        sqlHelper.execFile(TestDQL.class.getClassLoader().getResourceAsStream(iniReader.getValue("TableSchema",schemaName)), tableName);
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
                        sqlHelper.execFile(TestDQL.class.getClassLoader().getResourceAsStream(iniReader.getValue("DQLGroup1Values", value_List.get(j).trim())), tableName);
                    }
                }
            }
            if (param.get("Case_table_dependency").trim().length() > 0) {
                if (param.get("Table_value_ref").trim().length() > 0) {
                    List<String> value_List = CastUtils.construct1DListIncludeBlank(param.get("Table_value_ref").trim(),",");
                    for (int j = 0; j < value_List.size(); j++) {
//                        String tableName = "";
//                        if (!schemaList.get(j).trim().contains("_")) {
//                            tableName = param.get("TestID").trim() + "_0" + j + schemaList.get(j).trim();
//                        } else {
//                            String schemaName = schemaList.get(j).trim().substring(0,schemaList.get(j).trim().indexOf("_"));
//                            tableName = param.get("TestID").trim() + "_0" + j + schemaName;
//                        }
                        String tableName = param.get("Case_table_dependency").trim() + "_0" + j + schemaList.get(j).trim();
                        sqlHelper.execFile(TestDQL.class.getClassLoader().getResourceAsStream(iniReader.getValue("DQLGroup1Values", value_List.get(j).trim())), tableName);
                    }
                }
            }
        }
        
        if (param.get("Validation_type").equals("csv_equals")) {
            String resultFile = param.get("Expected_result").trim();
            List<List<String>> expectedResult = new ArrayList<>();
            if (!param.get("Component").equalsIgnoreCase("ComplexDataType")){
                expectedResult = ParseCsv.splitCsvString(resultFile,",");
            } else {
                expectedResult = ParseCsv.splitCsvString(resultFile,"&");
            }
            System.out.println("Expected: " + expectedResult);
            List<List<String>> actualResult = sqlHelper.statementQueryWithHead(sql);
            System.out.println("Actual: " + actualResult);
            Assert.assertEquals(actualResult, expectedResult);
        } else if (param.get("Validation_type").equals("csv_containsAll")) {
            String resultFile = param.get("Expected_result").trim();
            List<List<String>> expectedResult = new ArrayList<>();
            if (!param.get("Component").equalsIgnoreCase("ComplexDataType")){
                expectedResult = ParseCsv.splitCsvString(resultFile,",");
            } else {
                expectedResult = ParseCsv.splitCsvString(resultFile,"&");
            }
            System.out.println("Expected: " + expectedResult);
            List<List<String>> actualResult = sqlHelper.statementQueryWithHead(sql);
            System.out.println("Actual: " + actualResult);
            Assert.assertTrue(actualResult.containsAll(expectedResult));
            Assert.assertTrue(expectedResult.containsAll(actualResult));
        } else if (param.get("Validation_type").equals("string_equals")) {
            String expectedResult = param.get("Expected_result");
            System.out.println("Expected: " + expectedResult);
            String actualResult = sqlHelper.queryWithStrReturn(sql);
            System.out.println("Actual: " + actualResult);
            Assert.assertEquals(actualResult, expectedResult);
        } else if (param.get("Validation_type").equals("double_equals")) {
            Double expectedResult = Double.parseDouble(param.get("Expected_result"));
            System.out.println("Expected: " + expectedResult);
            Double actualResult = sqlHelper.queryWithDoubleReturn(sql);
            System.out.println("Actual: " + actualResult);
            Assert.assertEquals(actualResult, expectedResult);
        } else if (param.get("Validation_type").equals("assertNull")) {
            Assert.assertNull(sqlHelper.queryWithObjReturn(sql));
        } else if (param.get("Validation_type").equals("justExec")) {
            if (param.get("Component").equalsIgnoreCase("Explain")) {
                Thread.sleep(330000);
                sqlHelper.execSql(sql);
            } else {
                sqlHelper.execSql(sql);
            }
        }
    }

    @Test(priority = 1, enabled = true, dataProvider = "dqlData2", dataProviderClass = YamlDataHelper.class, description = "当前时间日期函数的查询测试")
    public void testDQL2(LinkedHashMap<String,String> param) throws SQLException, IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        if (param.get("Testable").trim().equals("n") || param.get("Testable").trim().equals("N")) {
            throw new SkipException("skip this test case");
        }
        String sql = param.get("Sql_state").trim();
        String actualResult = sqlHelper.queryWithStrReturn(sql);
        System.out.println("Actual: " + actualResult);
        if (param.get("Validation_type").equals("string_equals")) {
            String expectedResult = param.get("Expected_result");
            System.out.println("Expected: " + expectedResult);
            Assert.assertEquals(actualResult, expectedResult);
        } else if (param.get("Validation_type").equals("reg_match")) {
            String matchReg = param.get("Reg_match");
            System.out.println("matchReg: " + matchReg);
            Assert.assertTrue(actualResult.matches(matchReg));
        } else if (param.get("Validation_type").equals("reflection")) {
            Class clazz = Class.forName(param.get("Class_name").trim());
            if (param.get("Method_param_type").trim().equals("String")) {
                Method method = clazz.getDeclaredMethod(param.get("Reflection_method").trim(), String.class);
                Object o = method.invoke(clazz, param.get("Method_param_value").trim());
                switch(param.get("Reflection_method")) {
                    case "getCurDate": {
                        String expectedResult = o.toString();
                        System.out.println("Expected: " + expectedResult);
                        Assert.assertEquals(actualResult, expectedResult);
                        break;
                    }
                    case "getCurTime": {
                        String expectedResult = o.toString();
                        System.out.println("Expected: " + expectedResult);
                        Assert.assertEquals(actualResult.length(),8);
                        Assert.assertEquals(actualResult.substring(0,2), expectedResult.substring(0,2));
                        break;
                    }
                    case "getCurTimestamp": {
                        String expectedResult = o.toString();
                        System.out.println("Expected: " + expectedResult);
//                        Assert.assertEquals(actualResult.length(),23);
                        Assert.assertEquals(actualResult.substring(0,13), expectedResult.substring(0,13));
                        break;
                    }
                    case "getDiffDateStartCur": {
                        String expectedResult = o.toString();
                        System.out.println("Expected: " + expectedResult);
                        Assert.assertEquals(actualResult,expectedResult);
                        break;
                    }
                    case "getDiffDateEndCur": {
                        String expectedResult = o.toString();
                        System.out.println("Expected: " + expectedResult);
                        Assert.assertEquals(actualResult,expectedResult);
                        break;
                    }
                }
            } else {
                Method method = clazz.getDeclaredMethod(param.get("Reflection_method").trim());
                Object o = method.invoke(clazz);
                switch(param.get("Reflection_method")) {
                    case "getCurUTS": {
                        System.out.println("Expected: " + o);
                        Assert.assertEquals(actualResult.length(), 10);
                        long timeStampDiff = Long.parseLong(actualResult) - (Long)o;
                        System.out.println("timestampDiff: " + timeStampDiff);
                        Assert.assertTrue(Math.abs(timeStampDiff) < 60);
                        break;
                    }
                }
            }
        }
    }

    @Test(priority = 2, enabled = true, dataProvider = "dqlData3", dataProviderClass = YamlDataHelper.class, description = "explain查看任务执行计划")
    public void testDQL3(LinkedHashMap<String,String> param) throws SQLException, IOException, InterruptedException {
        if (param.get("Testable").trim().equals("n") || param.get("Testable").trim().equals("N")) {
            throw new SkipException("skip this test case");
        }

        List<String> tableList = new ArrayList<>();
        String sql = param.get("Sql_state").trim();
        if (param.get("Table_schema_ref").trim().length() > 0) {
            List<String> schemaList = CastUtils.construct1DListIncludeBlank(param.get("Table_schema_ref"),",");
            for (int i = 0; i < schemaList.size(); i++) {
                String tableName = "";
                if (!schemaList.get(i).trim().contains("_")) {
                    if (param.get("Case_table_dependency").trim().length() > 0) {
                        tableName = param.get("Case_table_dependency").trim() + "_0" + i + schemaList.get(i).trim();
                        sql = sql.replace("$" + schemaList.get(i).trim(), tableName);
                    } else {
                        tableName = param.get("TestID").trim() + "_0" + i + schemaList.get(i).trim();
                        sqlHelper.execFile(TestDQL.class.getClassLoader().getResourceAsStream(iniReader.getValue("TableSchema",schemaList.get(i).trim())), tableName);
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
                        sqlHelper.execFile(TestDQL.class.getClassLoader().getResourceAsStream(iniReader.getValue("TableSchema",schemaName)), tableName);
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
                        sqlHelper.execFile(TestDQL.class.getClassLoader().getResourceAsStream(iniReader.getValue("DQLGroup3Values", value_List.get(j).trim())), tableName);
                    }
                }
            }
            if (param.get("Case_table_dependency").trim().length() > 0) {
                if (param.get("Table_value_ref").trim().length() > 0) {
                    List<String> value_List = CastUtils.construct1DListIncludeBlank(param.get("Table_value_ref").trim(),",");
                    for (int j = 0; j < value_List.size(); j++) {
                        String tableName = param.get("Case_table_dependency").trim() + "_0" + j + schemaList.get(j).trim();
                        sqlHelper.execFile(TestDQL.class.getClassLoader().getResourceAsStream(iniReader.getValue("DQLGroup3Values", value_List.get(j).trim())), tableName);
                    }
                }
            }
        }

        if (param.get("Validation_type").equals("justExec")) {
            if (param.get("Component").equalsIgnoreCase("Explain-key")) {
                Thread.sleep(330000);
                sqlHelper.execSql(sql);
            } else {
                sqlHelper.execSql(sql);
            }
        } else if (param.get("Validation_type").equals("csv_containsAll")) {
            if (param.get("Component").equalsIgnoreCase("Explain-key")) {
                Thread.sleep(330000);
                String resultFile = param.get("Expected_result").trim();
                List<List<String>> expectedResult = new ArrayList<>();
                expectedResult = ParseCsv.splitCsvString(resultFile,",");
                System.out.println("Expected: " + expectedResult);
                List<List<String>> actualResult = sqlHelper.statementQueryWithHead(sql);
                System.out.println("Actual: " + actualResult);
                Assert.assertTrue(actualResult.containsAll(expectedResult));
                Assert.assertTrue(expectedResult.containsAll(actualResult));
            }
        }
    }
    
}
