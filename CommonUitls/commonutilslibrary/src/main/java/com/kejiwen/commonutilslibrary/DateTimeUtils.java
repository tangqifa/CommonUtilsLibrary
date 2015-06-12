package com.kejiwen.commonutilslibrary;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTimeUtils {
    private static final long SECONDS_PER_DAY = 3600 * 24;
    private static final long SECONDS_PER_HOUR = 3600;
    private static final long SECONDS_PER_MINUTE = 60;

    private static SimpleDateFormat sDateTimeFormat = null;
    private static SimpleDateFormat sDateFormat = null;

    public static SimpleDateFormat getDefaultFormatInstance() {
        if (sDateTimeFormat == null) {
            sDateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
        return sDateTimeFormat;
    }

    private static SimpleDateFormat getDateFormatInstance() {
        if (sDateFormat == null) {
            sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        }
        return sDateFormat;
    }

    /**
     * @param time returned by System.currentTimeMillis(), in milliseconds.
     * @return In format "yyyy-MM-dd HH:mm:ss"
     */
    public static String formatDefault(long time) {
        return getDefaultFormatInstance().format(new Date(time));
    }

    /**
     * Format time to stirng "yyyy-MM-dd"
     */
    public static String formatDate(long time) {
        return getDateFormatInstance().format(new Date(time));
    }

    /**
     * Convert system time to integer with format: yyyymmdd (such as 20130417)
     * 
     * @param sysTime System time from System.currentTimeMillis()
     */
    public static long getDayFromSysTime(long sysTime) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(sysTime);
        return c.get(Calendar.YEAR) * 10000 + (c.get(Calendar.MONTH) + 1) * 100
                + c.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * Convert system time to integer with format: yyyymmddhh (such as 2013041817)
     * 
     * @param sysTime System time from System.currentTimeMillis()
     */
    public static long getHourFromSysTime(long sysTime) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(sysTime);
        return c.get(Calendar.YEAR) * 1000000 + (c.get(Calendar.MONTH) + 1) * 10000
                + c.get(Calendar.DAY_OF_MONTH) * 100 + c.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * Convert system time to integer with format: yyyymmddhh (such as 2013042016)
     * 
     * @param sysTime System time from System.currentTimeMillis()
     */
    public static long getTwoHourFromSysTime(long sysTime) {
        long hour = getHourFromSysTime(sysTime);
        return hour / 2 * 2;
    }

    public static int getRealHourFromFormedHour(long formedHour) {
        return (int) (formedHour % 100);
    }

    /**
     * Get yesterday first hour with format: yyyymmdd (such as 2013041718)
     * 
     * @param sysTime System time from System.currentTimeMillis()
     */
    public static long getYesterDayFirstHourFromSysTime(long sysTime) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(sysTime);
        c.add(Calendar.DAY_OF_YEAR, -1);// yesterday
        return c.get(Calendar.YEAR) * 1000000 + (c.get(Calendar.MONTH) + 1) * 10000
                + c.get(Calendar.DAY_OF_MONTH) * 100;
    }

    /**
     * Get yesterday last hour with format: yyyymmdd (such as 2013041718)
     * 
     * @param sysTime System time from System.currentTimeMillis()
     */
    public static long getYesterDayLastHourFromSysTime(long sysTime) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(sysTime);
        c.add(Calendar.DAY_OF_YEAR, -1);// yesterday
        return c.get(Calendar.YEAR) * 1000000 + (c.get(Calendar.MONTH) + 1) * 10000
                + c.get(Calendar.DAY_OF_MONTH) * 100 + 23;
    }

    /**
     * Get yesterday with format: yyyymmdd (such as 20130417)
     * 
     * @param sysTime System time from System.currentTimeMillis()
     */
    public static long getYesterDayFromSysTime(long sysTime) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(sysTime);
        c.add(Calendar.DAY_OF_YEAR, -1);// yesterday
        return c.get(Calendar.YEAR) * 10000 + (c.get(Calendar.MONTH) + 1) * 100
                + c.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * Get the before yesterday with format: yyyymmdd (such as 20130417)
     * 
     * @param sysTime System time from System.currentTimeMillis()
     */
    public static long getBeforeYesterDayFromSysTime(long sysTime) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(sysTime);
        c.add(Calendar.DAY_OF_YEAR, -2);// the day before yesterday
        return c.get(Calendar.YEAR) * 10000 + (c.get(Calendar.MONTH) + 1) * 100
                + c.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * Get last week last day with format: yyyymmdd (such as 20130417)
     * 
     * @param sysTime System time from System.currentTimeMillis()
     */
    private static Calendar getCalendarOfLastDayOfLastWeekFromSysTime(long sysTime) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(sysTime);
        c.add(Calendar.WEEK_OF_YEAR, -1);
        while (c.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
            c.add(Calendar.DATE, 1);
        }
        return c;
    }

    /**
     * Get last week last day with format: yyyymmdd (such as 20130417)
     * 
     * @param sysTime System time from System.currentTimeMillis()
     */
    public static long getLastDayOfLastWeekFromSysTime(long sysTime) {
        Calendar c = getCalendarOfLastDayOfLastWeekFromSysTime(sysTime);
        return c.get(Calendar.YEAR) * 10000 + (c.get(Calendar.MONTH) + 1) * 100
                + c.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * Get last week first day with format: yyyymmdd (such as 20130417)
     * 
     * @param sysTime System time from System.currentTimeMillis()
     */
    public static long getFirstDayOfLastWeekFromSysTime(long sysTime) {
        Calendar c = getCalendarOfLastDayOfLastWeekFromSysTime(sysTime);
        c.add(Calendar.DAY_OF_YEAR, -6);
        return c.get(Calendar.YEAR) * 10000 + (c.get(Calendar.MONTH) + 1) * 100
                + c.get(Calendar.DAY_OF_MONTH);
    }
}
