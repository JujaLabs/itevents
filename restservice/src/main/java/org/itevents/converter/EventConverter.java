package org.itevents.converter;

import org.itevents.parameter.FilteredEventsParameter;
import org.itevents.service.CityService;
import org.itevents.service.TechnologyService;
import org.itevents.wrapper.EventWrapper;

import javax.inject.Inject;

/**
 * Created by vaa25 on 14.09.2015.
 */
public class EventConverter {

    @Inject
    private TechnologyService technologyService;
    @Inject
    private CityService cityService;

//    ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
//    private CityService cityService = context.getBean("cityService", CityService.class);
//    private TechnologyService technologyService =context.getBean("techTagService", TechnologyService.class);

    private EventWrapper wrapper;

    public EventConverter(EventWrapper wrapper) {
        this.wrapper = wrapper;
    }

    public FilteredEventsParameter convert() {
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
