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
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.sql.SQLException;
import java.util.List;

@RestController
@Api("Events")
@RequestMapping("/events")
public class EventRestController {

    @Inject
    private EventService eventService;
    @Inject
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    @ApiOperation(value = "Returns one event with the given id")
    public Event getEventById(@PathVariable("id") int id) {
        return eventService.getEvent(id);
    }

    @RequestMapping(method = RequestMethod.GET)
    @ApiOperation(value = "Returns events with the given parameters ")
    public List<Event> getFilteredEvents(@ModelAttribute FilterWrapper wrapper) throws SQLException {
        return eventService.getFilteredEvents(wrapper);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/{event_id}/assign")
    @ApiOperation(value = "Assigns logged in user to event")
    @ResponseStatus(value = HttpStatus.OK)
    public void assign(@PathVariable("event_id") int eventId) {
        Event event = eventService.getFutureEvent(eventId);
        User user = userService.getAuthorizedUser();
        eventService.assign(user, event);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{event_id}/unassign")
    @ApiOperation(value = "Unassigns logged in user from event")
    @ResponseStatus(value = HttpStatus.OK)
    public void unassign(@PathVariable("event_id") int eventId) {
        Event event = eventService.getFutureEvent(eventId);
        User user = userService.getAuthorizedUser();
        eventService.unassign(user, event);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{event_id}/visitors")
    @ApiOperation(value = "Returns list of visitors of event")
    public ResponseEntity<List<User>> getUsersByEvent(@PathVariable("event_id") int id) {
        Event event = eventService.getEvent(id);
        List<User> visitors = userService.getUsersByEvent(event);
        return new ResponseEntity<>(visitors, HttpStatus.OK);
    }
}
