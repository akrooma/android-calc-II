package com.example.kristjan.myreceiver;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Kristjan on 10/04/2016.
 */
public class DateUtil {
    /***
     * Gets the year, month and day of a date in milliseconds.
     * @param date - a date?
     * @return year, month and day of a date in milliseconds.
     */
    public static long getDateInMilliseconds(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        return setTimeTo0(calendar);
    }

    public static long getDateInMilliseconds(Long dateInMillis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(dateInMillis);

        return setTimeTo0(calendar);
    }

    private static long setTimeTo0(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTimeInMillis();
    }
}
