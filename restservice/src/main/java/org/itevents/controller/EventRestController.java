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

    @RequestMapping(method = RequestMethod.POST, value = "/events/{id}/willGo")
    public String iWillGo(@PathVariable("id") int id) {
        try {
            eventService.WillGo(id);
        }catch (Exception e) {
            System.out.println("Something went wrong. May be already subscribed");
        }
        return "successfully subscribed";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/events/{id}/willNotGo")
    public String iWillNotGo(@PathVariable("id") int id) {
        try {
            eventService.WillNotGo(id);
        }catch (Exception e){
        System.out.println("Something went wrong. May be already subscribed");
        }
        return "successfully unsubscribed";
    }
    @RequestMapping(method = RequestMethod.GET, value = "/events/{id}/getVisitors")
    public List getVisitors(@PathVariable("id") int id) {
       return eventService.getAllVisitors(id);
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
