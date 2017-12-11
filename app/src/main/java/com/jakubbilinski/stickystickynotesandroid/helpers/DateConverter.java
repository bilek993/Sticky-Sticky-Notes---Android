package com.jakubbilinski.stickystickynotesandroid.helpers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by jbili on 10.11.2017.
 */

public class DateConverter {

    private static String pattern = "yyyy-MM-dd'T'HH:mm:ss";

    public static String calendarToDate(Calendar calendarDate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, Locale.ENGLISH);
        return simpleDateFormat.format(calendarDate.getTime());
    }

    public static Calendar stringToCalendar(String stringDate) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, Locale.ENGLISH);

        try {
            calendar.setTime(simpleDateFormat.parse(stringDate));
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }

        return calendar;
    }

    public static String timezoneDateToNormal(String stringDate) {
        return stringDate.replaceAll("T", " ");
    }
}
