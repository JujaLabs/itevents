package org.itevents.controller;

import org.itevents.service.EventService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Антон on 04.08.2015.
 */
@RestController
public class RatingController {

    ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
    private EventService eventService = context.getBean("eventService", EventService.class);
}
