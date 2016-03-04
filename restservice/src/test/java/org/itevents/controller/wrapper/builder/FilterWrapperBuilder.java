package org.itevents.controller.wrapper.builder;

import org.itevents.controller.wrapper.FilterWrapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FilterWrapperBuilder {
    private Integer page;
    private Integer itemsPerPage;
    private Integer cityId;
    private Boolean free;
    private Double latitude;
    private Double longitude;
    private Integer radius;
    private String[] technologyTags = {};
    private Integer rangeInDays;

    private FilterWrapperBuilder() {
    }

    public static FilterWrapperBuilder aFilterWrapper() {
        return new FilterWrapperBuilder();
    }

    public FilterWrapperBuilder page(Integer page) {
        this.page = page;
        return this;
    }

    public FilterWrapperBuilder itemsPerPage(Integer itemsPerPage) {
        this.itemsPerPage = itemsPerPage;
        return this;
    }

    public FilterWrapperBuilder cityId(Integer cityId) {
        this.cityId = cityId;
        return this;
    }

    public FilterWrapperBuilder free(Boolean free) {
        this.free = free;
        return this;
    }

    public FilterWrapperBuilder latitude(Double latitude) {
        this.latitude = latitude;
        return this;
    }

    public FilterWrapperBuilder longitude(Double longitude) {
        this.longitude = longitude;
        return this;
    }

    public FilterWrapperBuilder radius(Integer radius) {
        this.radius = radius;
        return this;
    }

    public FilterWrapperBuilder technologyTags(String[] technologyTags) {
        this.technologyTags = technologyTags;
        return this;
    }

    public FilterWrapperBuilder technologyTag(String technologyTag) {
        List<String> technologyTagsList = new ArrayList<>();
        technologyTagsList.addAll(Arrays.asList(this.technologyTags));
        technologyTagsList.add(technologyTag);
        this.technologyTags = new String[technologyTagsList.size()];
        technologyTagsList.toArray(this.technologyTags);
        return this;
    }

    public FilterWrapperBuilder rangeInDays(Integer rangeInDays) {
        this.rangeInDays = rangeInDays;
        return this;
    }

    public FilterWrapperBuilder but() {
        return aFilterWrapper()
                .page(page)
                .itemsPerPage(itemsPerPage)
                .cityId(cityId)
                .free(free)
                .latitude(latitude)
                .longitude(longitude)
                .radius(radius)
                .technologyTags(technologyTags)
                .rangeInDays(rangeInDays);
    }

    public FilterWrapper build() {
        FilterWrapper filterWrapper = new FilterWrapper();
        filterWrapper.setPage(page);
        filterWrapper.setItemsPerPage(itemsPerPage);
        filterWrapper.setCityId(cityId);
        filterWrapper.setFree(free);
        filterWrapper.setLatitude(latitude);
        filterWrapper.setLongitude(longitude);
        filterWrapper.setRadius(radius);
        filterWrapper.setTechnologyTags(technologyTags);
        filterWrapper.setRangeInDays(rangeInDays);
        return filterWrapper;
    }
}