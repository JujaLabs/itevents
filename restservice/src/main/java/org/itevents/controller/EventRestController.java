package org.itevents.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.itevents.model.Event;
import org.itevents.service.EventService;
import org.itevents.wrapper.FilterWrapper;
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
    @ApiOperation(value = "Returns one event with the given id")
    public ResponseEntity<Event> getEventById(@PathVariable("id") int id) {
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
}
