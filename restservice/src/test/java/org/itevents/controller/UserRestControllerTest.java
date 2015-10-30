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

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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
        User testSubscriber = BuilderUtil.buildSubscriberTest();

        when(roleService.getRoleByName(subscriberRole.getName())).thenReturn(subscriberRole);
        when(userService.getUserByName(testSubscriber.getLogin())).thenReturn(null);
        doNothing().when(userService).addUser(testSubscriber);

        mvc.perform(post("/POST/users/register")
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

        mvc.perform(post("/POST/users/register")
                .param("username", user.getLogin())
                .param("password", user.getPassword()))
                .andExpect(status().isImUsed());

        verify(roleService, never()).getRole(anyInt());
        verify(userService,atLeastOnce()).getUserByName(user.getLogin());
        verify(userService, never()).addUser(user);
    }

    @Test
    @WithMockUser(username = "testSubscriber", password = "testSubscriberPassword", authorities = "subscriber")
    public void shouldRemoveExistingSubscriber() throws Exception {
        User user = BuilderUtil.buildSubscriberTest();

        when(userService.getUserByName(user.getLogin())).thenReturn(user);
        when(userService.removeUser(user)).thenReturn(user);

        mvc.perform(delete("/DELETE/users/delete"))
                .andExpect(status().isOk());

        verify(userService).getUserByName(user.getLogin());
        verify(userService).removeUser(user);
    }

    @Test
    public void shouldReturnUserSubscribedEvents() throws Exception {
        User user = BuilderUtil.buildUserAnakin();
        List expectedList = userService.getUserEvents(user);
        when(userService.getUser(user.getId())).thenReturn(user);
        mvc.perform(get("/GET/users/" +user.getId() + "/events"))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedList.toString()));
        verify(userService, atLeastOnce()).getUserEvents(user);
    }
}