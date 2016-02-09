package org.itevents.dao.model.builder;

import org.itevents.dao.model.Event;
import org.itevents.dao.model.User;
import org.itevents.dao.model.VisitLog;

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

    public VisitLogBuilder id(int id) {
        this.id = id;
        return this;
    }

    public VisitLogBuilder event(Event event) {
        this.event = event;
        return this;
    }

    public VisitLogBuilder date(Date date) {
        this.date = date;
        return this;
    }

    public VisitLogBuilder user(User user) {
        this.user = user;
        return this;
    }

    public VisitLogBuilder but() {
        return aVisitLog().id(id).event(event).date(date).user(user);
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
