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

package io.dingodb.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CommonArgs {
    public static String getDefaultDingoClusterIP() {
        if (System.getenv().containsKey("ConnectIP")) {
            return System.getenv("ConnectIP");
        }
        return  "172.20.61.107";
    }

    public static String getDefaultConnectUser() {
        if (System.getenv().containsKey("ConnectUser")) {
            return System.getenv("ConnectUser");
        }
        return  null;
    }

    public static String getDefaultConnectPass() {
        if (System.getenv().containsKey("ConnectPass")) {
            return System.getenv("ConnectPass");
        }
        return  null;
    }

    //获取dingo-store仓库的最近提交ID
    public static String getStoreCommitID() {
        if (System.getenv().containsKey("COMMIT_ID")) {
            return System.getenv("COMMIT_ID");
        }
        return  "";
    }

    //获取dingo仓库的最近提交ID
    public static String getDingoCommitID() {
        if (System.getenv().containsKey("DINGO_COMMIT_ID")) {
            return System.getenv("DINGO_COMMIT_ID");
        }
        return  "";
    }

    //获取当前日期的字符串
    public static String getCurDateStr(String pattern) {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String dateNowStr = simpleDateFormat.format(date);
        return dateNowStr;
    }
}
