package org.itevents.util.time;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Date;

public interface DateTime {
    LocalDateTime getLocalDateTime();
    Date getDate();
    DateTime withLocalDateTime(LocalDateTime localDateTime);
    DateTime withDate(Date date);
    DateTime withFormat(String format);
    DateTime parseFromString(String dateString) throws ParseException;
}
