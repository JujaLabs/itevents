package org.itevents.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.itevents.model.Event;
import org.itevents.model.User;
import org.itevents.model.VisitLog;
import org.itevents.model.builder.VisitLogBuilder;
import org.itevents.service.EventService;
import org.itevents.service.UserService;
import org.itevents.service.VisitLogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.net.URL;
import java.util.Date;
import java.util.GregorianCalendar;

@RestController
@Api(value = "visits", description = "Visit log")
public class VisitLogRestController {

    private static final Logger logger = LogManager.getLogger();

    @Inject
    private EventService eventService;
    @Inject
    private VisitLogService visitLogService;
    @Inject
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET, value = "/events/{event_id}/register")
    @ApiOperation(value = "Redirects to to given event page")
    public ResponseEntity<String> redirectToEventSite(@PathVariable("event_id") int eventId) {
        Event event = eventService.getEvent(eventId);
        if (isValid(event)) {
            User user = userService.getAuthorizedUser();
            VisitLog visitLog = VisitLogBuilder.aVisitLog()
                    .event(event)
                    .user(user)
                    .date(getCurrentDate())
                    .build();
            visitLogService.addVisitLog(visitLog);
            return new ResponseEntity(event.getRegLink(), HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    private boolean isValid(Event event) {
        try {
            return event != null && new URL(event.getRegLink()).toURI() != null;
        } catch (Exception e) {
            logger.error("Invalid event URL : " + event.getRegLink(), e);
            return false;
        }
    }

    private Date getCurrentDate() {
        return new GregorianCalendar().getTime();
    }
}
