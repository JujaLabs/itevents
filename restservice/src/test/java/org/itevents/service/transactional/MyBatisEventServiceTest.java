package org.itevents.service.transactional;

import org.itevents.dao.EventDao;
import org.itevents.dao.exception.EntityNotFoundDaoException;
import org.itevents.model.Event;
import org.itevents.model.Filter;
import org.itevents.model.User;
import org.itevents.service.EventService;
import org.itevents.service.exception.EntityNotFoundServiceException;
import org.itevents.test_utils.BuilderUtil;
import org.itevents.wrapper.FilterWrapper;
import org.itevents.util.time.DateTimeUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext.xml"})
public class MyBatisEventServiceTest {

    @InjectMocks
    @Inject
    private EventService eventService;
    @Inject
    private DateTimeUtil dateTimeUtil;
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

    @Test(expected = EntityNotFoundServiceException.class)
    public void shouldThrowEventNotFoundServiceException() throws Exception {
        int absentId = 0;

        when(eventDao.getEvent(absentId)).thenThrow(EntityNotFoundDaoException.class);

        eventService.getEvent(absentId);
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
    public void shouldFindEventsByParameter() throws ParseException {
        List<Event> expectedEvents = new ArrayList<>();
        expectedEvents.add(BuilderUtil.buildEventJava());

        when(eventDao.getFilteredEvents(any(Filter.class))).thenReturn(expectedEvents);

        List<Event> returnedEvents = eventService.getFilteredEvents(new FilterWrapper());

        verify(eventDao).getFilteredEvents(any(Filter.class));
        assertEquals(expectedEvents, returnedEvents);
    }

    @Test
    public void shouldNotFindEventsByParameter() throws ParseException {
        List<Event> expectedEvents = new ArrayList<>();

        when(eventDao.getFilteredEvents(any(Filter.class))).thenReturn(new ArrayList<>());

        List<Event> returnedEvents = eventService.getFilteredEvents(new FilterWrapper());

        verify(eventDao).getFilteredEvents(any(Filter.class));
        assertEquals(expectedEvents, returnedEvents);
    }

    @Test
    public void shouldReturnEventsByUser() throws Exception{
        User user = BuilderUtil.buildUserAnakin();
        eventService.getEventsByUser(user);
        verify(eventDao).getEventsByUser(user);
    }

    @Test
    public void shouldAssignToEvent() throws Exception {
        User user = BuilderUtil.buildUserAnakin();
        Event event = BuilderUtil.buildEventRuby();
        eventService.assignUserToEvent(user, event);
        verify(eventDao).assignUserToEvent(user, event);
    }

    @Test
    public void shouldUnassignUserFromEvent()throws Exception {
        User user = BuilderUtil.buildUserAnakin();
        Event event = BuilderUtil.buildEventJs();

        Date unassignDate = dateTimeUtil.setDate("20.07.2115");
        String unassignReason = "test";
        List events = new ArrayList<>();
        events.add(event);

        when(eventService.getEventsByUser(user)).thenReturn(events);

        eventService.unassignUserFromEvent(user, event, unassignDate, unassignReason);
        verify(eventDao).unassignUserFromEvent(user, event, unassignDate, unassignReason);
    }
}