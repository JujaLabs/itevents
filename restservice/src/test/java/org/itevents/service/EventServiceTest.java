package org.itevents.service;

import org.itevents.model.Event;
import org.itevents.model.Technology;
import org.itevents.parameter.FilteredEventsParameter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext.xml"})
@Transactional
public class EventServiceTest {

    private final int ID_1 = 1;
    private final int ID_2 = 2;
    private final int ID_3 = 3;
    private final int ID_4 = 4;
    private final int ID_6 = 6;
    private final int ID_7 = 7;
    @Inject
    private EventService eventService;
    @Inject
    private TechnologyService technologyService;
    @Inject
    private CityService cityService;

    //todo this test must do branch №6
    @Test
    public void testGetEventById() {
        Event returnedEvent = eventService.getEvent(ID_1);
        assertNotNull(returnedEvent);
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
