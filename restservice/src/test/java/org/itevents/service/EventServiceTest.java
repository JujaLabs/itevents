package org.itevents.service;

import org.itevents.model.Event;
import org.itevents.util.BuilderUtil;
import org.itevents.wrapper.EventWrapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
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
        addingEvent = BuilderUtil.buildEventRuby();
    }

    @Test
    public void testGetEventById() throws ParseException {
        Event expectedEvent = BuilderUtil.buildEventJava();
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
        Event expectedEvent = BuilderUtil.buildEventRuby();
        eventService.addEvent(expectedEvent);
        Event returnedEvent = eventService.removeEvent(expectedEvent);
        expectedEvent.setId(returnedEvent.getId());
        Assert.assertEquals(expectedEvent, returnedEvent);
    }

    @Test
    public void testAddEvent() throws ParseException {
        Event addingEvent = BuilderUtil.buildEventRuby();
        eventService.addEvent(addingEvent);
        Event returnedEvent = eventService.getEvent(addingEvent.getId());
        Assert.assertEquals(addingEvent, returnedEvent);
        eventService.removeEvent(returnedEvent);
    }

    @Test
    @Ignore
    public void testGetFilteredEventsEmpty() {
        EventWrapper wrapper = new EventWrapper();

        List<Event> expectedEvents = new ArrayList<>();
        expectedEvents.addAll(eventService.getAllEvents());

        List<Event> returnedEvents = eventService.getFilteredEvents(wrapper);
        assertEquals(expectedEvents, returnedEvents);
    }

    @Test
    @Ignore
    public void testGetFilteredEventsPage3ItemsPerPage2() {
        EventWrapper wrapper = new EventWrapper();
        wrapper.setPage(3);
        wrapper.setItemsPerPage(2);

        List<Event> expectedEvents = new ArrayList<>();
        expectedEvents.add(eventService.getEvent(5));
        expectedEvents.add(eventService.getEvent(6));

        List<Event> returnedEvents = eventService.getFilteredEvents(wrapper);
        assertEquals(expectedEvents, returnedEvents);
    }

    @Test
    @Ignore
    public void testGetFilteredEventsPage30ItemsPerPageMinus2() {
        EventWrapper wrapper = new EventWrapper();
        wrapper.setPage(30);
        wrapper.setItemsPerPage(-2);

        List<Event> expectedEvents = new ArrayList<>();

        List<Event> returnedEvents = eventService.getFilteredEvents(wrapper);
        assertEquals(expectedEvents, returnedEvents);
    }

    @Test
    @Ignore
    public void testGetFilteredEventsKyivJava() {
        int javaId = 1;
        int kyivId = 1;

        EventWrapper wrapper = new EventWrapper();
        String[] testTechnologies = new String[]{technologyService.getTechnology(javaId).getName()};
        wrapper.setTechnologiesNames(testTechnologies);
        wrapper.setCityId(kyivId);

        List<Event> expectedEvents = new ArrayList<>();
        expectedEvents.add(eventService.getEvent(1));

        List<Event> returnedEvents = eventService.getFilteredEvents(wrapper);
        assertEquals(expectedEvents, returnedEvents);
    }

    @Test
    @Ignore
    public void testGetFilteredEventsBoyarkaPayed() {
        int boyarkaId = 3;

        List<Event> expectedEvents = new ArrayList<>();
        expectedEvents.add(eventService.getEvent(6));

        EventWrapper wrapper = new EventWrapper();
        wrapper.setCityId(boyarkaId);
        wrapper.setFree(false);

        List<Event> returnedEvents = eventService.getFilteredEvents(wrapper);
        assertEquals(expectedEvents, returnedEvents);
    }

    @Test
    @Ignore
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

        List<Event> returnedEvents = eventService.getFilteredEvents(wrapper);
        assertEquals(expectedEvents, returnedEvents);
    }

    @Test
    @Ignore
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