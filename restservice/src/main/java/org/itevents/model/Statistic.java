package org.itevents.model;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.Date;

@XmlRootElement(name = "statistic")
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(propOrder = {"id", "eventId", "date", "userId"})
public class Statistic implements Serializable {

    private static final long serialVersionUID = "Statistic".hashCode();

    @XmlElement
    private int id;
    @XmlElement
    private int eventId;
    @XmlElement
    private Date date;
    @XmlElement
    private int userId;

    public Statistic() {
    }

    public Statistic(int id, int eventId, Date date, int userId) {
        this.id = id;
        this.eventId = eventId;
        this.date = date;
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Statistic{" +
                "id=" + id +
                ", eventId=" + eventId +
                ", date=" + date +
                ", userId=" + userId +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
