package org.itevents.service;

import org.itevents.model.Event;
import org.itevents.model.Location;
import org.itevents.model.Technology;
import org.itevents.parameter.FilteredEventsParameter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.text.ParseException;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext.xml"})
@Transactional
public class EventServiceTest {

    private final int YEAR_2015 = 2015;
    private final int MONTH_07 = 7;
    private final int DAY_10 = 10;
    private final int DAY_20 = 20;
    private final int SIZE_7 = 7;
    private final int ID_1 = 1;
    private final int ID_2 = 2;
    private final int ID_3 = 3;
    private final int ID_4 = 4;
    private final int ID_6 = 6;
    private final int ID_7 = 7;
    private Event addingEvent;
    @Inject
    private EventService eventService;
    @Inject
    private TechnologyService technologyService;
    @Inject
    private CityService cityService;

    @Before
    public void setup(){
        Calendar calendar = new GregorianCalendar(YEAR_2015, MONTH_07, DAY_20);
        Date date = calendar.getTime();
        addingEvent = new Event("Ruby", date, null, "http://www.ruby.com.ua", "Shulyavska",
                new Location(50.454605, 30.445495), "ruby@gmail.com");
    }

    @Test
    public void testGetEventById() throws ParseException {
        Calendar calendar = new GregorianCalendar(YEAR_2015, MONTH_07, DAY_10);
        Date date = calendar.getTime();
        Event expectedEvent = new Event(1, "Java", date, null, "http://www.java.com.ua",
                "Beresteyska", new Location(50.458585, 30.742017), "java@gmail.com");
        Event returnedEvent = eventService.getEvent(ID_1);
        Assert.assertEquals(expectedEvent, returnedEvent);
    }

    @Test
    public void testGetAllEvents() throws ParseException {
        int expectedSize = SIZE_7;
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

    @Test
    public void testGetFilteredEventsKyivJava() {
        int javaId = 1;
        int kyivId = 1;
        List<Technology> testTechnologies = new ArrayList<>();
        testTechnologies.add(technologyService.getTechnology(javaId));
        List<Event> expectedEvents = new ArrayList<>();
        FilteredEventsParameter params = new FilteredEventsParameter();
        params.setTechnologies(testTechnologies);
        params.setCity(cityService.getCity(kyivId));
        expectedEvents.add(eventService.getEvent(ID_1));
        List<Event> returnedEvents = eventService.getFilteredEvents(params);
        assertEquals(expectedEvents, returnedEvents);
    }

    @Test
    public void testGetFilteredEventsBoyarkaPayed() {
        int boyarkaId = 3;
        List<Event> expectedEvents = new ArrayList<>();
        FilteredEventsParameter params = new FilteredEventsParameter();
        params.setCity(cityService.getCity(boyarkaId));
        params.setFree(false);
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
        expectedEvents.add(eventService.getEvent(ID_4));
        expectedEvents.add(eventService.getEvent(ID_3));
        expectedEvents.add(eventService.getEvent(ID_7));
        List<Technology> testTechnologies = new ArrayList<>();
        testTechnologies.add(technologyService.getTechnology(phpId));
        testTechnologies.add(technologyService.getTechnology(antId));
        testTechnologies.add(technologyService.getTechnology(sqlId));
        FilteredEventsParameter params = new FilteredEventsParameter();
        params.setTechnologies(testTechnologies);
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
