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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateTimeUtils {
    
    public static String getCurDate(String pattern) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        LocalDateTime localDateTime = LocalDateTime.now();
        String formatCurDateStr = dateTimeFormatter.format(localDateTime);
        return formatCurDateStr;
    }
    
    public static String getCurTime(String pattern) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        LocalDateTime localDateTime = LocalDateTime.now();
        String formatCurTimeStr = dateTimeFormatter.format(localDateTime);
        return formatCurTimeStr;
    }
    
    public static String getCurTimestamp(String pattern) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        LocalDateTime localDateTime = LocalDateTime.now();
        String formatDateTimeStr = dateTimeFormatter.format(localDateTime);
        return formatDateTimeStr;
    }
    
    public static long getCurUTS() {
        return System.currentTimeMillis()/1000;
    }
    
    public static long getDiffDateStartCur(String inputDate) throws ParseException {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String curDate = simpleDateFormat.format(date);
        Date startDate = simpleDateFormat.parse(curDate);
        Date endDate = simpleDateFormat.parse(inputDate);
        long diffDate = (startDate.getTime() - endDate.getTime())/(1000*60*60*24);
        return diffDate;
    }

    public static long getDiffDateEndCur(String inputDate) throws ParseException {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String curDate = simpleDateFormat.format(date);
        Date startDate = simpleDateFormat.parse(inputDate);
        Date endDate = simpleDateFormat.parse(curDate);
        long diffDate = (startDate.getTime() - endDate.getTime())/(1000*60*60*24);
        return diffDate;
    }
    
}
