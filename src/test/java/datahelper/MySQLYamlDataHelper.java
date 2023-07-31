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

package datahelper;

import org.testng.annotations.DataProvider;
import org.yaml.snakeyaml.Yaml;
import utils.GetYaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MySQLYamlDataHelper {
    public static List<Map<String, String>> getYamlList(String yamlFile) {
        List<Map<String, String>> list = new ArrayList<>();
        Map<String, List> map = readYamlUtil(yamlFile);
        Map.Entry<String, List> entry = map.entrySet().iterator().next();
        list = entry.getValue();
        return list;
    }

    public static Map<String, List> readYamlUtil(String fileName) {
        Map<String, List> map = null;
        try {
            Yaml yaml = new Yaml();
            File file = new File(fileName);
            if (file.exists()) {
                FileInputStream fs = new FileInputStream(fileName);
                map = yaml.load(fs);
                fs.close();
                return map;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    @DataProvider (name = "mysqlDQLData1", parallel = false)
    public Object[][] mysqlDQLCaseGroup1() throws IOException, InterruptedException {
        String excelPath = "src/test/resources/io.dingodb.test/testdata/mysqlcases/dql/casegroup1/mysql_dql_cases1.xlsx";
        String yamlPath = GetYaml.convertExcelToYaml(excelPath,0,0);
        List<Map<String, String>> yamlList = getYamlList(yamlPath);
        Object[][] cases = new Object[yamlList.size()][];
        for (int i = 0; i< yamlList.size(); i++) {
            cases[i] = new Object[] {yamlList.get(i)};
        }
        return cases;
    }

    @DataProvider (name = "mysqlDQLData2", parallel = false)
    public Object[][] mysqlDQLCaseGroup2() throws IOException, InterruptedException {
        String excelPath = "src/test/resources/io.dingodb.test/testdata/mysqlcases/dql/casegroup2/mysql_dql_cases2.xlsx";
        String yamlPath = GetYaml.convertExcelToYaml(excelPath,0,0);
        List<Map<String, String>> yamlList = getYamlList(yamlPath);
        Object[][] cases = new Object[yamlList.size()][];
        for (int i = 0; i< yamlList.size(); i++) {
            cases[i] = new Object[] {yamlList.get(i)};
        }
        return cases;
    }

    @DataProvider (name = "mysqlDDLData1", parallel = true)
    public Object[][] mysqlDDLCases1() throws IOException, InterruptedException {
        String excelPath = "src/test/resources/io.dingodb.test/testdata/mysqlcases/ddl/mysql_ddl_cases.xlsx";
        String yamlPath = GetYaml.convertExcelToYaml(excelPath,0,0);
        List<Map<String, String>> yamlList = getYamlList(yamlPath);
        Object[][] cases = new Object[yamlList.size()][];
        for (int i = 0; i< yamlList.size(); i++) {
            cases[i] = new Object[] {yamlList.get(i)};
        }
        return cases;
    }

    @DataProvider (name = "mysqlDMLInsertData", parallel = false)
    public Object[][] mysqlDMLInsertCases() throws IOException, InterruptedException {
        String excelPath = "src/test/resources/io.dingodb.test/testdata/mysqlcases/dml/insert/mysql_insert_cases.xlsx";
        String yamlPath = GetYaml.convertExcelToYaml(excelPath,0,0);
        List<Map<String, String>> yamlList = getYamlList(yamlPath);
        Object[][] cases = new Object[yamlList.size()][];
        for (int i = 0; i< yamlList.size(); i++) {
            cases[i] = new Object[] {yamlList.get(i)};
        }
        return cases;
    }

    @DataProvider (name = "mysqlDMLUpDelData", parallel = false)
    public Object[][] mysqlDMLUpDelCases() throws IOException, InterruptedException {
        String excelPath = "src/test/resources/io.dingodb.test/testdata/mysqlcases/dml/updatedelete/mysql_updatedelete_cases.xlsx";
        String yamlPath = GetYaml.convertExcelToYaml(excelPath,0,0);
        List<Map<String, String>> yamlList = getYamlList(yamlPath);
        Object[][] cases = new Object[yamlList.size()][];
        for (int i = 0; i< yamlList.size(); i++) {
            cases[i] = new Object[] {yamlList.get(i)};
        }
        return cases;
    }
    
    @DataProvider (name = "mysqlPSDQLData", parallel = false)
    public Object[][] mysqlPSDQLCases() throws IOException, InterruptedException {
        String excelPath = "src/test/resources/io.dingodb.test/testdata/mysqlcases/prepareStatement/mysql_ps_dql_cases.xlsx";
        String yamlPath = GetYaml.convertExcelToYaml(excelPath,0,0);
        List<Map<String, String>> yamlList = getYamlList(yamlPath);
        Object[][] cases = new Object[yamlList.size()][];
        for (int i = 0; i< yamlList.size(); i++) {
            cases[i] = new Object[] {yamlList.get(i)};
        }
        return cases;
    }

    @DataProvider (name = "mysqlPSDMLData", parallel = false)
    public Object[][] mysqlPSDMLCases() throws IOException, InterruptedException {
        String excelPath = "src/test/resources/io.dingodb.test/testdata/mysqlcases/prepareStatement/mysql_ps_dml_cases.xlsx";
        String yamlPath = GetYaml.convertExcelToYaml(excelPath,0,0);
        List<Map<String, String>> yamlList = getYamlList(yamlPath);
        Object[][] cases = new Object[yamlList.size()][];
        for (int i = 0; i< yamlList.size(); i++) {
            cases[i] = new Object[] {yamlList.get(i)};
        }
        return cases;
    }

    @DataProvider (name = "mysqlPSBatchData", parallel = false)
    public Object[][] mysqlPSBatchCases() throws IOException, InterruptedException {
        String excelPath = "src/test/resources/io.dingodb.test/testdata/mysqlcases/prepareStatement/mysql_ps_batch_cases.xlsx";
        String yamlPath = GetYaml.convertExcelToYaml(excelPath,0,0);
        List<Map<String, String>> yamlList = getYamlList(yamlPath);
        Object[][] cases = new Object[yamlList.size()][];
        for (int i = 0; i< yamlList.size(); i++) {
            cases[i] = new Object[] {yamlList.get(i)};
        }
        return cases;
    }

    @DataProvider (name = "mysqlSQLBatchData", parallel = true)
    public Object[][] mysqlSQLBatchCases() throws IOException, InterruptedException {
        String excelPath = "src/test/resources/io.dingodb.test/testdata/mysqlcases/batchsql/mysql_batchsql_cases.xlsx";
        String yamlPath = GetYaml.convertExcelToYaml(excelPath,0,0);
        List<Map<String, String>> yamlList = getYamlList(yamlPath);
        Object[][] cases = new Object[yamlList.size()][];
        for (int i = 0; i< yamlList.size(); i++) {
            cases[i] = new Object[] {yamlList.get(i)};
        }
        return cases;
    }
    
    @DataProvider (name = "mysqlNegativeData", parallel = false)
    public Object[][] mysqlNegativeCases() throws IOException, InterruptedException {
        String excelPath = "src/test/resources/io.dingodb.test/testdata/mysqlcases/negative/mysql_negative_cases.xlsx";
        String yamlPath = GetYaml.convertExcelToYaml(excelPath,0,0);
        List<Map<String, String>> yamlList = getYamlList(yamlPath);
        Object[][] cases = new Object[yamlList.size()][];
        for (int i = 0; i< yamlList.size(); i++) {
            cases[i] = new Object[] {yamlList.get(i)};
        }
        return cases;
    }

    @DataProvider (name = "mysqlDCLData", parallel = true)
    public Object[][] mysqlDCLCases() throws IOException, InterruptedException {
        String excelPath = "src/test/resources/io.dingodb.test/testdata/mysqlcases/dcl/mysql_dcl_cases.xlsx";
        String yamlPath = GetYaml.convertExcelToYaml(excelPath,0,0);
        List<Map<String, String>> yamlList = getYamlList(yamlPath);
        Object[][] cases = new Object[yamlList.size()][];
        for (int i = 0; i< yamlList.size(); i++) {
            cases[i] = new Object[] {yamlList.get(i)};
        }
        return cases;
    }

    @DataProvider (name = "mysqlProtocolData", parallel = true)
    public Object[][] mysqlProtocolCases() throws IOException, InterruptedException {
        String excelPath = "src/test/resources/io.dingodb.test/testdata/mysqlcases/protocol/mysql_protocol_cases.xlsx";
        String yamlPath = GetYaml.convertExcelToYaml(excelPath,0,0);
        List<Map<String, String>> yamlList = getYamlList(yamlPath);
        Object[][] cases = new Object[yamlList.size()][];
        for (int i = 0; i< yamlList.size(); i++) {
            cases[i] = new Object[] {yamlList.get(i)};
        }
        return cases;
    }
}
