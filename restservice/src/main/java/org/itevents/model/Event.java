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
    private int id;
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

    public Event(int id, String title, Date eventDate, Date createDate, String regLink, String address, Location location, String contact) {
        this.id = id;
        this.title = title;
        this.eventDate = eventDate;
        this.createDate = createDate;
        this.regLink = regLink;
        this.address = address;
        this.location = location;
        this.contact = contact;
    }

    public Event( String title, Date eventDate, Date createDate, String regLink, String address, Location location, String contact) {
        this.title = title;
        this.eventDate = eventDate;
        this.createDate = createDate;
        this.regLink = regLink;
        this.address = address;
        this.location = location;
        this.contact = contact;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
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
        final StringBuilder sb = new StringBuilder("Event{");
        sb.append("id=").append(id);
        sb.append(", title='").append(title).append('\'');
        sb.append(", eventDate=").append(eventDate);
        sb.append(", createDate=").append(createDate);
        sb.append(", regLink='").append(regLink).append('\'');
        sb.append(", address='").append(address).append('\'');
        sb.append(", location=").append(location);
        sb.append(", contact='").append(contact).append('\'');
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Event event = (Event) o;

        if (id != event.id) return false;
        if (address != null ? !address.equals(event.address) : event.address != null) return false;
        if (contact != null ? !contact.equals(event.contact) : event.contact != null) return false;
        if (createDate != null ? !createDate.equals(event.createDate) : event.createDate != null) return false;
        if (!eventDate.equals(event.eventDate)) return false;
        if (location != null ? !location.equals(event.location) : event.location != null) return false;
        if (regLink != null ? !regLink.equals(event.regLink) : event.regLink != null) return false;
        return title.equals(event.title);

    }

    @Override
    public int hashCode() {
        int result = id ^ (id >>> 32);
        result = 31 * result + title.hashCode();
        result = 31 * result + eventDate.hashCode();
        result = 31 * result + (createDate != null ? createDate.hashCode() : 0);
        result = 31 * result + (regLink != null ? regLink.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (location != null ? location.hashCode() : 0);
        result = 31 * result + (contact != null ? contact.hashCode() : 0);
        return result;
    }

}
