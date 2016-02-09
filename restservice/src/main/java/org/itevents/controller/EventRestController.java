package org.itevents.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.itevents.controller.converter.FilterConverter;
import org.itevents.controller.wrapper.FilterWrapper;
import org.itevents.dao.model.Event;
import org.itevents.dao.model.User;
import org.itevents.dao.model.VisitLog;
import org.itevents.dao.model.builder.VisitLogBuilder;
import org.itevents.service.EventService;
import org.itevents.service.UserService;
import org.itevents.service.VisitLogService;
import org.itevents.util.time.DateTimeUtil;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.sql.SQLException;
import java.util.List;

@RestController
@Api("Events")
@RequestMapping("/events")
@Validated
public class EventRestController {
    @Inject
    private EventService eventService;
    @Inject
    private UserService userService;
    @Inject
    private VisitLogService visitLogService;
    @Inject
    private FilterConverter filterConverter;

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    @ApiOperation(value = "Returns one event with the given id")
    public Event getEventById(@PathVariable("id") int id) {
        return eventService.getEvent(id);
    }

    @RequestMapping(method = RequestMethod.GET)
    @ApiOperation(value = "Returns events with the given parameters ")
    public List<Event> getFilteredEvents(@ModelAttribute FilterWrapper wrapper) throws SQLException {
        return eventService.getFilteredEvents(filterConverter.toFilter(wrapper));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/{event_id}/assign")
    @ApiOperation(value = "Assigns logged in user to event")
    @ResponseStatus(value = HttpStatus.OK)
    public void assign(@PathVariable("event_id") int eventId) {
        Event event = eventService.getFutureEvent(eventId);
        User user = userService.getAuthorizedUser();
        eventService.assignUserToEvent(user, event);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/{event_id}/unassign")
    @ApiOperation(value = "Unassigns logged in user from event")
    @ResponseStatus(value = HttpStatus.OK)
    public void unassign(
            @PathVariable("event_id") int eventId,
            @NotEmpty
            @Length(max = 250)
            @RequestParam("unassign_reason")
            String unassignReason ) {
        Event event = eventService.getFutureEvent(eventId);
        User user = userService.getAuthorizedUser();
        eventService.unassignUserFromEvent(user, event, DateTimeUtil.getNowDate(),unassignReason);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{event_id}/visitors")
    @ApiOperation(value = "Returns list of visitors of event")
    @ResponseStatus(HttpStatus.OK)
    public List<User> getUsersByEvent(@PathVariable("event_id") int id) {
        Event event = eventService.getEvent(id);
        return userService.getUsersByEvent(event);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{event_id}/register")
    @ApiOperation(value = "Redirects to to given event page")
    @ResponseStatus(HttpStatus.OK)
    public String redirectToEventSite(@PathVariable("event_id") int eventId) {
        Event event = eventService.getEvent(eventId);
        User user = userService.getAuthorizedUser();
        VisitLog visitLog = VisitLogBuilder.aVisitLog()
                .event(event)
                .user(user)
                .date(DateTimeUtil.getNowDate())
                .build();
        visitLogService.addVisitLog(visitLog);
        return event.getRegLink();
    }
}
