package org.itevents;

import org.itevents.controller.FilterEventParams;
import org.itevents.model.Event;
import org.itevents.service.EventService;
import org.itevents.service.EventServiceImpl;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;

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

    @Test
    public void testGetFilteredEvents() {
        List<Event> expectedEvents = new ArrayList<>();
        FilterEventParams params = new FilterEventParams();
        params.setTechTags(new Integer[]{1});
        params.setCityId(1);
        expectedEvents.add(eventService.getEvent(1));
        List<Event> returnedEvents = eventService.getFilteredEvents(params);
        Assert.assertEquals(expectedEvents, returnedEvents);
    }
}
