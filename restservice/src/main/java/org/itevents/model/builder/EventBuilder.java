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
    private Boolean free;
    private Integer price;
    private Currency currency;
    private City city;
    private List<Technology> technologies;

    private EventBuilder() {
    }

    public static EventBuilder anEvent() {
        return new EventBuilder();
    }

    public EventBuilder setId(int id) {
        this.id = id;
        return this;
    }

    public EventBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public EventBuilder setEventDate(Date eventDate) {
        this.eventDate = eventDate;
        return this;
    }

    public EventBuilder setCreateDate(Date createDate) {
        this.createDate = createDate;
        return this;
    }

    public EventBuilder setRegLink(String regLink) {
        this.regLink = regLink;
        return this;
    }

    public EventBuilder setAddress(String address) {
        this.address = address;
        return this;
    }

    public EventBuilder setLocation(Location location) {
        this.location = location;
        return this;
    }

    public EventBuilder setContact(String contact) {
        this.contact = contact;
        return this;
    }

    public EventBuilder setFree(Boolean free) {
        this.free = free;
        return this;
    }

    public EventBuilder setPrice(Integer price) {
        this.price = price;
        return this;
    }

    public EventBuilder setCurrency(Currency currency) {
        this.currency = currency;
        return this;
    }

    public EventBuilder setCity(City city) {
        this.city = city;
        return this;
    }

    public EventBuilder setTechnologies(List<Technology> technologies) {
        this.technologies = technologies;
        return this;
    }

    public EventBuilder but() {
        return anEvent().setId(id).setTitle(title).setEventDate(eventDate).setCreateDate(createDate).setRegLink(regLink).setAddress(address).setLocation(location).setContact(contact).setFree(free).setPrice(price).setCurrency(currency).setCity(city).setTechnologies(technologies);
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
        event.setFree(free);
        event.setPrice(price);
        event.setCurrency(currency);
        event.setCity(city);
        event.setTechnologies(technologies);
        return event;
    }
}
