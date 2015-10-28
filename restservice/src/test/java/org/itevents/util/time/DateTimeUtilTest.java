package org.itevents.util.time;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * Created by roma on 28.10.2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:applicationContextTestAddon.xml"})
public class DateTimeUtilTest {
    private final String expectedFormattedDate = "28.10.2015";
    private final String expectedFormattedDatePlusOneDay = "29.10.2015";
    private final String dateFormatForTest = "dd.MM.yyyy";
    private Date initialDate;

    @Before
    public void setup() throws ParseException {
        this.initialDate = new SimpleDateFormat(dateFormatForTest).parse(expectedFormattedDate);
    }

    @Test
    public void shouldReturnFormattedDate(){
        assertEquals(expectedFormattedDate, DateTimeUtil.dateToString(initialDate, dateFormatForTest));
    }

    @Test
    public void shouldReturnDataWithAdditionalDays() throws ParseException {
        Date expectedDate = new SimpleDateFormat(dateFormatForTest).parse(
                expectedFormattedDatePlusOneDay
        );
        assertEquals(expectedDate, DateTimeUtil.addDaysToDate(initialDate, 1));
    }

}