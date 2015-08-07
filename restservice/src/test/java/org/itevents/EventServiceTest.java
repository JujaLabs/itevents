package org.itevents;

import org.itevents.model.Event;
import org.itevents.model.Location;
import org.itevents.service.EventService;
import org.junit.*;
import org.junit.runners.MethodSorters;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EventServiceTest {

    private final static int ID_1 = 1;
    private final static int SIZE_7 = 7;
    private final static int SIZE_8 = 8;
    private static EventService eventService;
    private static Event addingEvent;
    private static SimpleDateFormat formatter;

    @BeforeClass
    public static void setup() throws ParseException {
        ApplicationContext context = new ClassPathXmlApplicationContext(
                new String[] {"applicationContext.xml"});
        eventService = context.getBean("eventService", EventService.class);
        formatter = new SimpleDateFormat("dd.MM.yyyy");
        addingEvent = new Event("Ruby", formatter.parse("20.07.2015"), null, "http://www.ruby.com.ua", "Shulyavska",
                new Location(50.454605, 30.445495), "ruby@gmail.com");
    }

    @AfterClass
    public static void teardown() {
        eventService = null;
        addingEvent = null;
        formatter = null;
    }

    @Test
    public void testGetEventById() throws ParseException {
        Event expectedEvent = new Event(1, "Java", formatter.parse("10.07.2015"), null, "http://www.java.com.ua",
                "Beresteyska", new Location(50.458585, 30.742017), "java@gmail.com");
        Event returnedEvent = eventService.getEvent(ID_1);
        Assert.assertEquals(expectedEvent, returnedEvent);
    }

    @Test
    public void testGetAllEvents() throws ParseException {
        int expectedSize = SIZE_8;
        List<Event> returnedEvents = eventService.getAllEvents();
        Assert.assertEquals(expectedSize, returnedEvents.size());
    }

    @Test
    public void testRemoveEvent() throws ParseException {
        Event expectedEvent = addingEvent;
        eventService.addEvent(expectedEvent);
        Event returnedEvent = eventService.removeEvent(expectedEvent);
        expectedEvent.setId(returnedEvent.getId());
        Assert.assertEquals(expectedEvent, returnedEvent);
    }

    @Test
    public void testAddEvent() throws ParseException {
        eventService.addEvent(addingEvent);
        Event returnedEvent = eventService.getEvent(addingEvent.getId());
        Assert.assertEquals(addingEvent, returnedEvent);
        eventService.removeEvent(returnedEvent);
    }
}