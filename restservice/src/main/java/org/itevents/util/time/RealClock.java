package org.itevents.util.time;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;

@Component
public final class RealClock implements Clock {
    @Override
    public Date getNowDateTime() {
        return new CustomDateTime().getDate();
    }

    @Override
    public LocalDateTime getNowLocalDateTime() {
        return new CustomDateTime().getLocalDateTime();
    }
}
