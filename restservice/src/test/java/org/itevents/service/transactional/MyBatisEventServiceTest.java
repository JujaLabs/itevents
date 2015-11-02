package org.itevents.service.transactional;

import org.itevents.dao.EventDao;
import org.itevents.model.Event;
import org.itevents.model.User;
import org.itevents.parameter.FilteredEventsParameter;
import org.itevents.service.EventService;
import org.itevents.test_utils.BuilderUtil;
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
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext.xml"})
@Transactional
public class MyBatisEventServiceTest {

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
    public void shouldFindEventById() {
        int ID_1 = 1;

        eventService.getEvent(ID_1);

        verify(eventDao).getEvent(ID_1);
    }

    @Test
    public void shouldAddEvent() throws ParseException {
        Event testEvent = BuilderUtil.buildEventRuby();

        eventService.addEvent(testEvent);

        verify(eventDao).addEvent(testEvent);
        verify(eventDao).addEventTechnology(testEvent);
    }

    @Test
    public void shouldGetAllEvents() {
        eventService.getAllEvents();

        verify(eventDao).getAllEvents();
    }

    @Test
    public void shouldRemoveEvent() throws ParseException {
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
    public void shouldNotRemoveNonExistingEvent() throws ParseException {
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
    public void shouldFindEventsByParameter() throws ParseException {
        List<Event> expectedEvents = new ArrayList<>();
        expectedEvents.add(BuilderUtil.buildEventJava());

        when(eventDao.getFilteredEvents(any(FilteredEventsParameter.class))).thenReturn(expectedEvents);

        List<Event> returnedEvents = eventService.getFilteredEvents(new EventWrapper());

        verify(eventDao).getFilteredEvents(any(FilteredEventsParameter.class));
        assertEquals(expectedEvents, returnedEvents);
    }

    @Test
    public void shouldNotFindEventsByParameter() throws ParseException {
        List<Event> expectedEvents = new ArrayList<>();

        when(eventDao.getFilteredEvents(any(FilteredEventsParameter.class))).thenThrow(Exception.class);

        List<Event> returnedEvents = eventService.getFilteredEvents(new EventWrapper());

        verify(eventDao).getFilteredEvents(any(FilteredEventsParameter.class));
        assertEquals(expectedEvents, returnedEvents);
    }

    @Test
    public void shouldSubsribeUserToEventAndReturnVisitors() throws Exception {
        User user = BuilderUtil.buildUserAnakin();
        Event event = BuilderUtil.buildEventJs();
        List expectedVisitors = new ArrayList<>();
        expectedVisitors.add(user);
        when(eventService.getVisitors(event)).thenReturn(expectedVisitors);
        doNothing().when(eventDao).willGoToEvent(user, event);
        List returnedVisitors =  eventService.getVisitors(event);
        assertEquals(expectedVisitors, returnedVisitors);
    }

    @Test
    public void shouldUnSubscribeUserFromEvent()throws Exception {
        User user = BuilderUtil.buildUserAnakin();
        Event event = BuilderUtil.buildEventJs();
        eventService.willNotGoToEvent(user, event);
        doNothing().when(eventDao).willNotGoToEvent(user, event);
        List returnedVisitors =  eventService.getVisitors(event);
        assertEquals("[]",returnedVisitors.toString());
    }
}