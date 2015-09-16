package org.itevents.service.converter;

import org.itevents.parameter.FilteredEventsParameter;
import org.itevents.service.CityService;
import org.itevents.service.TechnologyService;
import org.itevents.wrapper.EventWrapper;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

/**
 * Created by vaa25 on 14.09.2015.
 */
@Component
public class EventConverter {

    @Inject
    private TechnologyService technologyService;
    @Inject
    private CityService cityService;

    public FilteredEventsParameter convert(EventWrapper wrapper) {
        FilteredEventsParameter result = new FilteredEventsParameter();
        if (wrapper.getTechnologiesNames() != null) {
            result.setTechnologies(technologyService.getSeveralTechnologiesByName(wrapper.getTechnologiesNames()));
        }
        if (wrapper.getCityId() != null) {
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
}
