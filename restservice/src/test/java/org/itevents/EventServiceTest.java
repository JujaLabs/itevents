package org.itevents;

import org.itevents.model.Event;
import org.itevents.parameter.FilteredEventsParameter;
import org.itevents.service.EventService;
import org.itevents.service.EventServiceImpl;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class EventServiceTest {

    private final static int ID_1 = 1;
    private final static int ID_2 = 2;
    private final static int ID_3 = 3;
    private final static int ID_4 = 4;
    private final static int ID_6 = 6;
    private final static int ID_7 = 7;
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

    //todo этот тест из числа общего crud на совести команды ветки №6
    @Test
    public void testGetEventById() {
        Event returnedEvent = eventService.getEvent(ID_1);
        assertNotNull(returnedEvent);
    }

    @Test
    public void testGetFilteredEventsKyivJava() {
        int javaId = 1;
        int kyivId = 1;
        List<Event> expectedEvents = new ArrayList<>();
        FilteredEventsParameter params = new FilteredEventsParameter();
        params.setTechTags(new Integer[]{javaId});
        params.setCityId(kyivId);
        expectedEvents.add(eventService.getEvent(ID_1));
        List<Event> returnedEvents = eventService.getFilteredEvents(params);
        assertEquals(expectedEvents, returnedEvents);
    }

    @Test
    public void testGetFilteredEventsBoyarkaPayed() {
        int boyarkaId = 3;
        List<Event> expectedEvents = new ArrayList<>();
        FilteredEventsParameter params = new FilteredEventsParameter();
        params.setCityId(boyarkaId);
        params.setPayed(true);
        expectedEvents.add(eventService.getEvent(ID_6));
        List<Event> returnedEvents = eventService.getFilteredEvents(params);
        assertEquals(expectedEvents, returnedEvents);
    }

    @Test
    public void testGetFilteredEventsPhpAntSql() {
        int phpId = 3;
        int antId = 7;
        int sqlId = 10;
        List<Event> expectedEvents = new ArrayList<>();
        FilteredEventsParameter params = new FilteredEventsParameter();
        params.setTechTags(new Integer[]{phpId, antId, sqlId});
        expectedEvents.add(eventService.getEvent(ID_4));
        expectedEvents.add(eventService.getEvent(ID_3));
        expectedEvents.add(eventService.getEvent(ID_7));
        List<Event> returnedEvents = eventService.getFilteredEvents(params);
        assertEquals(expectedEvents, returnedEvents);
    }

    @Test
    public void testGetFilteredEventsInRadius() {
        double testLatitude = 50.454605;
        double testLongitude = 30.403965;
        int testRadius = 5000;
        List<Event> expectedEvents = new ArrayList<>();
        expectedEvents.add(eventService.getEvent(ID_2));
        expectedEvents.add(eventService.getEvent(ID_3));
        FilteredEventsParameter params = new FilteredEventsParameter();
        params.setLatitude(testLatitude);
        params.setLongitude(testLongitude);
        params.setRadius(testRadius);
        List<Event> returnedEvents = eventService.getFilteredEvents(params);
        assertEquals(expectedEvents, returnedEvents);
    }
}
