package org.itevents.controller;

import io.swagger.annotations.Api;
import org.itevents.model.Event;
import org.itevents.service.EventService;
import org.itevents.wrapper.EventWrapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;

@RestController
@Api("Events")
public class EventRestController {

    @Inject
    private EventService eventService;

    @RequestMapping(method = RequestMethod.GET, value = "/events/{id}")
    public ResponseEntity<Event> getEvent(@PathVariable("id") int id) {
        Event event = eventService.getEvent(id);
        return getEventResponseEntity(event);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/events/{id}/willGo")
    public ResponseEntity<Event> iWillGo(@PathVariable("id") int id) {
        Event event = eventService.getEventWillGo(id);
        return getEventResponseEntity(event);
    }
    @RequestMapping(method = RequestMethod.GET, value = "/events/{id}/willNotGo")
    public ResponseEntity<Event> iWillNotGo(@PathVariable("id") int id) {
        Event event = eventService.getEventWillNotGo(id);
        return getEventResponseEntity(event);
    }
    @RequestMapping(method = RequestMethod.GET, value = "/events/{id}/getVisitors")
    public List getVisitors(@PathVariable("id") int id) {
        List visitors = eventService.getAllVisitors(id);
        return visitors;
    }


    private ResponseEntity<Event> getEventResponseEntity(Event event) {
        if (event == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(event, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/events")
    public List<Event> getFilteredEvents(@ModelAttribute EventWrapper wrapper) {
        return eventService.getFilteredEvents(wrapper);
    }
}
