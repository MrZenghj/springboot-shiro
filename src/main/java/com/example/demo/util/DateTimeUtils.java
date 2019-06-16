package com.example.demo.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
/**
 *  关于时间转化工具
 * */
public class DateTimeUtils {

    public static Date convertToDate(LocalDateTime localDateTime){
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date convertToDate(LocalDate localDate){
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public static LocalDateTime convertToLocalDateTime(Date date){
        return LocalDateTime.ofInstant(date.toInstant(),ZoneId.systemDefault());
    }

    public static LocalDate convertToLocalDate(Date date){
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static long toMilliseconds(LocalDateTime localDateTime){
        return localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    public static LocalDateTime toLocalDateTime(long longValue){
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(longValue),ZoneId.systemDefault());
    }

    public static long minutesUntilNow(LocalDateTime localDateTime){
        return LocalDateTime.now().until(localDateTime, ChronoUnit.MINUTES);
    }

    public static LocalDateTime toLocalDateTime(String dateTimeStr,String pattern){
        return LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ofPattern(pattern));
    }

    public static String toString(LocalDateTime dateTime,String pattern){
        return dateTime.format(DateTimeFormatter.ofPattern(pattern));
    }
}
