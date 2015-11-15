package org.itevents.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.itevents.model.Event;
import org.itevents.model.User;
import org.itevents.service.EventService;
import org.itevents.service.UserService;
import org.itevents.wrapper.EventWrapper;
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

    @RequestMapping(method = RequestMethod.GET, value = "/{eventId}")
    public ResponseEntity<Event> getEventById(@PathVariable("eventId") int id) {
        Event event = eventService.getEvent(id);
        if (event == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(event, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/events")
    @ApiOperation(value = "Returns events with the given parameters ")
    public List<Event> getFilteredEvents(@ModelAttribute EventWrapper wrapper) {
        return eventService.getFilteredEvents(wrapper);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/{eventId}/assign")
    @ApiOperation(value = "Signes logged in user to event")
    public ResponseEntity assign(@PathVariable("eventId") int eventId) {
        Event event = eventService.getEvent(eventId);
        if (event == null) return new ResponseEntity(HttpStatus.NOT_FOUND);
        if (new Date().after(event.getEventDate())) return new ResponseEntity(HttpStatus.BAD_REQUEST);
        User user = userService.getUserByName(SecurityContextHolder.getContext().getAuthentication().getName());
        eventService.assign(user, event);
        return new ResponseEntity(HttpStatus.OK);

    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{eventId}/unassign")
    @ApiOperation(value = "unassignes logged in user from event")
    public ResponseEntity unAssign(@PathVariable("eventId") int eventId) {
        Event event = eventService.getEvent(eventId);
        if (event == null) return new ResponseEntity(HttpStatus.NOT_FOUND);
        User user = userService.getUserByName(SecurityContextHolder.getContext().getAuthentication().getName());
        eventService.unassign(user, event);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{eventId}/visitors")
    @ApiOperation(value = "Returns list of visitors of event")
    public ResponseEntity<List<User>> getVisitors(@PathVariable("eventId") int id) {
        Event event = eventService.getEvent(id);
        if (event == null) return new ResponseEntity(HttpStatus.NOT_FOUND);
        List<User> visitors = eventService.getVisitors(event);
        return new ResponseEntity<>(visitors,HttpStatus.OK);
    }
}
