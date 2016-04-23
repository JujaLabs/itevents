package org.itevents.service.crawler.integration;

import org.itevents.service.crawler.interfaces.IntegrationEventData;

/**
 * Created by vaa25 on 06.04.2016.
 */
@SuppressWarnings("PMD.TooManyMethods")
public class SampleIntegrationEventData implements IntegrationEventData {
    private String title;
    private String date;
    private String time;
    private String address;
    private String registrationLink;
    private String city;
    private String description;
    private String price;
    private String url;

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public void setUrl(final String url) {
        this.url = url;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public void setTitle(final String title) {
        this.title = title;
    }

    @Override
    public String getDate() {
        return this.date;
    }

    @Override
    public void setDate(final String date) {
        this.date = date;
    }

    @Override
    public String getTime() {
        return this.time;
    }

    @Override
    public void setTime(final String time) {
        this.time = time;
    }

    @Override
    public String getAddress() {
        return this.address;
    }

    @Override
    public void setAddress(final String address) {
        this.address = address;
    }

    @Override
    public String getRegistrationLink() {
        return this.registrationLink;
    }

    @Override
    public void setRegistrationLink(final String registrationLink) {
        this.registrationLink = registrationLink;
    }

    @Override
    public String getCity() {
        return this.city;
    }

    @Override
    public void setCity(final String city) {
        this.city = city;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public void setDescription(final String description) {
        this.description = description;
    }

    @Override
    public String getPrice() {
        return this.price;
    }

    @Override
    public void setPrice(final String price) {
        this.price = price;
    }
}
