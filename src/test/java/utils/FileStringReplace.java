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

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileStringReplace {
    public static List<String> fileList = new ArrayList<>();
    public static void main(String[] args) throws IOException {
        File folder = new File("src/test/resources/io.dingodb.test/tabledata/txn_lsm/");
//        traverseFolderFirstDepth(folder);
        traverseFolder(folder);
        String oldString = "=LSM";
        String newString = "=TXN_LSM";
        int fileCount=0;
        for (String s : fileList) {
            stringReplace(s, oldString, newString);
            fileCount += 1;
        }
        System.out.println("共修改文件数： " + fileCount);
        
    }

    public static void traverseFolder(File folder) {
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    traverseFolder(file);
                } else {
                    fileList.add(file.getAbsolutePath());
                }
            }
        }
    }

    public static void traverseFolderFirstDepth(File folder) {
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    fileList.add(file.getAbsolutePath());
                }
            }
        }
    }
    
    public static void stringReplace(String filePath, String oldString, String newString) throws IOException {
        Path file = Paths.get(filePath);
        List<String> lines = Files.readAllLines(file);

        for (int i = 0; i < lines.size(); i++) {
            lines.set(i, lines.get(i).replace(oldString, newString));
        }

        Files.write(file, lines);
    }
    
}
