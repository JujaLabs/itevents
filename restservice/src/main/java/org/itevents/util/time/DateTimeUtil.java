package org.itevents.util.time;

import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Component
public class DateTimeUtil {

    private static Date frozenDateTime = null;

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

    private static String dateToString(Date date, String format) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }

    public static Date yyyyMMddStringToDate(String date) throws Exception {
        return new SimpleDateFormat("yyyy.MM.dd").parse(date);
    }

    synchronized public static Date getNowDate(){
        if (frozenDateTime != null) {
            return frozenDateTime;
        } else {
            return Calendar.getInstance().getTime();
        }
    }

    synchronized public static void freezeDateTime(){
        if (frozenDateTime == null) {
            frozenDateTime = Calendar.getInstance().getTime();
        }
    }

    synchronized public static void defreezeDateTime(){
        frozenDateTime = null;
    }
}
