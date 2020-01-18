package com.advance.mistra.utils.date;

import org.apache.commons.lang3.time.FastDateFormat;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * @Author: Mistra
 * @Version: 1.0
 * @Time: 2020/1/18 18:28
 * @Description:
 * @Copyright (c) Mistra,All Rights Reserved.
 * @Github: https://github.com/MistraR
 * @CSDN: https://blog.csdn.net/axela30w
 */
public class DateUtil {

    public static FastDateFormat dayFormat = FastDateFormat.getInstance("yyyy-MM-dd");
    public static FastDateFormat timeFormat = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");
    public final static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private static java.time.format.DateTimeFormatter
            dtf14 = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /***
     * 获取某年某月的开始时间
     * @param year 年
     * @param month 月
     * @return 日期
     */
    public static Date getBeginTime(int year, int month) {
        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDate localDate = yearMonth.atDay(1);
        LocalDateTime startOfDay = localDate.atStartOfDay();
        ZonedDateTime zonedDateTime = startOfDay.atZone(ZoneId.of("Asia/Shanghai"));
        return Date.from(zonedDateTime.toInstant());
    }

    /***
     * 获取某年某月的结束时间
     * @param year 年
     * @param month 月
     * @return 日期
     */
    public static Date getEndTime(int year, int month) {
        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDate endOfMonth = yearMonth.atEndOfMonth();
        LocalDateTime localDateTime = endOfMonth.atTime(23, 59, 59, 999);
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.of("Asia/Shanghai"));
        return Date.from(zonedDateTime.toInstant());
    }

    /***
     * 获取某月开始时间
     * @param date 输入日期
     * @return 日期
     */
    public static Date getMonthBiginTime(Date date){
        Calendar calendar = getCalendar(date);
        return getBeginTime(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH)+1);
    }

    /***
     * 获取某月结束时间
     * @param date 输入日期
     * @return 日期
     */
    public static Date getMonthEndTime(Date date){
        Calendar calendar = getCalendar(date);
        return getEndTime(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH)+1);
    }


    /***
     * 获取本周一的日期
     * @param date 输入日期
     * @return 日期
     */
    public static Date getMonday(Date date){
        return getDayOfWeek(date,1);
    }

    /***
     * 获取本周末的日期
     * @param date 输入日期
     * @return 日期
     */
    public static Date getSunDay(Date date){
        return getDayOfWeek(date,7);
    }

    private static Date getDayOfWeek(Date date,int add){
        Calendar calendar = getCalendar(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)-1;
        if(dayOfWeek==0){
            dayOfWeek=7;
        }
        calendar.add(Calendar.DATE,-dayOfWeek+add);
        return calendar.getTime();
    }

    private static Calendar getCalendar(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    public static long getUnixTime() {
        return (System.currentTimeMillis() / 1000);
    }

    public static String timeformatTime(Date date, String formatStr) {
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        return format.format(date);
    }

    public static String timeformatTime(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        return format.format(time);
    }

    public static String formatUnixTime(Long time, String pattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(new Date(time * 1000));
    }

    public static int timeToUnixTime2(String time) throws ParseException {
        DateFormat format = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
        return (int) (format.parse(time).getTime() / 1000);
    }

    public static int timeToUnixTime(String time) throws ParseException {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return (int) (format.parse(time).getTime() / 1000);
    }

    public static int dayToUnixTime(String day) throws ParseException {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return (int) (format.parse(day).getTime() / 1000);
    }

    public static LocalDateTime localDateTime(String s) {
        return LocalDateTime.parse(s, dtf14);
    }

    /**
     * 根据unix时间算出时间那天的开始时间的unix值
     *
     * @param unixTime
     * @return
     */
    public static long dayStart(long unixTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis((long) unixTime * 1000);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTimeInMillis() / 1000;
    }

    /**
     * 根据unix时间算出当月的开始时间的unix值
     *
     * @param unixTime
     * @return
     */
    public static long monthStart(long unixTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(unixTime * 1000);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTimeInMillis() / 1000;
    }


    /**
     * 根据unix时间算出当周的开始时间的unix值
     *
     * @param unixTime
     * @return
     */
    public static long weekStart(long unixTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(unixTime * 1000);
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getActualMinimum(Calendar.DAY_OF_WEEK));
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTimeInMillis() / 1000;
    }


    /**
     * 根据unix时间算出当周的结束时间的unix值
     *
     * @param unixTime
     * @return
     */
    public static long weekEnd(long unixTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(unixTime * 1000);
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getActualMaximum(Calendar.DAY_OF_WEEK));
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTimeInMillis() / 1000;
    }


    public static Date unixTimeToDate(int unixTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis((long) unixTime * 1000);
        return calendar.getTime();
    }

    /**
     * 根据unix时间算出当月的结束时间的unix值
     *
     * @param unixTime
     * @return
     */
    public static long monthEnd(long unixTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(unixTime * 1000);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTimeInMillis() / 1000;
    }

    /**
     * 根据unix时间算出时间那天的结束时间的unix值
     *
     * @param unixTime
     * @return
     */
    public static long dayEnd(long unixTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(unixTime * 1000);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTimeInMillis() / 1000;
    }

    /**
     * 计算两天相差的天数
     *
     * @param startDay
     * @param endDay
     * @return
     */
    public static int daysAway(Date startDay, Date endDay) {
        Calendar start = Calendar.getInstance();
        start.setTime(startDay);
        start.set(Calendar.HOUR_OF_DAY, 0);
        start.set(Calendar.MINUTE, 0);
        start.set(Calendar.SECOND, 0);
        start.set(Calendar.MILLISECOND, 0);
        Calendar end = Calendar.getInstance();
        end.setTime(endDay);
        end.set(Calendar.HOUR_OF_DAY, 0);
        end.set(Calendar.MINUTE, 0);
        end.set(Calendar.SECOND, 0);
        end.set(Calendar.MILLISECOND, 0);
        return (int) ((end.getTimeInMillis() - start.getTimeInMillis()) / 1000 / 60 / 60 / 24);
    }

    /**
     * 算出距离某个unix时刻多少秒的时间戳
     *
     * @param unixTime
     * @return
     */
    public static int fromTimeToUnix(int unixTime, int sec) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis((unixTime - sec) * 1000L);
        return (int) (calendar.getTimeInMillis() / 1000);
    }

    /**
     * 根据时分秒获得某天时刻的unix值
     *
     * @return
     */
    public static int unixByHMS(int unixTime, int hour, int minute, int sencond) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis((long) unixTime * 1000);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, sencond);
        return (int) (calendar.getTimeInMillis() / 1000);
    }

    public static Date getYearStartDate(Integer year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, Calendar.JANUARY, calendar.getActualMinimum(Calendar.DAY_OF_MONTH), 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static Date getYearEndDate(Integer year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, Calendar.DECEMBER, calendar.getActualMaximum(Calendar.DAY_OF_MONTH), 23, 59, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }


    /**
     * 次日
     *
     * @return
     */
    public static String nextDay() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 1);
        date = calendar.getTime();
        return dayFormat.format(date);
    }


    /**
     * 次周
     *
     * @return
     */
    public static String getNextWeekMonday() {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(getThisWeekMonday(date));
        cal.add(Calendar.DATE, 7);
        date = cal.getTime();
        return dayFormat.format(date);

    }


    /**
     * 次月
     *
     * @return
     */
    public static String getFirstDayOfNextMonth() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.MONTH, 1);
        return dayFormat.format(calendar.getTime());
    }


    /**
     * 当前季度开始时间戳
     *
     * @return
     */
    public static long getCurrentQuarterStartTime() throws ParseException {
        Calendar c = Calendar.getInstance();
        int currentMonth = c.get(Calendar.MONTH) + 1;
        Date now = null;
        if (currentMonth >= 1 && currentMonth <= 3) {
            c.set(Calendar.MONTH, 0);

        } else if (currentMonth >= 4 && currentMonth <= 6) {
            c.set(Calendar.MONTH, 3);

        } else if (currentMonth >= 7 && currentMonth <= 9) {
            c.set(Calendar.MONTH, 4);
        } else if (currentMonth >= 10 && currentMonth <= 12) {
            c.set(Calendar.MONTH, 9);

        }
        c.set(Calendar.DATE, 1);
        now = timeFormat.parse(dayFormat.format(c.getTime()) + " 00:00:00");
        return now.getTime() / 1000;
    }

    /**
     * 当前季度结束时间戳
     *
     * @return
     */
    public static long getCurrentQuarterEndTime() throws ParseException {
        Calendar c = Calendar.getInstance();
        int currentMonth = c.get(Calendar.MONTH) + 1;
        Date now = null;
        if (currentMonth >= 1 && currentMonth <= 3) {
            c.set(Calendar.MONTH, 2);
            c.set(Calendar.DATE, 31);
        } else if (currentMonth >= 4 && currentMonth <= 6) {
            c.set(Calendar.MONTH, 5);
            c.set(Calendar.DATE, 30);
        } else if (currentMonth >= 7 && currentMonth <= 9) {
            c.set(Calendar.MONTH, 8);
            c.set(Calendar.DATE, 30);
        } else if (currentMonth >= 10 && currentMonth <= 12) {
            c.set(Calendar.MONTH, 11);
            c.set(Calendar.DATE, 31);
        }
        now = timeFormat.parse(dayFormat.format(c.getTime()) + " 23:59:59");
        return now.getTime() / 1000;
    }


    /**
     * 获取下季度开始日期
     *
     * @return
     */
    public static String getNextQuarterEndTime() throws ParseException {
        Calendar calendar = Calendar.getInstance();
        int currentMonth = calendar.get(Calendar.MONTH) + 1;
        Date now = null;
        if (currentMonth >= 1 && currentMonth <= 3) {
            calendar.set(Calendar.MONTH, 2);
            calendar.set(Calendar.DATE, 31);
        } else if (currentMonth >= 4 && currentMonth <= 6) {
            calendar.set(Calendar.MONTH, 5);
            calendar.set(Calendar.DATE, 30);
        } else if (currentMonth >= 7 && currentMonth <= 9) {
            calendar.set(Calendar.MONTH, 8);
            calendar.set(Calendar.DATE, 30);
        } else if (currentMonth >= 10 && currentMonth <= 12) {
            calendar.set(Calendar.MONTH, 11);
            calendar.set(Calendar.DATE, 31);
        }
        now = timeFormat.parse(dayFormat.format(calendar.getTime()) + " 23:59:59");
        Calendar c = new GregorianCalendar();
        c.setTime(now);
        c.add(Calendar.DATE, 1);
        now = c.getTime();
        return dayFormat.format(now);
    }


    public static Date getThisWeekMonday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        // 获得当前日期是一个星期的第几天
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        // 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        // 获得当前日期是一个星期的第几天
        int day = cal.get(Calendar.DAY_OF_WEEK);
        // 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
        return cal.getTime();
    }

    /**
     * 获取当天零点时间戳
     */
    public static long getTodayStartTime() {
        long nowTime =System.currentTimeMillis();
        long todayStartTime = nowTime - (nowTime + TimeZone.getDefault().getRawOffset())% (1000*3600*24);
        return todayStartTime;
    }
}
