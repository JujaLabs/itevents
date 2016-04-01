package org.itevents.util.time;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

final class CustomDateTime implements DateTime {
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

    public CustomDateTime(final LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
        this.format = DEFAULT_FORMAT;
    }

    @Override
    public LocalDateTime getLocalDateTime() {
        return this.localDateTime;
    }

    @Override
    public Date getDate() {
        return Date.from(this.localDateTime.atZone(ZoneId.systemDefault()).toInstant());
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
