package io.dingodb.test;

import datahelper.YamlDataHelper;
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
    public void tearDownAll() throws SQLException {
        System.out.println(createTableSet);
        if(createTableSet.size() > 0) {
            for (String s : createTableSet) {
                sqlHelper.doDropTable(s);
            }
        }
        createTableSet.clear();
    }

    @BeforeMethod (alwaysRun = true)
    public void setup() throws Exception {
    }

    @AfterMethod (alwaysRun = true)
    public void cleanUp() throws Exception {
        if(createTableSet.size() > 0) {
            for (String s : createTableSet) {
                sqlHelper.doDropTable(s);
            }
        }
        createTableSet.clear();
    }

    @Test(priority = 0, enabled = true, dataProvider = "dqlData1", dataProviderClass = YamlDataHelper.class, description = "查询测试，正向用例")
    public void testDQL1(LinkedHashMap<String,String> param) throws SQLException, IOException {
        if (param.get("Testable").trim().equals("n") || param.get("Testable").trim().equals("N")) {
            throw new SkipException("skip this test case");
        }
        String sql = param.get("Sql_state").trim();
        if (param.get("Table_schema_ref").trim().length() > 0) {
            List<String> tableList = new ArrayList<>();
            List<String> schemaList = CastUtils.construct1DListIncludeBlank(param.get("Table_schema_ref"),",");
            for (int i = 0; i < schemaList.size(); i++) {
                String tableName = "";
                if (!schemaList.get(i).trim().contains("_")) {
                    tableName = param.get("TestID").trim() + "_0" + i + schemaList.get(i).trim();
                    sqlHelper.execFile(TestDQL.class.getClassLoader().getResourceAsStream(iniReader.getValue("TableSchema",schemaList.get(i).trim())), tableName);
                } else {
                    String schemaName = schemaList.get(i).trim().substring(0,schemaList.get(i).trim().indexOf("_"));
                    tableName = param.get("TestID").trim() + "_0" + i + schemaName;
                    sqlHelper.execFile(TestDQL.class.getClassLoader().getResourceAsStream(iniReader.getValue("TableSchema",schemaName)), tableName);
                }
                tableList.add(tableName);
                sql = sql.replace("$" + schemaList.get(i).trim(), tableName);
            }
            createTableSet.addAll(tableList);
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
        
        if (param.get("Validation_type").equals("csv_equals")) {
            String resultFile = param.get("Expected_result").trim();
            List<List<String>> expectedResult = ParseCsv.splitCsvString(resultFile);
            System.out.println("Expected: " + expectedResult);
            List<List<String>> actualResult = sqlHelper.statementQueryWithHead(sql);
            System.out.println("Actual: " + actualResult);
            Assert.assertEquals(actualResult, expectedResult);
        } else if (param.get("Validation_type").equals("csv_containsAll")) {
            String resultFile = param.get("Expected_result").trim();
            List<List<String>> expectedResult = ParseCsv.splitCsvString(resultFile);
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
                        Assert.assertEquals(actualResult.substring(0,5), expectedResult.substring(0,5));
                        break;
                    }
                    case "getCurTimestamp": {
                        String expectedResult = o.toString();
                        System.out.println("Expected: " + expectedResult);
                        Assert.assertEquals(actualResult.length(),19);
                        Assert.assertEquals(actualResult.substring(0,16), expectedResult.substring(0,16));
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
}
