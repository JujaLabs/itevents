package org.itevents.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@XmlRootElement()
@XmlAccessorType(XmlAccessType.NONE)
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
    private Boolean free;
    @XmlElement
    private Integer price;
    @XmlElement
    private Currency currency;
    @XmlElement
    private City city;
    private List<User> visitors;
    @XmlElement
    private List<Technology> technologies;

    public Event() {
    }

    public Event(int id, String title, Date eventDate, Date createDate, String regLink, String address,
                 Location location, String contact, Boolean free, Integer price, Currency currency, City city,
                 List<Technology> technologies) {
        this.id = id;
        this.title = title;
        this.eventDate = eventDate;
        this.createDate = createDate;
        this.regLink = regLink;
        this.address = address;
        this.location = location;
        this.contact = contact;
        this.free = free;
        this.price = price;
        this.currency = currency;
        this.city = city;
        this.technologies = technologies;
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

    public Boolean isFree() {
        return free;
    }

    public void setFree(Boolean free) {
        this.free = free;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
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

    public List<User> getAllVisitors() {
        return visitors;
    }

    public void setVisitors(List<User> visitors) {
        this.visitors = visitors;
    }

    public List<Technology> getTechnologies() {
        return technologies;
    }

    public void setTechnologies(List<Technology> technologies) {
        this.technologies = technologies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Event event = (Event) o;

        if (title != null ? !title.equals(event.title) : event.title != null) return false;
        if (eventDate != null ? !eventDate.equals(event.eventDate) : event.eventDate != null) return false;
        if (createDate != null ? !createDate.equals(event.createDate) : event.createDate != null) return false;
        if (regLink != null ? !regLink.equals(event.regLink) : event.regLink != null) return false;
        if (address != null ? !address.equals(event.address) : event.address != null) return false;
        if (location != null ? !location.equals(event.location) : event.location != null) return false;
        if (contact != null ? !contact.equals(event.contact) : event.contact != null) return false;
        if (free != null ? !free.equals(event.free) : event.free != null) return false;
        if (price != null ? !price.equals(event.price) : event.price != null) return false;
        if (currency != null ? !currency.equals(event.currency) : event.currency != null) return false;
        if (city != null ? !city.equals(event.city) : event.city != null) return false;
        return !(technologies != null ? !technologies.equals(event.technologies) : event.technologies != null);

    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (eventDate != null ? eventDate.hashCode() : 0);
        result = 31 * result + (createDate != null ? createDate.hashCode() : 0);
        result = 31 * result + (regLink != null ? regLink.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (location != null ? location.hashCode() : 0);
        result = 31 * result + (contact != null ? contact.hashCode() : 0);
        result = 31 * result + (free != null ? free.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (currency != null ? currency.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (technologies != null ? technologies.hashCode() : 0);
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
        sb.append(", free=").append(free);
        sb.append(", price=").append(price);
        sb.append(", currency=").append(currency);
        sb.append(", city=").append(city);
        sb.append(", technologies=").append(technologies);
        sb.append('}');
        return sb.toString();
    }
}
