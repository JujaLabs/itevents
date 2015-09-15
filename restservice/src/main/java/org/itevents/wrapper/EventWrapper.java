package org.itevents.wrapper;

/**
 * Created by vaa25 on 14.09.2015.
 */
public class EventWrapper {
    private Integer cityId;
    private Boolean free;
    private Double lat;
    private Double lon;
    private Integer radius;
    private String[] techTag;

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Boolean getFree() {
        return free;
    }

    public void setFree(Boolean free) {
        this.free = free;
    }

    public Double getLatitude() {
        return lat;
    }

    public void setLatitude(Double lat) {
        this.lat = lat;
    }

    public Double getLongitude() {
        return lon;
    }

    public void setLongitude(Double lon) {
        this.lon = lon;
    }

    public Integer getRadius() {
        return radius;
    }

    public void setRadius(Integer radius) {
        this.radius = radius;
    }

    public String[] getTechnologiesNames() {
        return techTag;
    }

    public void setTechnologiesNames(String[] techTag) {
        this.techTag = techTag;
    }
}
