package org.itevents.test_utils.time;

import org.itevents.util.time.*;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * This class is used in tests. Unlike {@link RealClock} FrozenClock always returns constant dateTime
 */

@Primary
@Component
public final class FrozenClock implements Clock {

    private final DateTime frozenDateTime;

    public FrozenClock() {
        this.frozenDateTime = new DateTimeFactory().withoutConstructorArgs();
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
