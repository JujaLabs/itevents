package org.itevents.util.time;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public final class CustomDateTime {
    private static final String DEFAULT_FORMAT = "dd.MM.yyyy";
    private final LocalDateTime localDateTime;
    private final String format;

    public CustomDateTime(){
        this.localDateTime = LocalDateTime.now();
        this.format = DEFAULT_FORMAT;
    }

    public CustomDateTime(final LocalDateTime localDateTime, final String format){
        this.localDateTime = localDateTime;
        this.format = format;
    }

    public LocalDateTime getLocalDateTime() {
        return this.localDateTime;
    }

    public Date getDate() {
        return Date.from(this.localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public CustomDateTime withLocalDateTime(LocalDateTime localDateTime) {
        return new CustomDateTime(localDateTime, this.format);
    }

    public CustomDateTime withDate(Date date) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        return withLocalDateTime(localDateTime);
    }

    public CustomDateTime withFormat(String format) {
        return new CustomDateTime(this.localDateTime, format);
    }

    public CustomDateTime parseFromString(String dateString) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(this.format);
        return new CustomDateTime().withDate(formatter.parse(dateString))
                                   .withFormat(this.format);
    }

    @Override
    public String toString(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(this.format);
        return this.localDateTime.format(formatter);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CustomDateTime dateTime = (CustomDateTime) o;

        if (localDateTime != null ? !localDateTime.equals(dateTime.localDateTime) : dateTime.localDateTime != null)
            return false;
        return format != null ? format.equals(dateTime.format) : dateTime.format == null;

    }

    @Override
    public int hashCode() {
        int result = localDateTime != null ? localDateTime.hashCode() : 0;
        result = 31 * result + (format != null ? format.hashCode() : 0);
        return result;
    }
}
