package org.itevents.wrapper;

import java.util.Arrays;

/**
 * Created by vaa25 on 14.09.2015.
 */
public class FilterWrapper {

    private Integer page;
    private Integer itemsPerPage;
    private Integer cityId;
    private Boolean free;
    private Double lat;
    private Double lon;
    private Integer radius;
    private String[] techTag;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getItemsPerPage() {
        return itemsPerPage;
    }

    public void setItemsPerPage(Integer itemsPerPage) {
        this.itemsPerPage = itemsPerPage;
    }

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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FilterWrapper{");
        sb.append("page=").append(page);
        sb.append(", itemsPerPage=").append(itemsPerPage);
        sb.append(", cityId=").append(cityId);
        sb.append(", free=").append(free);
        sb.append(", lat=").append(lat);
        sb.append(", lon=").append(lon);
        sb.append(", radius=").append(radius);
        sb.append(", techTag=").append(Arrays.toString(techTag));
        sb.append('}');
        return sb.toString();
    }
}
