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
import org.yaml.snakeyaml.Yaml;
import utils.GetYaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class BaseDataHelper {
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
    
    protected Object[][] getMultiEngineCasesData(String excelPathLSM, String excelPathBTREE, String excelPathTXNBTREE) throws IOException, InterruptedException {
        String yamlPathLSM = GetYaml.convertExcelToYaml(excelPathLSM,0,0);
        String yamlPathBTREE = GetYaml.convertExcelToYaml(excelPathBTREE,0,0);
        String yamlPathTXNBTREE = GetYaml.convertExcelToYaml(excelPathTXNBTREE,0,0);
        List<Map<String, String>> yamlListLSM = getYamlList(yamlPathLSM);
        List<Map<String, String>> yamlListBTREE = getYamlList(yamlPathBTREE);
        List<Map<String, String>> yamlListTXNBTREE = getYamlList(yamlPathTXNBTREE);
        Object[][] cases_lsm = new Object[yamlListLSM.size()][];
        for (int i = 0; i< yamlListLSM.size(); i++) {
            cases_lsm[i] = new Object[] {yamlListLSM.get(i)};
        }
        Object[][] cases_btree = new Object[yamlListBTREE.size()][];
        for (int i = 0; i< yamlListBTREE.size(); i++) {
            cases_btree[i] = new Object[] {yamlListBTREE.get(i)};
        }
        Object[][] cases_txnbtree = new Object[yamlListTXNBTREE.size()][];
        for (int i = 0; i< yamlListTXNBTREE.size(); i++) {
            cases_txnbtree[i] = new Object[] {yamlListTXNBTREE.get(i)};
        }
        List<Object[]> cases_merge = Lists.newArrayList();
        cases_merge.addAll(Arrays.asList(cases_lsm));
        cases_merge.addAll(Arrays.asList(cases_btree));
//        cases_merge.addAll(Arrays.asList(cases_txnbtree));
        return cases_merge.toArray(new Object[cases_merge.size()][]);
    }

    protected Object[][] getSingleEngineCasesData(String excelPath) throws IOException, InterruptedException {
        String yamlPath = GetYaml.convertExcelToYaml(excelPath,0,0);
        List<Map<String, String>> yamlList = getYamlList(yamlPath);
        Object[][] cases = new Object[yamlList.size()][];
        for (int i = 0; i< yamlList.size(); i++) {
            cases[i] = new Object[] {yamlList.get(i)};
        }
        return cases;
    }
    
}
