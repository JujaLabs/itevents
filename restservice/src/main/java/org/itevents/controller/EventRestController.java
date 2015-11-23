package org.itevents.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.itevents.model.Event;
import org.itevents.model.User;
import org.itevents.service.EventService;
import org.itevents.wrapper.FilterWrapper;
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
public class EventRestController {

    @Inject
    private EventService eventService;

    @Inject
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET, value = "/events/{eventID}")
    public ResponseEntity<Event> getEventById(@PathVariable("eventID") int id) {
        Event event = eventService.getEvent(id);
        if (event == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(event, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/events")
    @ApiOperation(value = "Returns events with the given parameters ")
    public List<Event> getFilteredEvents(@ModelAttribute FilterWrapper wrapper) {
        return eventService.getFilteredEvents(wrapper);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/events/{eventID}/willGo")
    @ApiOperation(value = "Subscribes logged in user on event")
    public ResponseEntity<Event> iWillGo(@PathVariable("eventID") int eventID) {
        Event event = eventService.getEvent(eventID);
        if (event == null) return new ResponseEntity(HttpStatus.NOT_FOUND);
        if (new Date().after(event.getEventDate())) return new ResponseEntity(HttpStatus.BAD_REQUEST);
        User user = userService.getUserByName(SecurityContextHolder.getContext().getAuthentication().getName());
        eventService.willGoToEvent(user, event);
        return new ResponseEntity<>(event, HttpStatus.CREATED);

    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/events/{eventID}/willNotGo")
    @ApiOperation(value = "Unsubscribes logged in user from event")
    public ResponseEntity<Event> iWillNotGo(@PathVariable("eventID") int eventID) {
        Event event = eventService.getEvent(eventID);
        if (event == null) return new ResponseEntity(HttpStatus.NOT_FOUND);
        User user = userService.getUserByName(SecurityContextHolder.getContext().getAuthentication().getName());
        eventService.willNotGoToEvent(user, event);
        return new ResponseEntity<>(event, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/events/{eventID}/getVisitors")
    @ApiOperation(value = "Returns list of visitors of event")
    public ResponseEntity<List<User>> getVisitors(@PathVariable("eventID") int id) {
        Event event = eventService.getEvent(id);
        if (event == null) return new ResponseEntity(HttpStatus.NOT_FOUND);
        List<User> visitors = eventService.getVisitors(event);
        return new ResponseEntity<>(visitors,HttpStatus.OK);
    }
}
