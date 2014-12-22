package com.twobros.hatebyte.ystrdy.date;

import java.util.Date;

/**
 * Created by scott on 12/1/14.
 */
public class YstrDate {

    public static Boolean hasFirstDayPassed(Date date) {
        Date newDate                     = new Date();
        return (Math.abs(date.getTime() - newDate.getTime()) > YstrDate.firstDayTime());
    }

    public static Boolean isOlderThanEighteenHours(Date date) {
        Date now = new Date();
        long diff = now.getTime() - date.getTime();
        return diff > YstrDate.eighteenHours();
    }

    public static Boolean isYoungerThanThirtyHours(Date date) {
        Date now = new Date();
        long diff = now.getTime() - date.getTime();
        return diff < YstrDate.thirtyHours();
    }

    public static Boolean isWithinTwoHoursOfNow(Date date) {
        Date now = new Date();
        long diff = now.getTime() - date.getTime();
        return Math.abs(diff) < YstrDate.twoHours();
    }

    public static Date ystrdy() {
        Date now = new Date();
        now.setTime(now.getTime() - YstrDate.twentyFourHours());
        return now;
    }

    public static long firstDayTime() {
        return (18 * 60 * 60 + 1) * 1000;
    }
    public static long threeDayTime() {
        return (long)3 * YstrDate.twentyFourHours();
    }
    public static long twentyFourHours() {
        return (24 * 60 * 60 + 1) * 1000;
    }
    public static long twoHours() {
        return (2 * 60 * 60 + 1) * 1000;
    }
    public static long eighteenHours() {
        return (18 * 60 * 60 + 1) * 1000;
    }
    public static long thirtyHours() {
        return (30 * 60 * 60 + 1) * 1000;
    }

    public static long gpsUpdateRate() {
        long onehour = (60 * 60 + 1) * 1000;
        long randomOffset = (long) Math.random() * 10;
        return randomOffset + onehour;
    }

}
