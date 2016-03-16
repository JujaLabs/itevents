package org.itevents.util.time;

import java.time.LocalDateTime;
import java.util.Date;

public interface Clock {
    Date getNowDateTime();
    LocalDateTime getNowLocalDateTime();
}
