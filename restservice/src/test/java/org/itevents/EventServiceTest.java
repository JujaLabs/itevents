package org.itevents;

import org.itevents.model.Event;
import org.itevents.model.Location;
import org.itevents.service.EventService;
import org.itevents.service.EventServiceImpl;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class EventServiceTest {

    private final static int ID_8 = 8;
    private final static int DAYS_FOR_FUTURE_EVENT = 7;

    private static SimpleDateFormat formatter;
    private static EventService eventService;

    @BeforeClass
    public static void setup() {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        eventService = context.getBean("eventService", EventServiceImpl.class);
        formatter = new SimpleDateFormat("dd.MM.yyyy");
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
    public void testGetFutureEventById() throws ParseException {
        Event returnedEvent = eventService.getFutureEventById(DAYS_FOR_FUTURE_EVENT, ID_8);
        Event expectedEvent = new Event(8, "Java", formatter.parse("10.08.2015"), null, "http://www.java.com.ua",
                "Beresteyska", new Location(50.458585, 30.742017), "java@gmail.com");
        Assert.assertEquals(expectedEvent, returnedEvent);
    }
}
