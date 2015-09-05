package org.itevents.controller;

import io.swagger.annotations.Api;
import org.itevents.model.Event;
import org.itevents.model.User;
import org.itevents.model.VisitLog;
import org.itevents.service.EventService;
import org.itevents.service.UserService;
import org.itevents.service.VisitLogService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.net.URL;


@RestController
@Api(value = "visits", description = "Visit log")
public class VisitLogRestController {

    @Inject
    private EventService eventService;
    @Inject
    private VisitLogService visitLogService;
    @Inject
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET, value = "/events/{event_id}/register")
    public ResponseEntity redirectToEventSite(@PathVariable("event_id") int eventId) {
        Event event = eventService.getEvent(eventId);
        HttpHeaders headers = new HttpHeaders();
        try {
            headers.setLocation(new URL(event.getRegLink()).toURI());
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        User user = getUserFromSecurityContext();
        visitLogService.addVisitLog(new VisitLog(event, user));
        return new ResponseEntity(headers, HttpStatus.FOUND);
    }

    private User getUserFromSecurityContext() {
        return userService.getUserByName(SecurityContextHolder.getContext().getAuthentication().getName());
    }
}
