package org.itevents.controller;

import org.itevents.model.Event;
import org.itevents.parameter.FilteredEventsParameter;
import org.itevents.service.*;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EventRestController {

    ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
    private EventService eventService = context.getBean("eventService", EventServiceImpl.class);
    private CityService cityService = context.getBean("cityService", CityServiceImpl.class);
    private TechnologyService technologyService =context.getBean("techTagService", TechnologyServiceImpl.class);

    @RequestMapping(value = "/events/{id}")
    public ResponseEntity<Event> getEvent(@PathVariable("id") int id) {
        Event event = eventService.getEvent(id);
        if (event == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Event>(event, HttpStatus.OK);
    }


    private List<Event> getPaginatedEvents(int page, int itemsPerPage, List<Event> events) {

        int pages = events.size()/itemsPerPage;
        if (events.size() % itemsPerPage != 0) {
            pages++;
        }
        PagedListHolder<Event> paginatedEvents = new PagedListHolder<Event>(events);
        paginatedEvents.setPageSize(itemsPerPage);
        if (page <= 0) {
            return paginatedEvents.getPageList();
        }
        if (page > pages - 1) {
            paginatedEvents.setPage(pages - 1);
            return paginatedEvents.getPageList();
        }
        paginatedEvents.setPage(page);
        return paginatedEvents.getPageList();
    }

    //    radius=10&cityId=23&lat=50.434&lon=30.543&free=true&techTag=java&techTag=javascript
    @RequestMapping(method = RequestMethod.GET, value = "/events")
    public List<Event> getFilteredEvents(@RequestParam(required = false, value = "page") Integer page,
                                         @RequestParam(required = false, value = "itemPerPage") Integer itemPerPage,
                                         @RequestParam(required = false, value = "cityId") Integer cityId,
                                         @RequestParam(required = false, value = "free") Boolean free,
                                         @RequestParam(required = false, value = "lat") Double latitude,
                                         @RequestParam(required = false, value = "lon") Double longitude,
                                         @RequestParam(required = false, value = "radius") Integer radius,
                                         @RequestParam(required = false, value = "techTag") String[] technologiesNames) {

        FilteredEventsParameter params = new FilteredEventsParameter();

        if (radius == null || latitude == null || longitude == null) {
            radius = null;
            longitude = null;
            latitude = null;
        }

        if (cityId != null) {
            params.setCity(cityService.getCity(cityId));
        }
        if (technologiesNames != null) {
            params.setTechnologies(technologyService.getSeveralTechnologiesByName(technologiesNames));
        }
        params.setFree(free);
        params.setLatitude(latitude);
        params.setLongitude(longitude);
        params.setRadius(radius);
        itemPerPage = getItemPerPage(itemPerPage);
        List<Event> filteredEvents = eventService.getFilteredEvents(params);
        if (page == null) {
            page = 0;
        }
        return getPaginatedEvents(page, itemPerPage, filteredEvents);
    }

    private int getItemPerPage(Integer itemPerPage) {
        if (itemPerPage == null) {
            itemPerPage = 10;
        }
        return itemPerPage;
    }
}
