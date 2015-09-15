package org.itevents.service;

import org.itevents.model.Event;
import org.itevents.model.Location;
import org.itevents.model.Technology;
import org.itevents.wrapper.EventWrapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext.xml"})
@Transactional
public class EventServiceTest {
    private Event addingEvent;
    @Inject
    private EventService eventService;
    @Inject
    private TechnologyService technologyService;
    @Inject
    private CityService cityService;

    private SimpleDateFormat dateFormatter = new SimpleDateFormat("dd.MM.yyyy");

    @Before
    public void setup() throws ParseException {
        List<Technology> technologies = new ArrayList<>();
        addingEvent = new Event("Ruby", dateFormatter.parse("20.07.2015"), null, "http://www.ruby.com.ua", "Shulyavska",
                new Location(50.454605, 30.445495), "ruby@gmail.com");
        addingEvent.setTechnologies(technologies);
    }

    @Test
    public void testGetEventById() throws ParseException {
        List<Technology> technologies = new ArrayList<>();
        technologies.add(technologyService.getTechnology(1));
        technologies.add(technologyService.getTechnology(4));
        technologies.add(technologyService.getTechnology(8));
        technologies.add(technologyService.getTechnology(9));
        Event expectedEvent = new Event(1, "Java", dateFormatter.parse("10.07.2015"), null, "http://www.java.com.ua", "Beresteyska",
                new Location(50.458585, 30.742017), "java@gmail.com", true, null, null, cityService.getCity(1), technologies);
        Event returnedEvent = eventService.getEvent(1);
        Assert.assertEquals(expectedEvent, returnedEvent);
    }

    @Test
    public void testGetAllEvents() throws ParseException {
        int expectedSize = 7;
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

        EventWrapper wrapper = new EventWrapper();
        String[] testTechnologies = new String[]{technologyService.getTechnology(javaId).getName()};
        wrapper.setTechnologiesNames(testTechnologies);
        wrapper.setCityId(kyivId);

        List<Event> expectedEvents = new ArrayList<>();
        expectedEvents.add(eventService.getEvent(1));

//        FilteredEventsParameter eventsParameter = new FilteredEventsParameter();
//        eventsParameter.setTechnologies(testTechnologies);
//        eventsParameter.setCity(cityService.getCity(kyivId));

        List<Event> returnedEvents = eventService.getFilteredEvents(wrapper);
        assertEquals(expectedEvents, returnedEvents);
    }

    @Test
    public void testGetFilteredEventsBoyarkaPayed() {
        int boyarkaId = 3;

        List<Event> expectedEvents = new ArrayList<>();
        expectedEvents.add(eventService.getEvent(6));

        EventWrapper wrapper = new EventWrapper();
        wrapper.setCityId(boyarkaId);
        wrapper.setFree(false);

//        FilteredEventsParameter eventsParameter = new FilteredEventsParameter();
//        eventsParameter.setCity(cityService.getCity(boyarkaId));
//        eventsParameter.setFree(false);

        List<Event> returnedEvents = eventService.getFilteredEvents(wrapper);
        assertEquals(expectedEvents, returnedEvents);
    }

    @Test
    public void testGetFilteredEventsPhpAntSql() {
        int phpId = 3;
        int antId = 7;
        int sqlId = 10;

        List<Event> expectedEvents = new ArrayList<>();
        expectedEvents.add(eventService.getEvent(4));
        expectedEvents.add(eventService.getEvent(3));
        expectedEvents.add(eventService.getEvent(7));

        EventWrapper wrapper = new EventWrapper();
        String[] testTechnologies = new String[]{
                technologyService.getTechnology(phpId).getName(),
                technologyService.getTechnology(antId).getName(),
                technologyService.getTechnology(sqlId).getName()};
        wrapper.setTechnologiesNames(testTechnologies);

//        List<Technology> testTechnologies = new ArrayList<>();
//        testTechnologies.add(technologyService.getTechnology(phpId));
//        testTechnologies.add(technologyService.getTechnology(antId));
//        testTechnologies.add(technologyService.getTechnology(sqlId));

//        FilteredEventsParameter eventsParameter = new FilteredEventsParameter();
//        eventsParameter.setTechnologies(testTechnologies);

        List<Event> returnedEvents = eventService.getFilteredEvents(wrapper);
        assertEquals(expectedEvents, returnedEvents);
    }

    @Test
    public void testGetFilteredEventsInRadius() {
        double testLatitude = 50.454605;
        double testLongitude = 30.403965;
        int testRadius = 5000;

        List<Event> expectedEvents = new ArrayList<>();
        expectedEvents.add(eventService.getEvent(2));
        expectedEvents.add(eventService.getEvent(3));

        EventWrapper wrapper = new EventWrapper();

        wrapper.setLatitude(testLatitude);
        wrapper.setLongitude(testLongitude);
        wrapper.setRadius(testRadius);

        List<Event> returnedEvents = eventService.getFilteredEvents(wrapper);
        assertEquals(expectedEvents, returnedEvents);
    }
}