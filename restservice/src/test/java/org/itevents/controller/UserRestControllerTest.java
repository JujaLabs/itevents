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
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;

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
    private FilterService filterService;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private EventService eventService;
    @InjectMocks
    private UserRestController userRestController;

    @Before
    public void init() {
        super.initMock(this);
        super.initMvc(userRestController);
        super.authenticationUser(BuilderUtil.buildSubscriberTest());
    }

    @Test
    public void shouldRegisterNewSubscriber() throws Exception {
        Role subscriberRole = BuilderUtil.buildRoleSubscriber();
        User testUser = BuilderUtil.buildUserTest();
        String password = testUser.getPassword();
        String encodedPassword = "encodedPassword";

        when(roleService.getRoleByName(subscriberRole.getName())).thenReturn(subscriberRole);
        doNothing().when(userService).checkNameAvailability(testUser.getLogin());
        when(passwordEncoder.encode(testUser.getPassword())).thenReturn(encodedPassword);
        doNothing().when(userService).addUser(testUser);

        mockMvc.perform(post("/users/register")
                .param("username", testUser.getLogin())
                .param("password", testUser.getPassword()))
                .andExpect(status().isOk());

        testUser.setRole(subscriberRole);
        testUser.setPassword(encodedPassword);
        verify(roleService).getRoleByName(subscriberRole.getName());
        verify(userService).checkNameAvailability(testUser.getLogin());
        verify(passwordEncoder).encode(password);
        verify(userService).addUser(testUser);
    }



    @Test
    @WithMockUser(username = "testSubscriber", password = "testSubscriberPassword", authorities = "subscriber")
    public void shouldAddFilterForSubscription() throws Exception {
        User user = BuilderUtil.buildSubscriberTest();

        when(userService.getAuthorizedUser()).thenReturn(user);
        doNothing().when(filterService).addFilter(eq(user), any(Filter.class));
        doNothing().when(userService).activateUserSubscription(user);

        mockMvc.perform(get("/users/subscribe"))
                .andExpect(status().isOk());

        verify(userService).getAuthorizedUser();
        verify(userService).activateUserSubscription(user);
        verify(filterService).addFilter(eq(user), any(Filter.class));
    }

    @Test
    @WithMockUser(username = "testSubscriber", password = "testSubscriberPassword", authorities = "subscriber")
    public void shouldDeactivateSubscription() throws Exception {
        User user = BuilderUtil.buildSubscriberTest();

        when(userService.getAuthorizedUser()).thenReturn(user);
        doNothing().when(userService).deactivateUserSubscription(user);

        mockMvc.perform(get("/users/unsubscribe"))
                .andExpect(status().isOk());

        verify(userService).getAuthorizedUser();
        verify(userService).deactivateUserSubscription(user);
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