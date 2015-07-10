package org.itevents.controller;

import org.itevents.model.Event;
import org.itevents.service.EventService;
import org.itevents.service.EventServiceImpl;
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

    @RequestMapping(value = "/events/{id}")
    public ResponseEntity<Event> getEvent (@PathVariable("id") Long id) {
        Event event = eventService.getEvent(id);
        if (event == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Event>(event, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/events")
    public List<Event> getEventsAtLocation(@RequestParam(value = "page") int page,
                                                           @RequestParam(value = "itemsPerPage") int itemsPerPage,
                                                           @RequestParam(value = "lat") float latitude,
                                                           @RequestParam(value = "lon") float longitude) {
        List<Event> events = eventService.getAllEventsWithinLocation(latitude, longitude);
        return events;
    }
}
