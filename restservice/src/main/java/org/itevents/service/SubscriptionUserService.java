package org.itevents.service;

import org.itevents.model.Event;
import org.itevents.model.VisitLog;

import java.util.Date;
import java.util.List;

/**
 * Created by ramax on 11/2/15.
 */
public interface SubscriptionUserService {
    public List<VisitLog> getVisitLogsByDate(Date date);
}
