package org.itevents.service;

import org.itevents.dao.EventDao;
import org.itevents.model.Event;
import org.itevents.parameter.FilteredEventsParameter;
import org.itevents.util.BuilderUtil;
import org.itevents.wrapper.EventWrapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.text.ParseException;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext.xml"})
@Transactional
public class EventServiceTest {

    private final int ID_1 = 1;

    @InjectMocks
    @Inject
    private EventService eventService;
    @Mock
    private EventDao eventDao;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetEvent() {
        eventService.getEvent(ID_1);
        verify(eventDao).getEvent(ID_1);
    }

    @Test
    public void testAddEvent() throws ParseException {
        Event testEvent = BuilderUtil.buildEventRuby();
        eventService.addEvent(testEvent);
        verify(eventDao).addEvent(testEvent);
        verify(eventDao).addEventTechnology(testEvent);
    }

    @Test
    public void testGetAllEvents() {
        eventService.getAllEvents();
        verify(eventDao).getAllEvents();
    }

    @Test
    public void testRemoveEventSuccess() throws ParseException {
        Event expectedEvent = BuilderUtil.buildEventRuby();
        when(eventDao.getEvent(expectedEvent.getId())).thenReturn(expectedEvent);
        doNothing().when(eventDao).removeEventTechnology(expectedEvent);
        doNothing().when(eventDao).removeEvent(expectedEvent);
        Event returnedEvent = eventService.removeEvent(expectedEvent);
        verify(eventDao).getEvent(expectedEvent.getId());
        verify(eventDao).removeEventTechnology(expectedEvent);
        verify(eventDao).removeEvent(expectedEvent);
        assertEquals(expectedEvent, returnedEvent);
    }

    @Test
    public void testRemoveEventFail() throws ParseException {
        Event expectedEvent = BuilderUtil.buildEventRuby();
        when(eventDao.getEvent(expectedEvent.getId())).thenReturn(null);
        doNothing().when(eventDao).removeEventTechnology(expectedEvent);
        doNothing().when(eventDao).removeEvent(expectedEvent);
        Event returnedEvent = eventService.removeEvent(expectedEvent);
        verify(eventDao).getEvent(expectedEvent.getId());
        verify(eventDao, never()).removeEventTechnology(expectedEvent);
        verify(eventDao, never()).removeEvent(expectedEvent);
        assertNull(returnedEvent);
    }

    @Test
    public void testGetFilteredEventsSuccess() throws ParseException {
        Set<Event> expectedEvents = new HashSet<>();
        expectedEvents.add(BuilderUtil.buildEventJava());
        when(eventDao.getFilteredEvents(any(FilteredEventsParameter.class))).thenReturn(expectedEvents);
        EventWrapper wrapper = new EventWrapper();
        Set<Event> returnedEvents = eventService.getFilteredEvents(wrapper);
        assertEquals(expectedEvents, returnedEvents);
    }

    @Test
    public void testGetFilteredEventsFail() throws ParseException {
        Set<Event> expectedEvents = new HashSet<>();
        when(eventDao.getFilteredEvents(any(FilteredEventsParameter.class))).thenThrow(Exception.class);
        EventWrapper wrapper = new EventWrapper();
        Set<Event> returnedEvents = eventService.getFilteredEvents(wrapper);
        assertEquals(expectedEvents, returnedEvents);
    }
}