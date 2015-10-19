package org.itevents.service.converter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.itevents.model.Filter;
import org.itevents.service.CityService;
import org.itevents.service.TechnologyService;
import org.itevents.wrapper.FilterWrapper;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

/**
 * Created by vaa25 on 14.09.2015.
 */
@Component
public class FilterConverter {

    private static final Logger logger = LogManager.getLogger();

    private final int DEFAULT_ITEMS_PER_PAGE = 10;
    @Inject
    private TechnologyService technologyService;
    @Inject
    private CityService cityService;

    public Filter toFilter(FilterWrapper wrapper) {
        Filter result = new Filter();
        int itemsPerPage;
        if (wrapper.getItemsPerPage() != null && wrapper.getItemsPerPage() > 0) {
            itemsPerPage = wrapper.getItemsPerPage();
        } else {
            itemsPerPage = getDefaultItemsPerPage();
        }
        result.setLimit(itemsPerPage);
        if (wrapper.getPage() == null || wrapper.getPage() <= 1) {
            result.setOffset(0);
        } else {
            result.setOffset((wrapper.getPage() - 1) * itemsPerPage);
        }
        if (wrapper.getTechnologiesNames() != null) {
            result.setTechnologies(technologyService.getTechnologiesByNames(wrapper.getTechnologiesNames()));
        }
        if (wrapper.getCityId() != null) {
            logger.info("CityService: " + cityService);
            logger.info("wrapper.getCityId(): " + wrapper.getCityId());
            result.setCity(cityService.getCity(wrapper.getCityId()));
        }
        if (wrapper.getFree() != null) {
            result.setFree(wrapper.getFree());
        }
        if (wrapper.getLatitude() != null && wrapper.getLongitude() != null && wrapper.getRadius() != null) {
            result.setLongitude(wrapper.getLongitude());
            result.setLatitude(wrapper.getLatitude());
            result.setRadius(wrapper.getRadius());
        }
        return result;
    }

    public int getDefaultItemsPerPage() {
        return DEFAULT_ITEMS_PER_PAGE;
    }
}
