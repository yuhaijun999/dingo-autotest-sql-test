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

package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ParseCsv {
    public static List<List<String>> splitCsvString(String csvPath, String splitCha) {
        List<List<String>> splitList = new ArrayList<>();

        try {
            File file = new File(csvPath);
            if (!file.exists()) {
                System.out.println("csv文件不存在！");
            } else {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(csvPath));
                String line = null;
                while ((line = bufferedReader.readLine()) != null) {
                    List<String> rowList = new ArrayList<>();
                    rowList = CastUtils.construct1DListIncludeBlank(line, splitCha);
                    splitList.add(rowList);
                }
                bufferedReader.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return splitList;
    }

    public static List<String> splitCsvToList(String csvPath) {
        List<String> splitList = new ArrayList<>();

        try {
            File file = new File(csvPath);
            if (!file.exists()) {
                System.out.println("csv文件不存在！");
            } else {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(csvPath));
                String line = null;
                while ((line = bufferedReader.readLine()) != null) {
                    splitList.add(line);
                }
                bufferedReader.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return splitList;
    }

}
