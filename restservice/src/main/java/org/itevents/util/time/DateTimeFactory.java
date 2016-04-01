package org.itevents.util.time;

import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
public class DateTimeFactory {

    public DateTimeFactory() {
    }

    public DateTime withoutConstructorArgs() {
        return new CustomDateTime();
    }

    public DateTime withLocalDateTime(LocalDateTime localDateTime) {
        return new CustomDateTime(localDateTime);
    }

    public DateTime withLocalDateTimeAndFormat(LocalDateTime localDateTime, String format) {
        return new CustomDateTime(localDateTime, format);
    }

    public DateTime parseFromStringWithFormat(String dateString, String format) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        Date date = formatter.parse(dateString);

        LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());

        return new CustomDateTime(localDateTime, format);
    }
}
