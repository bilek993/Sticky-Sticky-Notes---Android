package com.jakubbilinski.stickystickynotesandroid;

import com.jakubbilinski.stickystickynotesandroid.helpers.DateConverter;

import org.junit.Test;

import java.util.Calendar;

import static org.junit.Assert.assertEquals;

/**
 * Created by jbili on 10.11.2017.
 */

public class DateConverterUnitTests {

    @Test
    public void stringToCalendarTest() throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2017);
        calendar.set(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 25);
        calendar.set(Calendar.HOUR_OF_DAY, 1);
        calendar.set(Calendar.MINUTE, 2);
        calendar.set(Calendar.SECOND, 30);
        calendar.set(Calendar.MILLISECOND, 0);

        assertEquals(calendar.getTime().getTime(),
                DateConverter.stringToCalendar("2017-02-25T01:02:30").getTime().getTime());
    }

    @Test
    public void calendarToStringTest() throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2017);
        calendar.set(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 25);
        calendar.set(Calendar.HOUR_OF_DAY, 1);
        calendar.set(Calendar.MINUTE, 2);
        calendar.set(Calendar.SECOND, 30);
        calendar.set(Calendar.MILLISECOND, 0);

        assertEquals("2017-02-25T01:02:30", DateConverter.calendarToDate(calendar));
    }
}
