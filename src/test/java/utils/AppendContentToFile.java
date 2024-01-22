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
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AppendContentToFile {
    
    public static List<String> fileList = new ArrayList<>();
    public static void main(String[] args) {
        File folder = new File("src/test/resources/io.dingodb.test/tabledata/lsm/meta/");
        traverseFolderFirstDepth(folder);
//        traverseFolder(folder);
//        System.out.println(fileList);
        String engineName = " ENGINE=LSM";
        int fileCount=0;
        for (String s : fileList) {
            appendContent(s, engineName);
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
    
    public static void appendContent(String filePath, String content){
//        String content = " ENGINE=TXN_BTREE";
        
        try {
            FileWriter writer = new FileWriter(filePath, true);
            writer.append(content);
            writer.close();
            System.out.println(filePath + "追加内容成功");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
