package org.itevents.controller;

import org.itevents.model.Role;
import org.itevents.model.User;
import org.itevents.service.RoleService;
import org.itevents.service.UserService;
import org.itevents.test_utils.BuilderUtil;
import org.junit.Test;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;

import javax.inject.Inject;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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

    @Test
    public void shouldRegisterNewSubscriber() throws Exception {
        Role subscriberRole = BuilderUtil.buildRoleSubscriber();
        when(roleService.getRole(subscriberRole.getId())).thenReturn(subscriberRole);
        User testSubscriber = BuilderUtil.buildSubscriberTest();
        when(userService.getUserByName(testSubscriber.getLogin())).thenReturn(null);
        doNothing().when(userService).addUser(testSubscriber);
        mvc.perform(post("/users/register")
                .param("username", testSubscriber.getLogin())
                .param("password", testSubscriber.getPassword()))
                .andExpect(status().isOk());
        verify(roleService).getRole(subscriberRole.getId());
        verify(userService).getUserByName(testSubscriber.getLogin());
        verify(userService).addUser(testSubscriber);
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
    public void shouldRemoveUser() throws Exception {
        User user = BuilderUtil.buildSubscriberTest();
        when(userService.getUserByName(user.getLogin())).thenReturn(user);
        when(userService.removeUser(user)).thenReturn(user);
        mvc.perform(delete("/users/delete"))
                .andExpect(status().isOk());
        verify(userService).getUserByName(user.getLogin());
        verify(userService).removeUser(user);

    }

    @Test
    @WithMockUser(username = "testSubscriber", password = "testSubscriberPassword", authorities = "subscriber")
    public void shouldNotRemoveUser() throws Exception {
        User user = BuilderUtil.buildSubscriberTest();
        when(userService.getUserByName(user.getLogin())).thenReturn(user);
        when(userService.removeUser(user)).thenReturn(null);
        mvc.perform(delete("/users/delete"))
                .andExpect(status().isNotFound());
        verify(userService).getUserByName(user.getLogin());
        verify(userService).removeUser(user);

    }
}