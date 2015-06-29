package org.itevents.model;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.Date;

@XmlRootElement(name = "event")
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(propOrder={"id", "name", "location", "date"})
public class Event implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private long id;
    @XmlElement
    private String name;
    @XmlElement
    private Location location;
    @XmlElement
    private Date date;

    public Event() {
    }

    public Event(long id, String name, Location location, Date date) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.date = date;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Event ");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", location=").append(location);
        sb.append(", date=").append(date);
        return sb.toString();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
