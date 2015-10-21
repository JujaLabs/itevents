package org.itevents.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
    public ResponseEntity<Event> getEventById(@PathVariable("id") int id) {
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
    @RequestMapping(method = RequestMethod.POST, value = "/events/{id}/willGo/{userID}")
    public ResponseEntity<Event> iWillGo(@PathVariable("id") int id, @PathVariable("userID") int userID) {
        Event event = eventService.getEvent(id);
        eventService.WillGo(id, userID);
        return new ResponseEntity<>(event, HttpStatus.CREATED);

    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/events/{id}/willNotGo/{userID}")
    public ResponseEntity<Event> iWillNotGo(@PathVariable("id") int id, @PathVariable("userID") int userID) {
        Event event = eventService.getEvent(id);
        eventService.WillNotGo(id, userID);
        return new ResponseEntity<>(event, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/events/{id}/getVisitors")
    public ResponseEntity<List<User>> getVisitors(@PathVariable("id") int id) {
        List<User> visitors = eventService.getVisitors(id);
        return new ResponseEntity<>(visitors,HttpStatus.OK);
    }
}
