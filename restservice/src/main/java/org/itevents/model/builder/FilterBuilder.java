package org.itevents.model.builder;

import org.itevents.model.City;
import org.itevents.model.Filter;
import org.itevents.model.Technology;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by vaa25 on 27.10.2015.
 */
public class FilterBuilder {
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

    private FilterBuilder() {
    }

    public static FilterBuilder aFilter() {
        return new FilterBuilder();
    }

    public FilterBuilder id(Integer id) {
        this.id = id;
        return this;
    }

    public FilterBuilder offset(Integer offset) {
        this.offset = offset;
        return this;
    }

    public FilterBuilder limit(Integer limit) {
        this.limit = limit;
        return this;
    }

    public FilterBuilder city(City city) {
        this.city = city;
        return this;
    }

    public FilterBuilder free(Boolean free) {
        this.free = free;
        return this;
    }

    public FilterBuilder longitude(Double longitude) {
        this.longitude = longitude;
        return this;
    }

    public FilterBuilder latitude(Double latitude) {
        this.latitude = latitude;
        return this;
    }

    public FilterBuilder radius(Integer radius) {
        this.radius = radius;
        return this;
    }

    public FilterBuilder createDate(Date createDate) {
        this.createDate = createDate;
        return this;
    }

    public FilterBuilder technologies(List<Technology> technologies) {
        this.technologies = technologies;
        return this;
    }

    public FilterBuilder technology(Technology technology) {
        if (technologies == null) {
            technologies = new ArrayList<>();
        }
        this.technologies.add(technology);
        return this;
    }

    public FilterBuilder but() {
        return aFilter().id(id).offset(offset).limit(limit).city(city).free(free).longitude(longitude).latitude(latitude).radius(radius).createDate(createDate).technologies(technologies);
    }

    public Filter build() {
        Filter filter = new Filter();
        filter.setId(id);
        filter.setOffset(offset);
        filter.setLimit(limit);
        filter.setCity(city);
        filter.setFree(free);
        filter.setLongitude(longitude);
        filter.setLatitude(latitude);
        filter.setRadius(radius);
        filter.setCreateDate(createDate);
        filter.setTechnologies(technologies);
        return filter;
    }
}
