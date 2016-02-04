package org.itevents.util.time;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class DateTimeUtilTest {

    private final String dateFormatForTest = "dd.MM.yyyy";

    @Test
    public void shouldGetFormattedNowDatePlusOneDay() throws Exception {
        int daysToAdd = 1;
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, daysToAdd);
        String expectedString = format(calendar.getTime());

        String returnedString = DateTimeUtil.getFormattedNowDatePlusDays(daysToAdd, dateFormatForTest);

        assertEquals(expectedString, returnedString);
    }

    @Test
    public void testSetDate() throws Exception {
        Date expectedDate = Calendar.getInstance().getTime();

        Date returnedDate = DateTimeUtil.getNowDate();

        assertEquals(format(expectedDate), format(returnedDate));
    }

    private String format(Date date) {
        return new SimpleDateFormat(dateFormatForTest).format(date);
    }
}