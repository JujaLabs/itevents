package org.itevents.model;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.Date;

@XmlRootElement(name = "visitLog")
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(propOrder = {"id", "event", "date", "user"})
public class VisitLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private int id;
    @XmlElement
    private Event event;
    @XmlElement
    private Date date;
    @XmlElement
    private User user;

    public VisitLog() {
    }

    public VisitLog(int id, Event event, Date date, User user) {
        this.id = id;
        this.event = event;
        this.date = date;
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
}
