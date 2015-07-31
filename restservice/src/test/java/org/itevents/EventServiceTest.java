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
    public void testGetFilteredEventsKyivJava() {
        List<Event> expectedEvents = new ArrayList<>();
        FilterEventParams params = new FilterEventParams();
        params.setTechTags(new Integer[]{1});
        params.setCityId(1);
        expectedEvents.add(eventService.getEvent(1));
        List<Event> returnedEvents = eventService.getFilteredEvents(params);
        Assert.assertEquals(expectedEvents, returnedEvents);
    }

    @Test
    public void testGetFilteredEventsBoyarkaPayed() {
        List<Event> expectedEvents = new ArrayList<>();
        FilterEventParams params = new FilterEventParams();
        params.setCityId(3);
        params.setPayed(true);
        expectedEvents.add(eventService.getEvent(6));
        List<Event> returnedEvents = eventService.getFilteredEvents(params);
        Assert.assertEquals(expectedEvents, returnedEvents);
    }

    @Test
    public void testGetFilteredEventsPhpAntSql() {
        List<Event> expectedEvents = new ArrayList<>();
        FilterEventParams params = new FilterEventParams();
        params.setTechTags(new Integer[]{3, 7, 10});
        expectedEvents.add(eventService.getEvent(4));
        expectedEvents.add(eventService.getEvent(3));
        expectedEvents.add(eventService.getEvent(7));
        List<Event> returnedEvents = eventService.getFilteredEvents(params);
        Assert.assertEquals(expectedEvents, returnedEvents);
    }

    @Test
    public void testGetFilteredEventsInRadius() {
        List<Event> expectedEvents = new ArrayList<>();
        FilterEventParams params = new FilterEventParams();
        params.setLatitude(50.454605);
        params.setLongitude(30.403965);
        params.setRadius(5000);
        expectedEvents.add(eventService.getEvent(2));
        expectedEvents.add(eventService.getEvent(3));
        List<Event> returnedEvents = eventService.getFilteredEvents(params);
        Assert.assertEquals(expectedEvents, returnedEvents);
    }
}
