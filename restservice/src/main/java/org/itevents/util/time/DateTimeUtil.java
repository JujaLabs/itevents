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

    public String getFormattedNowDatePlusDays(int days, String dateFormat) {
        Date nowDate = getNowDate();
        Date futureDate = addDaysToDate(nowDate, days);
        return dateToString(futureDate, dateFormat);
    }

    public Date addDaysToDate(Date date, int daysCount) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, daysCount);
        return calendar.getTime();
    }

    public String dateToString(Date date, String format) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }

    public Date setDate(String date) throws Exception{
        return new SimpleDateFormat("yyyy.MM.dd").parse(date);
    }

    public Date getNowDate(){
        return Calendar.getInstance().getTime();
    }
}
