package org.itevents.model;

import javax.xml.bind.annotation.*;
import java.io.Serializable;

@XmlRootElement(name = "statistic")
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(propOrder = {"id", "event_id", "count"})
public class Statistic implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private long id;
    @XmlElement
    private long event_id;
    @XmlElement
    private int count;

    public Statistic() {
    }

    public Statistic(long id, long event_id, int count) {
        this.id = id;
        this.event_id = event_id;
        this.count = count;
    }

    @Override
    public String toString() {
        return "Statistic{" +
                "id=" + id +
                ", event_id=" + event_id +
                ", count=" + count +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getEvent_id() {
        return event_id;
    }

    public void setEvent_id(long event_id) {
        this.event_id = event_id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
