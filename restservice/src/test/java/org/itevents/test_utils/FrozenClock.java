package org.itevents.test_utils;

import org.itevents.util.time.Clock;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class FrozenClock implements Clock {
    private final Date frozenDateTime;

    public FrozenClock() {
        this.frozenDateTime = new Date();
    }

    @Override
    public Date getNowDateTime() {
        return this.frozenDateTime;
    }
}
