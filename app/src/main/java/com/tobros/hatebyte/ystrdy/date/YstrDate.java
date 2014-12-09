package com.tobros.hatebyte.ystrdy.date;

import java.util.Date;

/**
 * Created by scott on 12/1/14.
 */
public class YstrDate {

    public static Boolean hasFirstDayPassed(Date date) {
        Date newDate                     = new Date();
        return (Math.abs(date.getTime() - newDate.getTime()) > YstrDate.firstDayTime());
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

    public static long gpsUpdateRate() {
        long onehour = (60 * 60 + 1) * 1000;
        long randomOffset = (long) Math.random() * 10;
        return randomOffset + onehour;
    }
}
