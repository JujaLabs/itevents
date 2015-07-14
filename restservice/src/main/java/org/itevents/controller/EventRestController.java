package org.itevents.controller;

import org.itevents.model.Event;
import org.itevents.model.Location;
import org.itevents.service.EventService;
import org.itevents.service.EventServiceImpl;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class EventRestController {

    ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
    private EventService eventService = context.getBean("eventService", EventServiceImpl.class);

    @RequestMapping(value = "/events/{id}")
    public ResponseEntity<Event> getEvent (@PathVariable("id") Long id) {
        Event event = eventService.getEvent(id);
        if (event == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Event>(event, HttpStatus.OK);
    }

    /**
     * REST-method GET that returns list of all events at the location with pagination
     *
     * @param page number of page of events' list
     * @param itemsPerPage number of events placed on the page
     * @param latitude latitude of the area center
     * @param longitude longitude of the area center
     * @param radius radius of the area
     * @return list of events at the location
     */
    @RequestMapping(method = RequestMethod.GET, value = "/events")
    public List<Event> getEventsAtLocation(@RequestParam(value = "page") int page,
                                           @RequestParam(value = "itemsPerPage") int itemsPerPage,
                                           @RequestParam(value = "lat") double latitude,
                                           @RequestParam(value = "lon") double longitude,
                                           @RequestParam(value = "radius") int radius) {
        if (itemsPerPage <= 0) {
            return new ArrayList<>();
        }
        Location location = new Location(latitude, longitude);
        List<Event> events = eventService.getFutureEventsInRadius(location, radius);
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
}
