package org.itevents.test_utils.time;

import org.itevents.util.time.Clock;
import org.itevents.util.time.RealClock;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * This class is used in tests. Unlike {@link RealClock} FrozenClock always returns constant dateTime
 */

@Primary
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

    @Override
    public LocalDateTime getNowLocalDateTime() {
        return LocalDateTime.ofInstant(
                this.getNowDateTime().toInstant(), ZoneId.systemDefault()
        );
    }
}
