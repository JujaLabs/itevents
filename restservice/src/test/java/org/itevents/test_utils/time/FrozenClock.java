package org.itevents.test_utils.time;

import org.itevents.util.time.Clock;
import org.itevents.util.time.CustomDateTime;
import org.itevents.util.time.RealClock;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * This class is used in tests. Unlike {@link RealClock} FrozenClock always returns constant dateTime
 */

@Primary
@Component
public class FrozenClock implements Clock {
    private final CustomDateTime frozenDateTime;

    public FrozenClock() {
        this.frozenDateTime = new CustomDateTime();
    }

    @Override
    public Date getNowDateTime() {
        return this.frozenDateTime.getDate();
    }

    @Override
    public LocalDateTime getNowLocalDateTime() {
        return this.frozenDateTime.getLocalDateTime();
    }
}
