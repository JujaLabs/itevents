package org.itevents.controller;

import org.itevents.model.Filter;
import org.itevents.model.Role;
import org.itevents.model.User;
import org.itevents.service.FilterService;
import org.itevents.service.RoleService;
import org.itevents.service.UserService;
import org.itevents.test_utils.BuilderUtil;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.test.context.support.WithMockUser;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    @InjectMocks
    private UserRestController userRestController;

    @Before
    public void init() {
        super.initMock(this);
        super.initMvc(userRestController);
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

        mvc.perform(get("/users/unsubscribe"))
                .andExpect(status().isOk());

        verify(userService).getAuthorizedUser();
        verify(userService).deactivateUserSubscription(user);
    }
}