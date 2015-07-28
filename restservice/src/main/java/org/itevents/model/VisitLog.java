package org.itevents.model;

import java.util.Date;

public class VisitLog {

    private int id;
    private Event event;
    private Date date;
    private User user;

    public VisitLog() {
    }

    public VisitLog(Event event, User user) {
        this.event = event;
        this.user = user;
    }

    @Override
    public String toString() {
        return "VisitLog{" +
                "id=" + id +
                ", event=" + event +
                ", date=" + date +
                ", user=" + user +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VisitLog visitLog = (VisitLog) o;

        if (id != visitLog.id) return false;
        if (!event.equals(visitLog.event)) return false;
        if (date != null ? !date.equals(visitLog.date) : visitLog.date != null) return false;
        return user.equals(visitLog.user);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + event.hashCode();
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + user.hashCode();
        return result;
    }
}
