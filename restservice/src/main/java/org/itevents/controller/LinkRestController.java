package org.itevents.controller;

import org.itevents.model.Event;
import org.itevents.service.EventService;
import org.itevents.service.EventServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LinkRestController {

    ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
    private EventService eventService = context.getBean("eventService", EventServiceImpl.class);

    @RequestMapping(value = "/link/{id}")
    public ResponseEntity<String> getEvent(@PathVariable("id") int id) {
        Event event = eventService.getEvent(id);
        if (event == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(event.getRegLink(), HttpStatus.OK);
    }


}
