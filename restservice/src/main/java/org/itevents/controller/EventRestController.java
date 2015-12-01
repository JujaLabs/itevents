package org.itevents.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.itevents.model.Event;
import org.itevents.model.User;
import org.itevents.service.EventService;
import org.itevents.service.UserService;
import org.itevents.wrapper.FilterWrapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

@RestController
@Api("Events")
@RequestMapping("/events")
public class EventRestController {
    @Inject
    private EventService eventService;
    @Inject
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET, value = "/{event_id}")
    public ResponseEntity<Event> getEventById(@PathVariable("event_id") int id) {
        Event event = eventService.getEvent(id);
        if (event == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(event, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET)
    @ApiOperation(value = "Returns events with the given parameters ")
    public List<Event> getFilteredEvents(@ModelAttribute FilterWrapper wrapper) {
        return eventService.getFilteredEvents(wrapper);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/{event_id}/assign")
    @ApiOperation(value = "Assigns logged in user to event")
    public ResponseEntity assign(@PathVariable("event_id") int eventId) {
        Event event = eventService.getEvent(eventId);
        if (event == null || new Date().after(event.getEventDate()) ) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } else {
            User user = getUserFromSecurityContext();
            eventService.assign(user, event);
            return new ResponseEntity(HttpStatus.OK);
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{event_id}/unassign")
    @ApiOperation(value = "Unassigns logged in user from event")
    public ResponseEntity unassign(@PathVariable("event_id") int eventId) {
        Event event = eventService.getEvent(eventId);
        if (event == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } else {
            User user = getUserFromSecurityContext();
            eventService.unassign(user, event);
            return new ResponseEntity(HttpStatus.OK);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{event_id}/visitors")
    @ApiOperation(value = "Returns list of visitors of event")
    public ResponseEntity<List<User>> getUsersByEvent(@PathVariable("event_id") int id) {
        Event event = eventService.getEvent(id);
        if (event == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } else {
            List<User> visitors = userService.getUsersByEvent(event);
            return new ResponseEntity<>(visitors, HttpStatus.OK);
        }
    }

    private User getUserFromSecurityContext() {
        return userService.getUserByName(SecurityContextHolder.getContext().getAuthentication().getName());
    }
}
