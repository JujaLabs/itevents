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
    @XmlElement
    private int price;
    @XmlElement
    private Currency currency;
    @XmlElement
    private City city;

    public Event() {
    }

    public Event(int id, String title, Date eventDate, Date createDate, String regLink, String address,
                 Location location, String contact, int price, Currency currency, City city) {
        this.id = id;
        this.title = title;
        this.eventDate = eventDate;
        this.createDate = createDate;
        this.regLink = regLink;
        this.address = address;
        this.location = location;
        this.contact = contact;
        this.price = price;
        this.currency = currency;
        this.city = city;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Event event = (Event) o;

        if (id != event.id) return false;
        if (price != event.price) return false;
        if (!title.equals(event.title)) return false;
        if (!eventDate.equals(event.eventDate)) return false;
        if (createDate != null ? !createDate.equals(event.createDate) : event.createDate != null) return false;
        if (!regLink.equals(event.regLink)) return false;
        if (address != null ? !address.equals(event.address) : event.address != null) return false;
        if (location != null ? !location.equals(event.location) : event.location != null) return false;
        if (contact != null ? !contact.equals(event.contact) : event.contact != null) return false;
        if (!currency.equals(event.currency)) return false;
        return !(city != null ? !city.equals(event.city) : event.city != null);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + title.hashCode();
        result = 31 * result + eventDate.hashCode();
        result = 31 * result + (createDate != null ? createDate.hashCode() : 0);
        result = 31 * result + regLink.hashCode();
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (location != null ? location.hashCode() : 0);
        result = 31 * result + (contact != null ? contact.hashCode() : 0);
        result = 31 * result + price;
        result = 31 * result + currency.hashCode();
        result = 31 * result + (city != null ? city.hashCode() : 0);
        return result;
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
        sb.append(", price=").append(price);
        sb.append(", currency=").append(currency);
        sb.append(", city=").append(city);
        sb.append('}');
        return sb.toString();
    }
}
