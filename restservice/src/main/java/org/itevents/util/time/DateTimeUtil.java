package org.itevents.util.time;

import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by roma on 18.10.15.
 */
@Component
public class DateTimeUtil {

    public static String getFormattedNowDatePlusDays(int days, String dateFormat) {
        Date nowDate = getNowDate();
        Date futureDate = addDaysToDate(nowDate, days);
        return dateToString(futureDate, dateFormat);
    }

    public static Date addDaysToDate(Date date, int daysCount) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, daysCount);
        return calendar.getTime();
    }

    public static String dateToString(Date date, String format) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }

    public static Date setDate(String date) throws Exception{
        return new SimpleDateFormat("yyyy.MM.dd").parse(date);
    }

    public static Date getNowDate(){
        return Calendar.getInstance().getTime();
    }
}
