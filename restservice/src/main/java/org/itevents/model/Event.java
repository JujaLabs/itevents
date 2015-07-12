package org.itevents.model;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.Date;

@XmlRootElement(name = "event")
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(propOrder={"id", "title", "eventDate", "createDate", "regLink", "address", "location", "contact"})
public class Event implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private long id;
    @XmlElement
    private String title;
    @XmlElement
    private Date eventDate;
    @XmlElement
    private Date createDate;
    @XmlElement
    private String regLink;
    @XmlElement
    private String address;
    @XmlElement
    private Location location;
    @XmlElement
    private String contact;

    public Event() {
    }

    public Event(long id, String title, Date eventDate, Date createDate, String regLink, String address, Location location,  String contact) {
        this.id = id;
        this.title = title;
        this.eventDate = eventDate;
        this.createDate = createDate;
        this.regLink = regLink;
        this.address = address;
        this.location = location;
        this.contact = contact;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = title;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getRegLink() {
        return regLink;
    }

    public void setRegLink(String regLink) {
        this.regLink = regLink;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Event ");
        sb.append("id=").append(id);
        sb.append(", title='").append(title).append('\'');
        sb.append(", eventDate=").append(eventDate);
        sb.append(", createDate=").append(createDate);
        sb.append(", regLink=").append(regLink);
        sb.append(", address=").append(address);
        sb.append(", location=").append(location);
        sb.append(", contact=").append(contact);
        return sb.toString();
    }
}
