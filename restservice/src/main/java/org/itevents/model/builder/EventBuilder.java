package org.itevents.model.builder;

import org.itevents.model.*;

import java.util.Date;
import java.util.List;

/**
 * Created by vaa25 on 30.09.2015.
 */
public class EventBuilder {
    private int id;
    private String title;
    private Date eventDate;
    private Date createDate;
    private String regLink;
    private String address;
    private Location location;
    private String contact;
    private Integer price;
    private Currency currency;
    private City city;
    private List<Technology> technologies;

    private EventBuilder() {
    }

    public static EventBuilder anEvent() {
        return new EventBuilder();
    }

    public EventBuilder id(int id) {
        this.id = id;
        return this;
    }

    public EventBuilder title(String title) {
        this.title = title;
        return this;
    }

    public EventBuilder eventDate(Date eventDate) {
        this.eventDate = eventDate;
        return this;
    }

    public EventBuilder createDate(Date createDate) {
        this.createDate = createDate;
        return this;
    }

    public EventBuilder regLink(String regLink) {
        this.regLink = regLink;
        return this;
    }

    public EventBuilder address(String address) {
        this.address = address;
        return this;
    }

    public EventBuilder location(Location location) {
        this.location = location;
        return this;
    }

    public EventBuilder contact(String contact) {
        this.contact = contact;
        return this;
    }

    public EventBuilder price(Integer price) {
        this.price = price;
        return this;
    }

    public EventBuilder currency(Currency currency) {
        this.currency = currency;
        return this;
    }

    public EventBuilder city(City city) {
        this.city = city;
        return this;
    }

    public EventBuilder technologies(List<Technology> technologies) {
        this.technologies = technologies;
        return this;
    }

    public EventBuilder but() {
        return anEvent().id(id).title(title).eventDate(eventDate).createDate(createDate).regLink(regLink)
                .address(address).location(location).contact(contact).price(price).currency(currency)
                .city(city).technologies(technologies);
    }

    public Event build() {
        Event event = new Event();
        event.setId(id);
        event.setTitle(title);
        event.setEventDate(eventDate);
        event.setCreateDate(createDate);
        event.setRegLink(regLink);
        event.setAddress(address);
        event.setLocation(location);
        event.setContact(contact);
        event.setPrice(price);
        event.setCurrency(currency);
        event.setCity(city);
        event.setTechnologies(technologies);
        return event;
    }
}
