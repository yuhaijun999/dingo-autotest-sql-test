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

import org.apache.commons.lang.RandomStringUtils;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Random;

public class GetRandomValue {
    private static Random rd = new Random();

    //获取最大值为max以内的随机int整数
    public static int getRandInt(int max) {
        return rd.nextInt(max) + 1;
    }

    //获取随机long整数
    public static long getRandLong() {
        return rd.nextLong();
    }

    //获取max以内的随机浮点数
    public static double getRandDouble(int min, int max, int scaleNum) {
        double d0 = min + ((max -min) * rd.nextDouble());
        BigDecimal bg = new BigDecimal(d0);
        double d1 = bg.setScale(scaleNum, BigDecimal.ROUND_HALF_UP).doubleValue();
        return d1;
    }

    //获取指定长度的字母和数字组成的字符串
    public static String getRandStr(int strLeng) {
        return RandomStringUtils.randomAlphanumeric(strLeng);
    }

    //获取指定长度的纯数字组成的字符串
    public static String getRandNumStr(int strLeng) {
        return RandomStringUtils.randomNumeric(strLeng);
    }

    //获取随机日期，返回Date型
    public static Date getRandomDate(String patternFormat, String startDateStr, String endDateStr) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(patternFormat);
        long start = sdf.parse(startDateStr).getTime();
        long end = sdf.parse(endDateStr).getTime();
        long randomDate = nextLong(start, end);
        return Date.valueOf(sdf.format(randomDate));
    }

    //获取随机时间，返回时间型
    public static Time getRandomTime(String patternFormat, String startTimeStr, String endTimeStr) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(patternFormat);
        long start = sdf.parse(startTimeStr).getTime();
        long end = sdf.parse(endTimeStr).getTime();
        long randomTime = nextLong(start, end);
        return Time.valueOf(sdf.format(randomTime));
    }

    //获取随机日期时间，返回timestamp型
    public static Timestamp getRandomTimestamp(String patternFormat, String startTimestampStr, String endTimestampStr) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(patternFormat);
        long start = sdf.parse(startTimestampStr).getTime();
        long end = sdf.parse(endTimestampStr).getTime();
        long randomTimestamp = nextLong(start, end);
        return Timestamp.valueOf(sdf.format(randomTimestamp));
    }

    public static long nextLong(long start, long end) {
        Random random = new Random();
        return start + (long) (random.nextDouble() * (end - start + 1));
    }
    
    public static boolean getRandBoolean() {
        return rd.nextBoolean();
    }
}
