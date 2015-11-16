package org.itevents.model.builder;

import org.itevents.model.City;
import org.itevents.model.Filter;
import org.itevents.model.Technology;

import java.util.List;

public class FilterBuilder {
    private int id;
    private Integer offset;
    private Integer limit;
    private City city;
    private Boolean free;
    private Double longitude;
    private Double latitude;
    private Integer radius;
    private List<Technology> technologies;
    private Integer rangeInDays;

    private FilterBuilder() {
    }

    public static FilterBuilder aFilter() {
        return new FilterBuilder();
    }

    public FilterBuilder id(int id) {
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

    public FilterBuilder technologies(List<Technology> technologies) {
        this.technologies = technologies;
        return this;
    }

    public FilterBuilder rangeInDays(Integer rangeInDays) {
        this.rangeInDays = rangeInDays;
        return this;
    }

    public FilterBuilder but() {
        return aFilter().id(id).offset(offset).limit(limit).city(city).free(free).longitude(longitude).latitude(latitude).radius(radius).technologies(technologies).rangeInDays(rangeInDays);
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
        filter.setTechnologies(technologies);
        filter.setRangeInDays(rangeInDays);
        return filter;
    }
}
