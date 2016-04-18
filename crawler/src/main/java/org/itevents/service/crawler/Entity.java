package org.itevents.service.crawler;

/**
 * Created by vaa25 on 06.04.2016.
 */
@SuppressWarnings("PMD.TooManyMethods")
public class Entity {
    private String title;
    private String date;
    private String time;
    private String address;
    private String registrationLink;
    private String city;
    private String description;
    private String price;

    public String getTitle() {
        return this.title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(final String date) {
        this.date = date;
    }

    public String getTime() {
        return this.time;
    }

    public void setTime(final String time) {
        this.time = time;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(final String address) {
        this.address = address;
    }

    public String getRegistrationLink() {
        return this.registrationLink;
    }

    public void setRegistrationLink(final String registrationLink) {
        this.registrationLink = registrationLink;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(final String city) {
        this.city = city;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getPrice() {
        return this.price;
    }

    public void setPrice(final String price) {
        this.price = price;
    }
}
