package org.itevents.controller.wrapper;

import java.util.Arrays;

/**
 * Created by vaa25 on 14.09.2015.
 */
public class FilterWrapper {

    private Integer page;
    private Integer itemsPerPage;
    private Integer cityId;
    private Boolean free;
    private Double latitude;
    private Double longitude;
    private Integer radius;
    private String[] technologyTags;
    private Integer rangeInDays;

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
        return latitude;
    }

    public void setLatitude(Double lat) {
        this.latitude = lat;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double lon) {
        this.longitude = lon;
    }

    public Integer getRadius() {
        return radius;
    }

    public void setRadius(Integer radius) {
        this.radius = radius;
    }

    public String[] getTechnologiesNames() {
        return technologyTags;
    }

    public void setTechnologiesNames(String[] technologyTags) {
        this.technologyTags = technologyTags;
    }

    public Integer getRangeInDays() {
        return rangeInDays;
    }

    public void setRangeInDays(Integer rangeInDays) {
        this.rangeInDays = rangeInDays;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FilterWrapper{");
        sb.append("page=").append(page);
        sb.append(", itemsPerPage=").append(itemsPerPage);
        sb.append(", cityId=").append(cityId);
        sb.append(", free=").append(free);
        sb.append(", latitude=").append(latitude);
        sb.append(", longitude=").append(longitude);
        sb.append(", radius=").append(radius);
        sb.append(", technologyTags=").append(Arrays.toString(technologyTags));
        sb.append(", rangeInDays=").append(rangeInDays);
        sb.append('}');
        return sb.toString();
    }
}
