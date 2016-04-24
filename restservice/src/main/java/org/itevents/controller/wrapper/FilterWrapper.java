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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FilterWrapper that = (FilterWrapper) o;

        if (page != null ? !page.equals(that.page) : that.page != null) return false;
        if (itemsPerPage != null ? !itemsPerPage.equals(that.itemsPerPage) : that.itemsPerPage != null) return false;
        if (cityId != null ? !cityId.equals(that.cityId) : that.cityId != null) return false;
        if (free != null ? !free.equals(that.free) : that.free != null) return false;
        if (lat != null ? !lat.equals(that.lat) : that.lat != null) return false;
        if (lon != null ? !lon.equals(that.lon) : that.lon != null) return false;
        if (radius != null ? !radius.equals(that.radius) : that.radius != null) return false;
        return Arrays.equals(techTag, that.techTag);

    }

    @Override
    public int hashCode() {
        int result = page != null ? page.hashCode() : 0;
        result = 31 * result + (itemsPerPage != null ? itemsPerPage.hashCode() : 0);
        result = 31 * result + (cityId != null ? cityId.hashCode() : 0);
        result = 31 * result + (free != null ? free.hashCode() : 0);
        result = 31 * result + (lat != null ? lat.hashCode() : 0);
        result = 31 * result + (lon != null ? lon.hashCode() : 0);
        result = 31 * result + (radius != null ? radius.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(techTag);
        return result;
    }
}
