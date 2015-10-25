package org.itevents.model;

import java.util.Date;
import java.util.List;

/**
 * Created by vaa25 on 28.07.2015.
 */
public class Filter {

    private Integer id;
    private Integer offset;
    private Integer limit;
    private City city;
    private Boolean free;
    private Double longitude;
    private Double latitude;
    private Integer radius;
    private Date createDate;
    private List<Technology> technologies;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public List<Technology> getTechnologies() {
        return technologies;
    }

    public void setTechnologies(List<Technology> technologies) {
        this.technologies = technologies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Filter filter = (Filter) o;

        if (offset != null ? !offset.equals(filter.offset) : filter.offset != null) return false;
        if (limit != null ? !limit.equals(filter.limit) : filter.limit != null) return false;
        if (city != null ? !city.equals(filter.city) : filter.city != null) return false;
        if (free != null ? !free.equals(filter.free) : filter.free != null) return false;
        if (longitude != null ? !longitude.equals(filter.longitude) : filter.longitude != null) return false;
        if (latitude != null ? !latitude.equals(filter.latitude) : filter.latitude != null) return false;
        if (radius != null ? !radius.equals(filter.radius) : filter.radius != null) return false;
        if (createDate != null ? !createDate.equals(filter.createDate) : filter.createDate != null) return false;
        return !(technologies != null ? !technologies.equals(filter.technologies) : filter.technologies != null);

    }

    @Override
    public int hashCode() {
        int result = offset != null ? offset.hashCode() : 0;
        result = 31 * result + (limit != null ? limit.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (free != null ? free.hashCode() : 0);
        result = 31 * result + (longitude != null ? longitude.hashCode() : 0);
        result = 31 * result + (latitude != null ? latitude.hashCode() : 0);
        result = 31 * result + (radius != null ? radius.hashCode() : 0);
        result = 31 * result + (createDate != null ? createDate.hashCode() : 0);
        result = 31 * result + (technologies != null ? technologies.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Filter{");
        sb.append("id=").append(id);
        sb.append(", offset=").append(offset);
        sb.append(", limit=").append(limit);
        sb.append(", city=").append(city);
        sb.append(", free=").append(free);
        sb.append(", longitude=").append(longitude);
        sb.append(", latitude=").append(latitude);
        sb.append(", radius=").append(radius);
        sb.append(", createDate=").append(createDate);
        sb.append(", technologies=").append(technologies);
        sb.append('}');
        return sb.toString();
    }
}
