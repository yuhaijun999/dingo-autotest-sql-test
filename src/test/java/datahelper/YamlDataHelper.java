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

import org.apache.commons.compress.utils.Lists;
import org.testng.annotations.DataProvider;
import org.yaml.snakeyaml.Yaml;
import utils.GetYaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class YamlDataHelper {
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

    @DataProvider (name = "dqlData1", parallel = false)
    public Object[][] dqlCaseGroup1() throws IOException, InterruptedException {
        String excelPathLSM = "src/test/resources/io.dingodb.test/testdata/cases/dql/casegroup1/sql_dql_cases1.xlsx";
        String excelPathBTREE = "src/test/resources/io.dingodb.test/testdata/btreecases/dql/casegroup1/sql_dql_cases1_btree.xlsx";
        String yamlPathLSM = GetYaml.convertExcelToYaml(excelPathLSM,0,0);
        String yamlPathBTREE = GetYaml.convertExcelToYaml(excelPathBTREE,0,0);
        List<Map<String, String>> yamlListLSM = getYamlList(yamlPathLSM);
        List<Map<String, String>> yamlListBTREE = getYamlList(yamlPathBTREE);
        Object[][] cases_lsm = new Object[yamlListLSM.size()][];
        for (int i = 0; i< yamlListLSM.size(); i++) {
            cases_lsm[i] = new Object[] {yamlListLSM.get(i)};
        }
        Object[][] cases_btree = new Object[yamlListBTREE.size()][];
        for (int i = 0; i< yamlListBTREE.size(); i++) {
            cases_btree[i] = new Object[] {yamlListBTREE.get(i)};
        }
        List<Object[]> cases_merge = Lists.newArrayList();
        cases_merge.addAll(Arrays.asList(cases_lsm));
        cases_merge.addAll(Arrays.asList(cases_btree));
        return cases_merge.toArray(new Object[cases_merge.size()][]);
    }

    @DataProvider (name = "dqlData2", parallel = false)
    public Object[][] dqlCaseGroup2() throws IOException, InterruptedException {
        String excelPath = "src/test/resources/io.dingodb.test/testdata/cases/dql/casegroup2/sql_dql_cases2.xlsx";
        String yamlPath = GetYaml.convertExcelToYaml(excelPath,0,0);
        List<Map<String, String>> yamlList = getYamlList(yamlPath);
        Object[][] cases = new Object[yamlList.size()][];
        for (int i = 0; i< yamlList.size(); i++) {
            cases[i] = new Object[] {yamlList.get(i)};
        }
        return cases;
    }

    @DataProvider (name = "dqlData3", parallel = true)
    public Object[][] dqlCaseGroup3() throws IOException, InterruptedException {
        String excelPathLSM = "src/test/resources/io.dingodb.test/testdata/cases/dql/casegroup3/sql_dql_cases3.xlsx";
        String excelPathBTREE = "src/test/resources/io.dingodb.test/testdata/btreecases/dql/casegroup3/sql_dql_cases3_btree.xlsx";
        String yamlPathLSM = GetYaml.convertExcelToYaml(excelPathLSM,0,0);
        String yamlPathBTREE = GetYaml.convertExcelToYaml(excelPathBTREE,0,0);
        List<Map<String, String>> yamlListLSM = getYamlList(yamlPathLSM);
        List<Map<String, String>> yamlListBTREE = getYamlList(yamlPathBTREE);
        Object[][] cases_lsm = new Object[yamlListLSM.size()][];
        for (int i = 0; i< yamlListLSM.size(); i++) {
            cases_lsm[i] = new Object[] {yamlListLSM.get(i)};
        }
        Object[][] cases_btree = new Object[yamlListBTREE.size()][];
        for (int i = 0; i< yamlListBTREE.size(); i++) {
            cases_btree[i] = new Object[] {yamlListBTREE.get(i)};
        }
        List<Object[]> cases_merge = Lists.newArrayList();
        cases_merge.addAll(Arrays.asList(cases_lsm));
        cases_merge.addAll(Arrays.asList(cases_btree));
        return cases_merge.toArray(new Object[cases_merge.size()][]);
    }

    @DataProvider (name = "dmlInsertData", parallel = false)
    public Object[][] dmlInsertCases() throws IOException, InterruptedException {
        String excelPathLSM = "src/test/resources/io.dingodb.test/testdata/cases/dml/insert/sql_insert_cases.xlsx";
        String excelPathBTREE = "src/test/resources/io.dingodb.test/testdata/btreecases/dml/insert/sql_insert_cases_btree.xlsx";
        String yamlPathLSM = GetYaml.convertExcelToYaml(excelPathLSM,0,0);
        String yamlPathBTREE = GetYaml.convertExcelToYaml(excelPathBTREE,0,0);
        List<Map<String, String>> yamlListLSM = getYamlList(yamlPathLSM);
        List<Map<String, String>> yamlListBTREE = getYamlList(yamlPathBTREE);
        Object[][] cases_lsm = new Object[yamlListLSM.size()][];
        for (int i = 0; i< yamlListLSM.size(); i++) {
            cases_lsm[i] = new Object[] {yamlListLSM.get(i)};
        }
        Object[][] cases_btree = new Object[yamlListBTREE.size()][];
        for (int i = 0; i< yamlListBTREE.size(); i++) {
            cases_btree[i] = new Object[] {yamlListBTREE.get(i)};
        }
        
        List<Object[]> cases_merge = Lists.newArrayList();
        cases_merge.addAll(Arrays.asList(cases_lsm));
        cases_merge.addAll(Arrays.asList(cases_btree));
        return cases_merge.toArray(new Object[cases_merge.size()][]);
    }

    @DataProvider (name = "dmlUpDelData", parallel = false)
    public Object[][] dmlUpDelCases() throws IOException, InterruptedException {
        String excelPathLSM = "src/test/resources/io.dingodb.test/testdata/cases/dml/updatedelete/sql_updatedelete_cases.xlsx";
        String excelPathBTREE = "src/test/resources/io.dingodb.test/testdata/btreecases/dml/updatedelete/sql_updatedelete_cases_btree.xlsx";
        String yamlPathLSM = GetYaml.convertExcelToYaml(excelPathLSM,0,0);
        String yamlPathBTREE = GetYaml.convertExcelToYaml(excelPathBTREE,0,0);
        List<Map<String, String>> yamlListLSM = getYamlList(yamlPathLSM);
        List<Map<String, String>> yamlListBTREE = getYamlList(yamlPathBTREE);
        Object[][] cases_lsm = new Object[yamlListLSM.size()][];
        for (int i = 0; i< yamlListLSM.size(); i++) {
            cases_lsm[i] = new Object[] {yamlListLSM.get(i)};
        }
        Object[][] cases_btree = new Object[yamlListBTREE.size()][];
        for (int i = 0; i< yamlListBTREE.size(); i++) {
            cases_btree[i] = new Object[] {yamlListBTREE.get(i)};
        }

        List<Object[]> cases_merge = Lists.newArrayList();
        cases_merge.addAll(Arrays.asList(cases_lsm));
        cases_merge.addAll(Arrays.asList(cases_btree));
        return cases_merge.toArray(new Object[cases_merge.size()][]);
    }

    @DataProvider (name = "ddlData1", parallel = false)
    public Object[][] ddlCases1() throws IOException, InterruptedException {
        String excelPathLSM = "src/test/resources/io.dingodb.test/testdata/cases/ddl/sql_ddl_cases.xlsx";
        String excelPathBTREE = "src/test/resources/io.dingodb.test/testdata/btreecases/ddl/sql_ddl_cases_btree.xlsx";
        String yamlPathLSM = GetYaml.convertExcelToYaml(excelPathLSM,0,0);
        String yamlPathBTREE = GetYaml.convertExcelToYaml(excelPathBTREE,0,0);
        List<Map<String, String>> yamlListLSM = getYamlList(yamlPathLSM);
        List<Map<String, String>> yamlListBTREE = getYamlList(yamlPathBTREE);
        Object[][] cases_lsm = new Object[yamlListLSM.size()][];
        for (int i = 0; i< yamlListLSM.size(); i++) {
            cases_lsm[i] = new Object[] {yamlListLSM.get(i)};
        }
        Object[][] cases_btree = new Object[yamlListBTREE.size()][];
        for (int i = 0; i< yamlListBTREE.size(); i++) {
            cases_btree[i] = new Object[] {yamlListBTREE.get(i)};
        }
        List<Object[]> cases_merge = Lists.newArrayList();
        cases_merge.addAll(Arrays.asList(cases_lsm));
        cases_merge.addAll(Arrays.asList(cases_btree));
        return cases_merge.toArray(new Object[cases_merge.size()][]);
    }

    @DataProvider (name = "dclData", parallel = false)
    public Object[][] dclCases() throws IOException, InterruptedException {
        String excelPath = "src/test/resources/io.dingodb.test/testdata/cases/dcl/sql_dcl_cases.xlsx";
        String yamlPath = GetYaml.convertExcelToYaml(excelPath,0,0);
        List<Map<String, String>> yamlList = getYamlList(yamlPath);
        Object[][] cases = new Object[yamlList.size()][];
        for (int i = 0; i< yamlList.size(); i++) {
            cases[i] = new Object[] {yamlList.get(i)};
        }
        return cases;
    }

    @DataProvider (name = "sqlBatchData", parallel = false)
    public Object[][] sqlBatchCases() throws IOException, InterruptedException {
        String excelPathLSM = "src/test/resources/io.dingodb.test/testdata/cases/batchsql/sql_batchsql_cases.xlsx";
        String excelPathBTREE = "src/test/resources/io.dingodb.test/testdata/btreecases/batchsql/sql_batchsql_cases_btree.xlsx";
        String yamlPathLSM = GetYaml.convertExcelToYaml(excelPathLSM,0,0);
        String yamlPathBTREE = GetYaml.convertExcelToYaml(excelPathBTREE,0,0);
        List<Map<String, String>> yamlListLSM = getYamlList(yamlPathLSM);
        List<Map<String, String>> yamlListBTREE = getYamlList(yamlPathBTREE);
        Object[][] cases_lsm = new Object[yamlListLSM.size()][];
        for (int i = 0; i< yamlListLSM.size(); i++) {
            cases_lsm[i] = new Object[] {yamlListLSM.get(i)};
        }
        Object[][] cases_btree = new Object[yamlListBTREE.size()][];
        for (int i = 0; i< yamlListBTREE.size(); i++) {
            cases_btree[i] = new Object[] {yamlListBTREE.get(i)};
        }
        List<Object[]> cases_merge = Lists.newArrayList();
        cases_merge.addAll(Arrays.asList(cases_lsm));
        cases_merge.addAll(Arrays.asList(cases_btree));
        return cases_merge.toArray(new Object[cases_merge.size()][]);
    }

    @DataProvider (name = "negativeData", parallel = false)
    public Object[][] negativeCases() throws IOException, InterruptedException {
        String excelPathLSM = "src/test/resources/io.dingodb.test/testdata/cases/negative/sql_negative_cases.xlsx";
        String excelPathBTREE = "src/test/resources/io.dingodb.test/testdata/btreecases/negative/sql_negative_cases_btree.xlsx";
        String yamlPathLSM = GetYaml.convertExcelToYaml(excelPathLSM,0,0);
        String yamlPathBTREE = GetYaml.convertExcelToYaml(excelPathBTREE,0,0);
        List<Map<String, String>> yamlListLSM = getYamlList(yamlPathLSM);
        List<Map<String, String>> yamlListBTREE = getYamlList(yamlPathBTREE);
        Object[][] cases_lsm = new Object[yamlListLSM.size()][];
        for (int i = 0; i< yamlListLSM.size(); i++) {
            cases_lsm[i] = new Object[] {yamlListLSM.get(i)};
        }
        Object[][] cases_btree = new Object[yamlListBTREE.size()][];
        for (int i = 0; i< yamlListBTREE.size(); i++) {
            cases_btree[i] = new Object[] {yamlListBTREE.get(i)};
        }
        List<Object[]> cases_merge = Lists.newArrayList();
        cases_merge.addAll(Arrays.asList(cases_lsm));
        cases_merge.addAll(Arrays.asList(cases_btree));
        return cases_merge.toArray(new Object[cases_merge.size()][]);
    }

    @DataProvider (name = "indexData1", parallel = false)
    public Object[][] indexCases1() throws IOException, InterruptedException {
        String excelPathLSM = "src/test/resources/io.dingodb.test/testdata/cases/index/index_cases1.xlsx";
        String excelPathBTREE = "src/test/resources/io.dingodb.test/testdata/btreecases/index/index_cases1_btree.xlsx";
        String yamlPathLSM = GetYaml.convertExcelToYaml(excelPathLSM,0,0);
        String yamlPathBTREE = GetYaml.convertExcelToYaml(excelPathBTREE,0,0);
        List<Map<String, String>> yamlListLSM = getYamlList(yamlPathLSM);
        List<Map<String, String>> yamlListBTREE = getYamlList(yamlPathBTREE);
        Object[][] cases_lsm = new Object[yamlListLSM.size()][];
        for (int i = 0; i< yamlListLSM.size(); i++) {
            cases_lsm[i] = new Object[] {yamlListLSM.get(i)};
        }
        Object[][] cases_btree = new Object[yamlListBTREE.size()][];
        for (int i = 0; i< yamlListBTREE.size(); i++) {
            cases_btree[i] = new Object[] {yamlListBTREE.get(i)};
        }
        List<Object[]> cases_merge = Lists.newArrayList();
        cases_merge.addAll(Arrays.asList(cases_lsm));
        cases_merge.addAll(Arrays.asList(cases_btree));
        return cases_merge.toArray(new Object[cases_merge.size()][]);
    }

    @DataProvider (name = "indexData2", parallel = true)
    public Object[][] indexCases2() throws IOException, InterruptedException {
        String excelPathLSM = "src/test/resources/io.dingodb.test/testdata/cases/index/index_cases2.xlsx";
        String excelPathBTREE = "src/test/resources/io.dingodb.test/testdata/btreecases/index/index_cases2_btree.xlsx";
        String yamlPathLSM = GetYaml.convertExcelToYaml(excelPathLSM,0,0);
        String yamlPathBTREE = GetYaml.convertExcelToYaml(excelPathBTREE,0,0);
        List<Map<String, String>> yamlListLSM = getYamlList(yamlPathLSM);
        List<Map<String, String>> yamlListBTREE = getYamlList(yamlPathBTREE);
        Object[][] cases_lsm = new Object[yamlListLSM.size()][];
        for (int i = 0; i< yamlListLSM.size(); i++) {
            cases_lsm[i] = new Object[] {yamlListLSM.get(i)};
        }
        Object[][] cases_btree = new Object[yamlListBTREE.size()][];
        for (int i = 0; i< yamlListBTREE.size(); i++) {
            cases_btree[i] = new Object[] {yamlListBTREE.get(i)};
        }
        List<Object[]> cases_merge = Lists.newArrayList();
        cases_merge.addAll(Arrays.asList(cases_lsm));
        cases_merge.addAll(Arrays.asList(cases_btree));
        return cases_merge.toArray(new Object[cases_merge.size()][]);
    }

    @DataProvider (name = "psDQLData", parallel = false)
    public Object[][] psDQLCases() throws IOException, InterruptedException {
        String excelPathLSM = "src/test/resources/io.dingodb.test/testdata/cases/prepareStatement/sql_ps_dql_cases.xlsx";
        String excelPathBTREE = "src/test/resources/io.dingodb.test/testdata/btreecases/prepareStatement/sql_ps_dql_cases_btree.xlsx";
        String yamlPathLSM = GetYaml.convertExcelToYaml(excelPathLSM,0,0);
        String yamlPathBTREE = GetYaml.convertExcelToYaml(excelPathBTREE,0,0);
        List<Map<String, String>> yamlListLSM = getYamlList(yamlPathLSM);
        List<Map<String, String>> yamlListBTREE = getYamlList(yamlPathBTREE);
        Object[][] cases_lsm = new Object[yamlListLSM.size()][];
        for (int i = 0; i< yamlListLSM.size(); i++) {
            cases_lsm[i] = new Object[] {yamlListLSM.get(i)};
        }
        Object[][] cases_btree = new Object[yamlListBTREE.size()][];
        for (int i = 0; i< yamlListBTREE.size(); i++) {
            cases_btree[i] = new Object[] {yamlListBTREE.get(i)};
        }
        List<Object[]> cases_merge = Lists.newArrayList();
        cases_merge.addAll(Arrays.asList(cases_lsm));
        cases_merge.addAll(Arrays.asList(cases_btree));
        return cases_merge.toArray(new Object[cases_merge.size()][]);
    }

    @DataProvider (name = "psDMLData", parallel = false)
    public Object[][] psDMLCases() throws IOException, InterruptedException {
        String excelPathLSM = "src/test/resources/io.dingodb.test/testdata/cases/prepareStatement/sql_ps_dml_cases.xlsx";
        String excelPathBTREE = "src/test/resources/io.dingodb.test/testdata/btreecases/prepareStatement/sql_ps_dml_cases_btree.xlsx";
        String yamlPathLSM = GetYaml.convertExcelToYaml(excelPathLSM,0,0);
        String yamlPathBTREE = GetYaml.convertExcelToYaml(excelPathBTREE,0,0);
        List<Map<String, String>> yamlListLSM = getYamlList(yamlPathLSM);
        List<Map<String, String>> yamlListBTREE = getYamlList(yamlPathBTREE);
        Object[][] cases_lsm = new Object[yamlListLSM.size()][];
        for (int i = 0; i< yamlListLSM.size(); i++) {
            cases_lsm[i] = new Object[] {yamlListLSM.get(i)};
        }
        Object[][] cases_btree = new Object[yamlListBTREE.size()][];
        for (int i = 0; i< yamlListBTREE.size(); i++) {
            cases_btree[i] = new Object[] {yamlListBTREE.get(i)};
        }
        List<Object[]> cases_merge = Lists.newArrayList();
        cases_merge.addAll(Arrays.asList(cases_lsm));
        cases_merge.addAll(Arrays.asList(cases_btree));
        return cases_merge.toArray(new Object[cases_merge.size()][]);
    }

    @DataProvider (name = "psBatchData", parallel = false)
    public Object[][] psBatchCases() throws IOException, InterruptedException {
        String excelPathLSM = "src/test/resources/io.dingodb.test/testdata/cases/prepareStatement/sql_ps_batch_cases.xlsx";
        String excelPathBTREE = "src/test/resources/io.dingodb.test/testdata/btreecases/prepareStatement/sql_ps_batch_cases_btree.xlsx";
        String yamlPathLSM = GetYaml.convertExcelToYaml(excelPathLSM,0,0);
        String yamlPathBTREE = GetYaml.convertExcelToYaml(excelPathBTREE,0,0);
        List<Map<String, String>> yamlListLSM = getYamlList(yamlPathLSM);
        List<Map<String, String>> yamlListBTREE = getYamlList(yamlPathBTREE);
        Object[][] cases_lsm = new Object[yamlListLSM.size()][];
        for (int i = 0; i< yamlListLSM.size(); i++) {
            cases_lsm[i] = new Object[] {yamlListLSM.get(i)};
        }
        Object[][] cases_btree = new Object[yamlListBTREE.size()][];
        for (int i = 0; i< yamlListBTREE.size(); i++) {
            cases_btree[i] = new Object[] {yamlListBTREE.get(i)};
        }
        List<Object[]> cases_merge = Lists.newArrayList();
        cases_merge.addAll(Arrays.asList(cases_lsm));
        cases_merge.addAll(Arrays.asList(cases_btree));
        return cases_merge.toArray(new Object[cases_merge.size()][]);
    }
    
}
