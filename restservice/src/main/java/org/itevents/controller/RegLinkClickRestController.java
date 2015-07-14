package org.itevents.controller;

import org.itevents.model.Event;
import org.itevents.service.EventService;
import org.itevents.service.EventServiceImpl;
import org.itevents.service.RegLinkClickService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class RegLinkClickRestController {

    ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
    private EventService eventService = context.getBean("eventService", EventServiceImpl.class);
    private RegLinkClickService regLinkClickService = context.getBean("regLinkClickService", RegLinkClickService.class);

    @RequestMapping(value = "/reglinkclick/{user_id}/{event_id}")
    public ResponseEntity<String> getEvent(@PathVariable("event_id") int event_id, @PathVariable("user_id") int user_id) {
        Event event = eventService.getEvent(event_id);
        if (event == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        regLinkClickService.addClick(event_id, user_id);
        return new ResponseEntity<String>(event.getRegLink(), HttpStatus.OK);
    }

}
