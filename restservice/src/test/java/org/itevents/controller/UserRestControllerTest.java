package org.itevents.controller;

import org.itevents.model.Filter;
import org.itevents.model.Role;
import org.itevents.model.User;
import org.itevents.service.FilterService;
import org.itevents.service.RoleService;
import org.itevents.service.UserService;
import org.itevents.test_utils.BuilderUtil;
import org.junit.Test;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;

import javax.inject.Inject;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by vaa25 on 16.10.2015.
 */
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserRestControllerTest extends AbstractControllerTest {
    @Inject
    private RoleService roleService;
    @Inject
    private UserService userService;
    @Inject
    private FilterService filterService;

    @Test
    public void shouldRegisterNewSubscriber() throws Exception {
        Role subscriberRole = BuilderUtil.buildRoleSubscriber();
        User testSubscriber = BuilderUtil.buildSubscriberTest();

        when(roleService.getRoleByName(subscriberRole.getName())).thenReturn(subscriberRole);
        when(userService.getUserByName(testSubscriber.getLogin())).thenReturn(null);
        doNothing().when(userService).addUser(any(User.class));

        mvc.perform(post("/users/register")
                .param("username", testSubscriber.getLogin())
                .param("password", testSubscriber.getPassword()))
                .andExpect(status().isOk());

        verify(roleService).getRoleByName(subscriberRole.getName());
        verify(userService).getUserByName(testSubscriber.getLogin());
        verify(userService).addUser(any(User.class));
    }

    @Test
    public void shouldNotRegisterExistingSubscriber() throws Exception {
        User user = BuilderUtil.buildSubscriberTest();

        when(userService.getUserByName(user.getLogin())).thenReturn(user);

        mvc.perform(post("/users/register")
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

        mvc.perform(get("/users/subscribe"))
                .andExpect(status().isOk());

        verify(userService).getAuthorizedUser();
        verify(userService).activateUserSubscription(user);
        verify(filterService).addFilter(eq(user), any(Filter.class));
    }
}