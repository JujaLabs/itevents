package org.itevents.controller;

import io.swagger.annotations.Api;
import org.itevents.model.Event;
import org.itevents.model.User;
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

    @RequestMapping(method = RequestMethod.POST, value = "/events/{id}/willGo")
    public ResponseEntity<Event> iWillGo(@PathVariable("id") int id) {
//        Event event = eventService.getEvent(id);
        eventService.WillGo(id);
//        return new ResponseEntity<>(event, HttpStatus.OK);
        return null;
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/events/{id}/willNotGo")
    public ResponseEntity<Event> iWillNotGo(@PathVariable("id") int id) {
//        Event event = eventService.getEvent(id);
            eventService.WillNotGo(id);
        return null;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/events/{id}/getVisitors")
    public List<User> getVisitors(@PathVariable("id") int id) {
       return eventService.getVisitors(id);
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
