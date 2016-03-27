package org.itevents.util.time;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;

@Component
public class RealClock implements Clock {
    @Override
    public Date getNowDateTime() {
        return new CustomDateTime().getDate();
    }

    // we use this method to get instance of LocalDateTime class that has convenient methods for date/time manipulation
    @Override
    public LocalDateTime getNowLocalDateTime() {
        return new CustomDateTime().getLocalDateTime();
    }
}
