package org.itevents.dao.model;

import java.util.Date;

public class VisitLog {

    private int id;
    private Event event;
    private Date date;
    private User user;

    public VisitLog() {
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

        if (event != null ? !event.equals(visitLog.event) : visitLog.event != null) return false;
        if (date != null ? !date.equals(visitLog.date) : visitLog.date != null) return false;
        return !(user != null ? !user.equals(visitLog.user) : visitLog.user != null);

    }

    @Override
    public int hashCode() {
        int result = event != null ? event.hashCode() : 0;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("VisitLog{");
        sb.append("id=").append(id);
        sb.append(", event=").append(event);
        sb.append(", date=").append(date);
        sb.append(", user=").append(user);
        sb.append('}');
        return sb.toString();
    }
}
