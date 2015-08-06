package org.itevents;

import org.itevents.model.Event;
import org.itevents.service.EventService;
import org.itevents.service.EventServiceImpl;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class EventServiceTest {

    private static EventService eventService;

    @BeforeClass
    public static void setup() {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        eventService = context.getBean("eventService", EventServiceImpl.class);
    }

    @AfterClass
    public static void teardown() {
        eventService = null;
    }

    @Test
    public void testGetEventById() {
        Event event = eventService.getEvent(1);
        Assert.assertNotNull(event);
    }
}
