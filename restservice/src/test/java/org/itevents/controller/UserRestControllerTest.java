package org.itevents.controller;

import org.codehaus.jackson.map.ObjectMapper;
import org.itevents.model.Event;
import org.itevents.model.Filter;
import org.itevents.model.Role;
import org.itevents.model.User;
import org.itevents.service.EventService;
import org.itevents.service.FilterService;
import org.itevents.service.RoleService;
import org.itevents.service.UserService;
import org.itevents.test_utils.BuilderUtil;
import org.junit.Test;

import javax.inject.Inject;
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
public class UserRestControllerTest extends AbstractWebTest {

    @Inject
    private RoleService roleService;
    @Inject
    private UserService userService;
    @Inject
    private FilterService filterService;
    @Inject
    private EventService eventService;

    @Test
    public void shouldRegisterNewSubscriber() throws Exception {
        Role subscriberRole = BuilderUtil.buildRoleSubscriber();
        User testUser = BuilderUtil.buildUserTest();
        String encodedPassword = "encodedPassword";

        when(roleService.getRoleByName(subscriberRole.getName())).thenReturn(subscriberRole);
        when(userService.getUserByName(testUser.getLogin())).thenReturn(null);
        doNothing().when(userService).addUser(testUser);

        mockMvc.perform(post("/users/register")
                .param("username", testUser.getLogin())
                .param("password", testUser.getPassword()))
                .andExpect(status().isOk());

        testUser.setRole(subscriberRole);
        testUser.setPassword(encodedPassword);
    }

    @Test
    public void shouldNotRegisterExistingSubscriber() throws Exception {
        User user = BuilderUtil.buildSubscriberTest();

        when(userService.getUserByName(user.getLogin())).thenReturn(user);

        mockMvc.perform(post("/users/register")
                .param("username", user.getLogin())
                .param("password", user.getPassword()))
                .andExpect(status().isImUsed());
    }

    @Test
    public void shouldAddFilterForSubscription() throws Exception {
        User user = BuilderUtil.buildSubscriberTest();

        when(userService.getAuthorizedUser()).thenReturn(user);
        doNothing().when(filterService).addFilter(eq(user), any(Filter.class));
        doNothing().when(userService).activateUserSubscription(user);

        mockMvc.perform(get("/users/subscribe"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldDeactivateSubscription() throws Exception {
        User user = BuilderUtil.buildSubscriberTest();

        when(userService.getAuthorizedUser()).thenReturn(user);
        doNothing().when(userService).deactivateUserSubscription(user);

        mockMvc.perform(get("/users/unsubscribe"))
                .andExpect(status().isOk());
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