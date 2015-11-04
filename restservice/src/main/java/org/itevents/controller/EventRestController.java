package org.itevents.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.itevents.controller.exception.EventNotFoundControllerException;
import org.itevents.model.Event;
import org.itevents.service.EventService;
import org.itevents.service.exception.EventNotFoundServiceException;
import org.itevents.wrapper.FilterWrapper;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.sql.SQLException;
import java.util.List;

@RestController
@Api("Events")
public class EventRestController {

    @Inject
    private EventService eventService;

    @RequestMapping(method = RequestMethod.GET, value = "/events/{id}")
    @ApiOperation(value = "Returns one event with the given id")
    public Event getEventById(@PathVariable("id") int id) {
        try {
            return eventService.getEvent(id);
        } catch (EventNotFoundServiceException e) {
            throw new EventNotFoundControllerException();
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/events")
    @ApiOperation(value = "Returns events with the given parameters ")
    public List<Event> getFilteredEvents(@ModelAttribute FilterWrapper wrapper) throws SQLException {
        return eventService.getFilteredEvents(wrapper);
    }
}
