package org.itevents.controller;

import org.codehaus.jackson.map.ObjectMapper;
import org.itevents.model.Event;
import org.itevents.model.User;
import org.itevents.service.EventService;
import org.itevents.service.UserService;
import org.itevents.test_utils.BuilderUtil;
import org.itevents.util.time.DateTimeUtil;
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

public class EventRestControllerTest extends AbstractControllerSecurityTest {

    @Mock
    private EventService eventService;
    @Mock
    private UserService userService;
    @Mock
    private DateTimeUtil dateTimeUtil;
    @InjectMocks
    private EventRestController eventRestController;

    @Before
    public void init() {
        super.initMock(this);
        super.initMvc(eventRestController);
        super.authenticationUser(BuilderUtil.buildSubscriberTest());
    }

    @Test
    public void shouldFindEventById() throws Exception {
        Event event = BuilderUtil.buildEventJava();

        when(eventService.getEvent(event.getId())).thenReturn(event);

        mockMvc.perform(get("/events/" + event.getId()))
                .andExpect(status().isOk());
        verify(eventService).getEvent(event.getId());
    }

    @Test
    public void shouldAssignUserToEvent() throws Exception {
        Event event = BuilderUtil.buildEventJava();

        when(eventService.getEvent(event.getId())).thenReturn(event);

        mockMvc.perform(post("/events/" + event.getId() + "/assign"))
                .andExpect(status().isOk());
        verify(eventService).getEvent(event.getId());
    }

    @Test
    public void shouldUnassignUserFromEvent() throws Exception{
        Event event = BuilderUtil.buildEventJava();
        User user = BuilderUtil.buildUserAnakin();
        ArrayList <Event> expectedEvents = new ArrayList<>();
        expectedEvents.add(event);
        String unassignReason = "test";

        when(eventService.getEvent(event.getId())).thenReturn(event);
        when(userService.getAuthorizedUser()).thenReturn(user);
        when(eventService.getEventsByUser(user)).thenReturn(expectedEvents);

        mockMvc.perform(post("/events/" + event.getId() + "/unassign/" + unassignReason))
                .andExpect(status().isOk());
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
    public void shouldNotAssignUserToEventIfEventIsAbsent() throws Exception {
        mockMvc.perform(post("/events/0/assign"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldNotAssignUserToEventIfEventDateIsPassed() throws Exception {
        Event event = BuilderUtil.buildEventJava();
        event.setEventDate(new Date());

        mockMvc.perform(post("/events/" + event.getId() + "/assign"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldNotAssignUserToEventIfAssigned() throws Exception {
        User user = BuilderUtil.buildUserAnakin();
        Event event = BuilderUtil.buildEventJava();
        ArrayList <Event> expectedEvents = new ArrayList<>();
        expectedEvents.add(event);

        when(eventService.getEvent(event.getId())).thenReturn(event);
        when(userService.getAuthorizedUser()).thenReturn(user);
        when(eventService.getEventsByUser(user)).thenReturn(expectedEvents);

        mockMvc.perform(post("/events/" + event.getId() + "/assign"))
                .andExpect(status().isConflict());
    }
}
