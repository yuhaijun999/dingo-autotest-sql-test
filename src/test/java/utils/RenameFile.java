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

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class RenameFile {
    public static List<String> fileList = new ArrayList<>();
    public static void main(String[] args) {
        File folder = new File("src/test/resources/io.dingodb.test/testdata/txnlsmmysqlcases/protocol/expectedresult/");
        traverseFolder(folder);
//        traverseFolderFirstDepth(folder);
        System.out.println(fileList.size());
        int count = 0;
        for (String s : fileList) {
            File file = new File(s);
            Path oldFilePath = Paths.get(s);
            String oldFileName = oldFilePath.getFileName().toString();
//            String oldFileName = s.substring(s.lastIndexOf("\\") + 1);
//            String newFileName = "txn" + oldFileName;
            String newFileName = oldFileName.replace("txnbtree","txnlsm");
            String newFilePath = oldFilePath.getParent().toString() + "\\" + newFileName;
//            System.out.println(newFilePath);
            File newFile = new File(newFilePath);
            boolean isRenamed = file.renameTo(newFile);
            if (isRenamed) {
                System.out.println(s + "文件名修改成功！");
                count += 1;
            } else {
                System.out.println(s + "文件名修改失败！");
                try {
                    FileUtils.copyFile(file, newFile);
                    if (!file.delete()) {
                        FileUtils.deleteQuietly(newFile);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

//            try {
//                FileUtils.moveFile(file, newFile);
//                System.out.println(s + "文件名修改成功！");
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
        }
        System.out.println("共修改文件个数：" + count);
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
}
