package com.example.kristjan.myreceiver;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Kristjan on 10/04/2016.
 *
 * This class holds a bunch of methods to get the millisecond format of a datestamp and
 * string formats of a date- / timestamp.
 */
public class DateUtil {
    /***
     * Gets the date portion(time portion is nullified) of a Date.
     * @param date - a Date object.
     * @return year, month and day of a date in milliseconds.
     */
    public static long getDateInMilliseconds(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        return setTimeTo0(calendar);
    }

    /***
     * Gets the date portion(time portion is nullified) of a Date.
     * @param timestampInMillis - a Date / timestamp in milliseconds.
     * @return date in milliseconds.
     */
    public static long getDateInMilliseconds(Long timestampInMillis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestampInMillis);

        return setTimeTo0(calendar);
    }

    // sets all the time(hour, minute, second etc) parameters to 0 so we just get the date in milliseconds.
    private static long setTimeTo0(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTimeInMillis();
    }

    /***
     * Gets the String representation (format: yyyy-MM-dd) of a dateStamp.
     * @param dateStamp dateStamp in milliseconds.
     * @return dateStamp as a String.
     */
    public static String dateStampToString(long dateStamp) {
        Date date = new Date(dateStamp);
        SimpleDateFormat dateStampFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateStampFormat.format(date);
    }

    /***
     * Gets the String representation (format: yyyy-MM-dd hh:mm:ss) of a timestamp.
     * @param timestamp - timestamp in milliseconds.
     * @return timestamp as a String.
     */
    public static String timestampToString(long timestamp) {
        Date date = new Date(timestamp);
        SimpleDateFormat timestampFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return timestampFormat.format(date);
    }
}
