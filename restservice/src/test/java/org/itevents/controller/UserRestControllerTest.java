package org.itevents.controller;

import org.codehaus.jackson.map.ObjectMapper;
import org.itevents.model.Event;
import org.itevents.model.Role;
import org.itevents.model.User;
import org.itevents.service.EventService;
import org.itevents.service.RoleService;
import org.itevents.service.UserService;
import org.itevents.test_utils.BuilderUtil;
import org.itevents.util.OneTimePassword.OtpGen;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by vaa25 on 16.10.2015.
 */
public class UserRestControllerTest extends AbstractControllerSecurityTest {

    @Mock
    private RoleService roleService;
    @Mock
    private UserService userService;
    @Mock
    private EventService eventService;
    @InjectMocks
    private UserRestController userRestController;
    @Mock
    OtpGen otpGen;

    @Before
    public void init() {
        super.initMock(this);
        super.initMvc(userRestController);
        super.authenticationUser(BuilderUtil.buildSubscriberTest());
    }

    @Test
    public void shouldRegisterNewSubscriber() throws Exception {
        Role subscriberRole = BuilderUtil.buildRoleSubscriber();
        User testSubscriber = BuilderUtil.buildSubscriberTest();

        when(roleService.getRoleByName(subscriberRole.getName())).thenReturn(subscriberRole);
        when(userService.getUserByName(testSubscriber.getLogin())).thenReturn(null);
        doNothing().when(userService).addUser(testSubscriber);

        mockMvc.perform(post("/users/register")
                .param("username", testSubscriber.getLogin())
                .param("password", testSubscriber.getPassword()))
                .andExpect(status().isOk());

        verify(roleService).getRoleByName(subscriberRole.getName());
        verify(userService).getUserByName(testSubscriber.getLogin());
        verify(userService).addUser(testSubscriber);
    }

    @Test
    public void shouldNotRegisterExistingSubscriber() throws Exception {
        User user = BuilderUtil.buildSubscriberTest();
        when(userService.getUserByName(user.getLogin())).thenReturn(user);

        mockMvc.perform(post("/users/register")
                .param("username", user.getLogin())
                .param("password", user.getPassword()))
                .andExpect(status().isImUsed());

        verify(roleService, never()).getRole(anyInt());
        verify(userService).getUserByName(user.getLogin());
        verify(userService, never()).addUser(user);
    }
    
    @Test
    public void shouldReturnEventsByUser() throws Exception {
        User user = BuilderUtil.buildUserAnakin();
        List<Event> expectedEvents = new ArrayList<>();
        expectedEvents.add(BuilderUtil.buildEventJs());
        String expectedEventsInJson = new ObjectMapper().writeValueAsString(expectedEvents);

        when(eventService.getEventsByUser(user)).thenReturn(expectedEvents);
        when(userService.getUser(user.getId())).thenReturn(user);

        mockMvc.perform(get("/users/" + user.getId() + "/events"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedEventsInJson));
    }
}