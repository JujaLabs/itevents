package org.itevents.service.transactional;

import org.itevents.dao.EventDao;
import org.itevents.dao.exception.EntityNotFoundDaoException;
import org.itevents.dao.model.Event;
import org.itevents.dao.model.Filter;
import org.itevents.dao.model.User;
import org.itevents.dao.model.VisitLog;
import org.itevents.dao.model.builder.VisitLogBuilder;
import org.itevents.service.EventService;
import org.itevents.service.UserService;
import org.itevents.service.VisitLogService;
import org.itevents.service.exception.EntityNotFoundServiceException;
import org.itevents.service.exception.TimeCollisionServiceException;
import org.itevents.test_utils.BuilderUtil;
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
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext.xml"})
public class MyBatisEventServiceTest {

    @InjectMocks
    @Inject
    private EventService eventService;
    @Mock
    private EventDao eventDao;
    @Mock
    private UserService userService;
    @Mock
    private VisitLogService visitLogService;

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
    public void shouldFindEventsByParameter() throws Exception {
        List<Event> expectedEvents = new ArrayList<>();
        expectedEvents.add(BuilderUtil.buildEventJava());
        Filter filter = new Filter();

        when(eventDao.getFilteredEvents(filter)).thenReturn(expectedEvents);

        List<Event> returnedEvents = eventService.getFilteredEvents(filter);

        verify(eventDao).getFilteredEvents(filter);
        assertEquals(expectedEvents, returnedEvents);
    }

    @Test
    public void shouldNotFindEventsByParameter() throws Exception {
        List<Event> expectedEvents = new ArrayList<>();
        Filter filter = new Filter();

        when(eventDao.getFilteredEvents(filter)).thenReturn(new ArrayList<>());

        List<Event> returnedEvents = eventService.getFilteredEvents(filter);

        verify(eventDao).getFilteredEvents(filter);
        assertEquals(expectedEvents, returnedEvents);
    }

    @Test
    public void shouldReturnEventsByUser() throws Exception {
        User user = BuilderUtil.buildUserAnakin();

        eventService.getEventsByUser(user);

        verify(eventDao).getEventsByUser(user);
    }

    @Test
    public void shouldAssignToEvent() throws Exception {
        User user = BuilderUtil.buildUserAnakin();
        Event event = BuilderUtil.buildEventRuby();

        when(eventService.getEvent(event.getId())).thenReturn(event);
        when(userService.getAuthorizedUser()).thenReturn(user);

        eventService.assignAuthorizedUserToEvent(event.getId());

        verify(eventDao).assignUserToEvent(user, event);
    }

    /* @TODO:
     *https://github.com/JuniorsJava/itevents/issues/203
     * Remove any(Date.class) and other matchers
     */
    @Test
    public void shouldUnassignUserFromEvent() throws Exception {
        User user = BuilderUtil.buildUserAnakin();
        Event event = BuilderUtil.buildEventJs();
        String unassignReason = "test";
        List events = new ArrayList<>();
        events.add(event);

        when(eventService.getEvent(event.getId())).thenReturn(event);
        when(userService.getAuthorizedUser()).thenReturn(user);
        when(eventService.getEventsByUser(user)).thenReturn(events);

        eventService.unassignAuthorizedUserFromEvent(event.getId(), unassignReason);

        verify(eventDao).unassignUserFromEvent(eq(user), eq(event), any(Date.class), eq(unassignReason));
    }

    @Test
    public void shouldFindFutureEvent() throws Exception {
        Event expectedEvent = BuilderUtil.buildEventJava();

        when(eventDao.getEvent(expectedEvent.getId())).thenReturn(expectedEvent);

        Event returnedEvent = eventService.getFutureEvent(expectedEvent.getId());

        assertEquals(expectedEvent, returnedEvent);
    }

    @Test(expected = TimeCollisionServiceException.class)
    public void shouldThrowTimeCollisionServiceExceptionWhenTryFindPastEventAsFutureEvent() throws Exception {
        Event event = BuilderUtil.buildEventJava();
        int yesterday = -1;
        event.setEventDate(getDateInPast(yesterday));

        when(eventDao.getEvent(event.getId())).thenReturn(event);

        eventService.getFutureEvent(event.getId());
    }

    private Date getDateInPast(int daysCount) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, daysCount);
        return calendar.getTime();
    }

    @Test
    public void shouldReturnLinkToEventSite() throws Exception {
        Event event = BuilderUtil.buildEventJava();
        User userGuest = BuilderUtil.buildUserGuest();
        VisitLog visitLog = VisitLogBuilder.aVisitLog().event(event).user(userGuest).build();
        String expectedLink = event.getRegLink();

        when(eventService.getEvent(event.getId())).thenReturn(event);
        when(userService.getAuthorizedUser()).thenReturn(userGuest);
        doNothing().when(visitLogService).addVisitLog(visitLog);

        String actualLink = eventService.redirectToEventSite(event.getId());

        assertEquals(expectedLink, actualLink);
    }
}