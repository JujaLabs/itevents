package org.itevents.util.time;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class FormattedDateTime {
    private Date dateTime;
    private String dateTimeFormat;

    public FormattedDateTime(String dateTimeFormat) {
        this.dateTimeFormat = dateTimeFormat;
    }

    public FormattedDateTime(Date dateTime, String dateTimeFormat) {
        this(dateTimeFormat);
        this.dateTime = dateTime;
    }

    public FormattedDateTime(LocalDateTime localDateTime, String dateTimeFormat) {
        this(
                Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant()),
                dateTimeFormat
        );
    }

    public Date getDateTime() {
        return this.dateTime;
    }


    public String buildFormatedDateTimeString(){
        return new SimpleDateFormat(this.dateTimeFormat).format(dateTime);
    }

    public FormattedDateTime parseFormattedDateTimeFromString(String dateString) throws ParseException {
        Date date = new SimpleDateFormat(this.dateTimeFormat).parse(dateString);
        return  new FormattedDateTime(date, this.dateTimeFormat);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FormattedDateTime that = (FormattedDateTime) o;

        if (dateTime != null ? !dateTime.equals(that.dateTime) : that.dateTime != null) return false;
        return dateTimeFormat != null ? dateTimeFormat.equals(that.dateTimeFormat) : that.dateTimeFormat == null;

    }

    @Override
    public int hashCode() {
        int result = dateTime != null ? dateTime.hashCode() : 0;
        result = 31 * result + (dateTimeFormat != null ? dateTimeFormat.hashCode() : 0);
        return result;
    }
}
