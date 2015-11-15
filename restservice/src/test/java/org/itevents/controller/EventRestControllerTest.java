package org.itevents.controller;

import org.itevents.model.Event;
import org.itevents.service.EventService;
import org.itevents.service.UserService;
import org.itevents.test_utils.BuilderUtil;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class EventRestControllerTest extends AbstractControllerSecurityTest {

    @Mock
    private EventService eventService;
    @Mock
    private UserService userService;
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
    public void shouldSubscribeUserToEvent() throws Exception {
        Event event = BuilderUtil.buildEventJava();
        when(eventService.getEvent(event.getId())).thenReturn(event);
        mockMvc.perform(post("/events/" + event.getId() + "/willGo"))
                .andExpect(status().isCreated());
        verify(eventService, atLeastOnce()).getEvent(event.getId());
    }

    @Test
    public void shouldUnSubscribeUserFromEvent() throws Exception{
        Event event = BuilderUtil.buildEventJava();
        when(eventService.getEvent(event.getId())).thenReturn(event);
        mockMvc.perform(delete("/events/" + event.getId() + "/willNotGo"))
                .andExpect(status().isOk());
        verify(eventService, atLeastOnce()).getEvent(event.getId());
    }

    @Test
    public void shouldReturnListOfVisitors() throws Exception {
        Event event = BuilderUtil.buildEventJava();
        when(eventService.getEvent(event.getId())).thenReturn(event);
        mockMvc.perform(get("/events/" + event.getId() + "/getVisitors"))
                .andExpect(status().isOk());
        verify(eventService, atLeastOnce()).getVisitors(event);
    }
}
