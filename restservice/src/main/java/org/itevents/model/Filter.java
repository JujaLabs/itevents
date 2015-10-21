package org.itevents.model;

import java.util.Date;
import java.util.List;

/**
 * Created by vaa25 on 28.07.2015.
 */
public class Filter {

    private int id;
    private Integer offset;
    private Integer limit;
    private City city;
    private Boolean free;
    private Double longitude;
    private Double latitude;
    private Integer radius;
    private List<Technology> technologies;
    private Date maximumDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Boolean getFree() {
        return free;
    }

    public void setFree(Boolean payed) {
        this.free = payed;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Integer getRadius() {
        return radius;
    }

    public void setRadius(Integer radius) {
        this.radius = radius;
    }

    public List<Technology> getTechnologies() {
        return technologies;
    }

    public void setTechnologies(List<Technology> technologies) {
        this.technologies = technologies;
    }

    public Date getMaximumDate() {
        return maximumDate;
    }

    public void setMaximumDate(Date maximumDate) {
        this.maximumDate = maximumDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Filter filter = (Filter) o;

        if (city != null ? !city.equals(filter.city) : filter.city != null) return false;
        if (free != null ? !free.equals(filter.free) : filter.free != null) return false;
        if (longitude != null ? !longitude.equals(filter.longitude) : filter.longitude != null) return false;
        if (latitude != null ? !latitude.equals(filter.latitude) : filter.latitude != null) return false;
        if (radius != null ? !radius.equals(filter.radius) : filter.radius != null) return false;
        return !(technologies != null ? !technologies.equals(filter.technologies) : filter.technologies != null);

    }

    @Override
    public int hashCode() {
        int result = city != null ? city.hashCode() : 0;
        result = 31 * result + (free != null ? free.hashCode() : 0);
        result = 31 * result + (longitude != null ? longitude.hashCode() : 0);
        result = 31 * result + (latitude != null ? latitude.hashCode() : 0);
        result = 31 * result + (radius != null ? radius.hashCode() : 0);
        result = 31 * result + (technologies != null ? technologies.hashCode() : 0);
        return result;
    }
}