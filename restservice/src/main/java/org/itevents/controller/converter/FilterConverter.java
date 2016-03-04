package org.itevents.controller.converter;

import org.itevents.controller.wrapper.FilterWrapper;
import org.itevents.dao.model.Filter;
import org.itevents.service.CityService;
import org.itevents.service.TechnologyService;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

/**
 * Created by vaa25 on 14.09.2015.
 */
@Component
public class FilterConverter {

    public static final int MINUMUM_RANGE_IN_DAYS_OF_FILTER = 0;
    private final int DEFAULT_ITEMS_PER_PAGE = 10;
    @Inject
    private TechnologyService technologyService;
    @Inject
    private CityService cityService;

    public Filter toFilter(FilterWrapper wrapper) {

        Filter filter = new Filter();
        int itemsPerPage;

        if (wrapper.getItemsPerPage() != null && wrapper.getItemsPerPage() > 0) {
            itemsPerPage = wrapper.getItemsPerPage();
        } else {
            itemsPerPage = DEFAULT_ITEMS_PER_PAGE;
        }
        filter.setLimit(itemsPerPage);

        if (wrapper.getPage() == null || wrapper.getPage() <= 1) {
            filter.setOffset(0);
        } else {
            filter.setOffset((wrapper.getPage() - 1) * itemsPerPage);
        }

        if (wrapper.getTechnologyTags() != null) {
            filter.setTechnologies(technologyService.getTechnologiesByNames(wrapper.getTechnologyTags()));
        }

        if (wrapper.getCityId() != null) {
            filter.setCity(cityService.getCity(wrapper.getCityId()));
        }

        if (wrapper.getFree() != null) {
            filter.setFree(wrapper.getFree());
        }

        if (wrapper.getLatitude() != null && wrapper.getLongitude() != null && wrapper.getRadius() != null) {
            filter.setLongitude(wrapper.getLongitude());
            filter.setLatitude(wrapper.getLatitude());
            filter.setRadius(wrapper.getRadius());
        }

        Integer rangeInDays = wrapper.getRangeInDays();
        if (rangeInDays != null && rangeInDays > MINUMUM_RANGE_IN_DAYS_OF_FILTER) {
            filter.setRangeInDays(rangeInDays);
        }

        return filter;
    }

}
