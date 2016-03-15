package org.itevents.controller;

import org.codehaus.jackson.map.ObjectMapper;
import org.itevents.dao.model.Event;
import org.itevents.dao.model.User;
import org.itevents.dao.model.VisitLog;
import org.itevents.dao.model.builder.VisitLogBuilder;
import org.itevents.service.EventService;
import org.itevents.service.UserService;
import org.itevents.service.VisitLogService;
import org.itevents.test_utils.BuilderUtil;
import org.itevents.util.time.Clock;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class EventRestControllerTest extends AbstractControllerTest {

    @Mock
    private EventService eventService;
    @Mock
    private UserService userService;
    @Mock
    private VisitLogService visitLogService;
    @Mock
    private Clock clock;
    @InjectMocks
    private EventRestController eventRestController;

    @Before
    public void init() {
        super.initMock(this);
        super.initMvc(eventRestController);
    }

    @Test
    public void shouldFindEventById() throws Exception {
        Event event = BuilderUtil.buildEventJava();
        String expectedEventInJson = new ObjectMapper().writeValueAsString(event);

        when(eventService.getEvent(event.getId())).thenReturn(event);

        mockMvc.perform(get("/events/" + event.getId())
                .header("Accept", "application/json"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedEventInJson));
    }

    @Test
    public void shouldAssignUserToEvent() throws Exception {
        Event event = BuilderUtil.buildEventJava();
        User user = BuilderUtil.buildTestSubscriber();

        when(eventService.getFutureEvent(event.getId())).thenReturn(event);
        when(userService.getAuthorizedUser()).thenReturn(user);

        mockMvc.perform(post("/events/" + event.getId() + "/assign"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldUnassignUserFromEvent() throws Exception{
        Event event = BuilderUtil.buildEventJava();
        User user = BuilderUtil.buildUserAnakin();
        String validUnassignReason = "test";
        Date nowDate = new Date();

        when(eventService.getFutureEvent(event.getId())).thenReturn(event);
        when(userService.getAuthorizedUser()).thenReturn(user);
        when(clock.getNowDateTime()).thenReturn(nowDate);

        mockMvc.perform(post("/events/" + event.getId() + "/unassign")
                .param("unassign_reason", validUnassignReason))
                .andExpect(status().isOk());

        verify(eventService).getFutureEvent(event.getId());
        verify(userService).getAuthorizedUser();
        verify(clock).getNowDateTime();
        verify(eventService).unassignUserFromEvent(user, event, nowDate, validUnassignReason);
    }

    @Test
    public void shouldReturnUsersByEvent() throws Exception {
        Event event = BuilderUtil.buildEventJava();
        List<User> expectedUsers = new ArrayList<>();
        expectedUsers.add(BuilderUtil.buildUserAnakin());
        String expectedUsersInJson = new ObjectMapper().writeValueAsString(expectedUsers);

        when(eventService.getEvent(event.getId())).thenReturn(event);
        when(userService.getUsersByEvent(event)).thenReturn(expectedUsers);

        mockMvc.perform(get("/events/" + event.getId() + "/visitors"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedUsersInJson));
    }

    @Test
    public void shouldRegisterUserToEventAndReturnUrlToEventSite() throws Exception {
        Event event = BuilderUtil.buildEventJava();
        User userGuest = BuilderUtil.buildUserGuest();
        Date nowDate = new Date();
        VisitLog visitLog = VisitLogBuilder.aVisitLog()
                .event(event)
                .user(userGuest)
                .date(nowDate)
                .build();

        when(eventService.getEvent(event.getId())).thenReturn(event);
        when(userService.getAuthorizedUser()).thenReturn(userGuest);
        when(clock.getNowDateTime()).thenReturn(nowDate);

        mockMvc.perform(get("/events/" + event.getId() + "/register"))
                .andExpect(content().string(event.getRegLink()))
                .andExpect(status().isOk());

        verify(eventService).getEvent(event.getId());
        verify(userService).getAuthorizedUser();
        verify(visitLogService).addVisitLog(visitLog);
        verify(clock).getNowDateTime();
    }
}
