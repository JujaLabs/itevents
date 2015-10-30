package org.itevents.controller;

import org.itevents.model.Event;
import org.itevents.model.User;
import org.itevents.service.EventService;
import org.itevents.service.UserService;
import org.itevents.test_utils.BuilderUtil;
import org.junit.Test;
import org.springframework.security.test.context.support.WithMockUser;

import javax.inject.Inject;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class EventRestControllerTest extends AbstractControllerTest {
    @Inject
    private EventService eventService;
    @Inject
    private UserService userService;

    @Test
    public void shouldFindEventById() throws Exception {
        Event event = BuilderUtil.buildEventJava();
        when(eventService.getEvent(event.getId())).thenReturn(event);
        mvc.perform(get("/GET/events/" + event.getId()))
                .andExpect(status().isOk());
        verify(eventService).getEvent(event.getId());
    }

    @Test
    @WithMockUser(username = "testSubscriber", password = "testSubscriberPassword", authorities = "subscriber")
    public void shouldSubscribeUserToEvent() throws Exception {
        User user = BuilderUtil.buildSubscriberTest();
        Event event = BuilderUtil.buildEventJava();
        when(userService.getUserByName(user.getLogin())).thenReturn(user);
        when(eventService.getEvent(event.getId())).thenReturn(event);
        mvc.perform(post("/POST/events/" + event.getId() + "/willGo"))
                .andExpect(status().isCreated());
        verify(eventService, atLeastOnce()).getEvent(event.getId());
    }

    @Test
    @WithMockUser(username = "testSubscriber", password = "testSubscriberPassword", authorities = "subscriber")
    public void shouldUnSubscribeUserFromEvent() throws Exception{
        User user = BuilderUtil.buildSubscriberTest();
        Event event = BuilderUtil.buildEventJava();
        when(userService.getUserByName(user.getLogin())).thenReturn(user);
        when(eventService.getEvent(event.getId())).thenReturn(event);
        mvc.perform(delete("/DELETE/events/" + event.getId() + "/willNotGo"))
                .andExpect(status().isOk());
        verify(eventService, atLeastOnce()).getEvent(event.getId());
    }

    @Test
    public void shouldReturnListOfVisitors() throws Exception {
        Event event = BuilderUtil.buildEventJava();
        List expectedList = eventService.getVisitors(event);
        when(eventService.getEvent(event.getId())).thenReturn(event);
        mvc.perform(get("/GET/events/" + event.getId() + "/getVisitors"))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedList.toString()));
        verify(eventService, atLeastOnce()).getVisitors(event);
    }
}
