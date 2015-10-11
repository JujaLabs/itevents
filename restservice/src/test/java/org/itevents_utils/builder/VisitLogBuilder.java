package org.itevents_utils.builder;

import org.itevents.model.Event;
import org.itevents.model.User;
import org.itevents.model.VisitLog;

import java.util.Date;

/**
 * Created by vaa25 on 03.10.2015.
 */
public class VisitLogBuilder {
    private int id;
    private Event event;
    private Date date;
    private User user;

    private VisitLogBuilder() {
    }

    public static VisitLogBuilder aVisitLog() {
        return new VisitLogBuilder();
    }

    public VisitLogBuilder setId(int id) {
        this.id = id;
        return this;
    }

    public VisitLogBuilder setEvent(Event event) {
        this.event = event;
        return this;
    }

    public VisitLogBuilder setDate(Date date) {
        this.date = date;
        return this;
    }

    public VisitLogBuilder setUser(User user) {
        this.user = user;
        return this;
    }

    public VisitLogBuilder but() {
        return aVisitLog().setId(id).setEvent(event).setDate(date).setUser(user);
    }

    public VisitLog build() {
        VisitLog visitLog = new VisitLog();
        visitLog.setId(id);
        visitLog.setEvent(event);
        visitLog.setDate(date);
        visitLog.setUser(user);
        return visitLog;
    }
}
