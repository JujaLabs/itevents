package org.itevents.model;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.sql.Date;

@XmlRootElement(name = "statistic")
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(propOrder = {"id", "event_id", "date", "user_id"})
public class Statistic implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private int id;
    @XmlElement
    private int event_id;
    @XmlElement
    private Date date;
    @XmlElement
    private int user_id;

    public Statistic() {
    }

    public Statistic(int id, int event_id, Date date, int user_id) {
        this.id = id;
        this.event_id = event_id;
        this.date = date;
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return "Statistic{" +
                "id=" + id +
                ", event_id=" + event_id +
                ", date=" + date +
                ", user_id=" + user_id +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEvent_id() {
        return event_id;
    }

    public void setEvent_id(int event_id) {
        this.event_id = event_id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
