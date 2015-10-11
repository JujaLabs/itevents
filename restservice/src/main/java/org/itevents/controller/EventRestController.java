package org.itevents.controller;

import io.swagger.annotations.Api;
import org.itevents.model.Event;
import org.itevents.service.EventService;
import org.itevents.wrapper.EventWrapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.Set;

@RestController
@Api("Events")
public class EventRestController {

    @Inject
    private EventService eventService;

    @RequestMapping(method = RequestMethod.GET, value = "/events/{id}")
    public ResponseEntity<Event> getEvent(@PathVariable("id") int id) {
        Event event = eventService.getEvent(id);
        if (event == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(event, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/events")
    public Set<Event> getFilteredEvents(@ModelAttribute EventWrapper wrapper) {
        return eventService.getFilteredEvents(wrapper);
    }
}
