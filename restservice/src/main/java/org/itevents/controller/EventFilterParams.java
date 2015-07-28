package org.itevents.controller;

/**
 * Created by vaa25 on 28.07.2015.
 */
public class EventFilterParams {

    private Integer page;
    private Integer cityId;
    private Boolean payed;
    private Double latitude;
    private Double longitude;
    private Integer radius;
    private String[] techTags;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Boolean getPayed() {
        return payed;
    }

    public void setPayed(Boolean payed) {
        this.payed = payed;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Integer getRadius() {
        return radius;
    }

    public void setRadius(Integer radius) {
        this.radius = radius;
    }

    public String[] getTechTags() {
        return techTags;
    }

    public void setTechTags(String[] techTags) {
        this.techTags = techTags;
    }
}
