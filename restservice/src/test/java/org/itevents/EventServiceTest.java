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

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EventServiceTest {

    private static EventService eventService;
    private static List<Event> expectedEvents;
    private static Event addingEvent;

    @BeforeClass
    public static void setup() throws ParseException {
        ApplicationContext context = new ClassPathXmlApplicationContext("testApplicationContext.xml");
        eventService = context.getBean("eventService", EventService.class);
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        expectedEvents = new ArrayList<>(Arrays.asList(
                new Event(1, "Java", formatter.parse("10.07.2015"), null, "www.java.com.ua", "Beresteyska",
                        new Location(50.458585, 30.742017), "java@gmail.com"),
                new Event(2, "PHP", formatter.parse("20.07.2015"), null, "www.php.com.ua", "Shulyavska",
                        new Location(50.454605, 30.445495), "php@gmail.com")));
        addingEvent = new Event(3, "PHP", formatter.parse("20.07.2015"), null, "www.php.com.ua", "Shulyavska",
                new Location(50.454605, 30.445495), "php@gmail.com");
    }

    @AfterClass
    public static void teardown() {
        eventService = null;
        expectedEvents = null;
        addingEvent = null;
    }

    @Test
    public void aTestGetEventById() throws ParseException {
        Event actualEvent = eventService.getEvent(1);
        Assert.assertEquals(expectedEvents.get(0), actualEvent);
    }

    @Test
    public void bTestGetAllEvents() throws ParseException {
        List<Event> actualEvents = eventService.getAllEvents();
        Assert.assertEquals(expectedEvents, actualEvents);
    }

    @Test
    public void cTestRemoveEvent() throws ParseException {
        expectedEvents.remove(1);
        eventService.removeEvent(2);
        List<Event> actualEvents = eventService.getAllEvents();
        Assert.assertEquals(expectedEvents, actualEvents);
    }

    @Test
    public void dTestAddEvent() throws ParseException {
        expectedEvents.add(addingEvent);
        eventService.addEvent(addingEvent);
        List<Event> actualEvents = eventService.getAllEvents();
        Assert.assertEquals(expectedEvents, actualEvents);
    }
}