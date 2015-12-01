package org.itevents.util.time;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by roma on 18.10.15.
 */
public class TimeUtil {

    public static Date getNowDate() {
        return Calendar.getInstance().getTime();
    }
}