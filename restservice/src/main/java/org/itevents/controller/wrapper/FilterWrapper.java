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

    public String[] getTechnologyTags() {
        return technologyTags;
    }

    public void setTechnologyTags(String[] technologyTags) {
        this.technologyTags = technologyTags;
    }

    public Integer getRangeInDays() {
        return rangeInDays;
    }

    public void setRangeInDays(Integer rangeInDays) {
        this.rangeInDays = rangeInDays;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FilterWrapper that = (FilterWrapper) o;

        if (page != null ? !page.equals(that.page) : that.page != null) return false;
        if (itemsPerPage != null ? !itemsPerPage.equals(that.itemsPerPage) : that.itemsPerPage != null) return false;
        if (cityId != null ? !cityId.equals(that.cityId) : that.cityId != null) return false;
        if (free != null ? !free.equals(that.free) : that.free != null) return false;
        if (latitude != null ? !latitude.equals(that.latitude) : that.latitude != null) return false;
        if (longitude != null ? !longitude.equals(that.longitude) : that.longitude != null) return false;
        if (radius != null ? !radius.equals(that.radius) : that.radius != null) return false;
        if (!Arrays.equals(technologyTags, that.technologyTags)) return false;
        return rangeInDays != null ? rangeInDays.equals(that.rangeInDays) : that.rangeInDays == null;

    }

    @Override
    public int hashCode() {
        int result = page != null ? page.hashCode() : 0;
        result = 31 * result + (itemsPerPage != null ? itemsPerPage.hashCode() : 0);
        result = 31 * result + (cityId != null ? cityId.hashCode() : 0);
        result = 31 * result + (free != null ? free.hashCode() : 0);
        result = 31 * result + (latitude != null ? latitude.hashCode() : 0);
        result = 31 * result + (longitude != null ? longitude.hashCode() : 0);
        result = 31 * result + (radius != null ? radius.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(technologyTags);
        result = 31 * result + (rangeInDays != null ? rangeInDays.hashCode() : 0);
        return result;
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
