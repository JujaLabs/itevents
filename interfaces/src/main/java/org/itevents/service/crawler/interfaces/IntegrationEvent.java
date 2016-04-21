package org.itevents.service.crawler.interfaces;

/**
 * Created by vaa25 on 21.04.2016.
 */
public interface IntegrationEvent {
    String getUrl();

    void setUrl(String url);

    String getTitle();

    void setTitle(String title);

    String getDate();

    void setDate(String date);

    String getTime();

    void setTime(String time);

    String getAddress();

    void setAddress(String address);

    String getRegistrationLink();

    void setRegistrationLink(String registrationLink);

    String getCity();

    void setCity(String city);

    String getDescription();

    void setDescription(String description);

    String getPrice();

    void setPrice(String price);
}
