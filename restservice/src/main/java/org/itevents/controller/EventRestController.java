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
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;

@RestController
@Api("Event")
public class EventRestController {

    @Inject
    private EventService eventService;

    @Inject
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET, value = "/event/{eventID}")
    public ResponseEntity<Event> getEventById(@PathVariable("eventID") int id) {
        Event event = eventService.getEvent(id);
        if (event == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(event, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/event")
    @ApiOperation(value = "Returns events with the given parameters ")
    public List<Event> getFilteredEvents(@ModelAttribute EventWrapper wrapper) {
        return eventService.getFilteredEvents(wrapper);
    }
    @RequestMapping(method = RequestMethod.POST, value = "/event/{eventID}/willGo/{userID}")
    public ResponseEntity<Event> iWillGo(@PathVariable("eventID") int eventID, @PathVariable("userID") int userID) {
        Event event = eventService.getEvent(eventID);
        User user = userService.getUser(userID);
        eventService.WillGo(event, user);
        return new ResponseEntity<>(event, HttpStatus.CREATED);

    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/event/{eventID}/willNotGo/{userID}")
    public ResponseEntity<Event> iWillNotGo(@PathVariable("eventID") int eventID, @PathVariable("userID") int userID) {
        Event event = eventService.getEvent(eventID);
        User user = userService.getUser(userID);
        eventService.WillNotGo(event, user);
        return new ResponseEntity<>(event, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/event/{eventID}/getVisitors")
    public ResponseEntity<List<User>> getVisitors(@PathVariable("eventID") int id) {
        Event event = eventService.getEvent(id);
        List<User> visitors = eventService.getVisitors(event);
        return new ResponseEntity<>(visitors,HttpStatus.OK);
    }
}
