package org.itevents.util.time;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class RealClock implements Clock {
    @Override
    public Date getNowDateTime() {
        return new Date();
    }
}
