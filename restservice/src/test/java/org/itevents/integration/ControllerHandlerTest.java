package org.itevents.integration;

import org.itevents.model.Event;
import org.itevents.model.User;
import org.itevents.model.VisitLog;
import org.itevents.service.EventService;
import org.itevents.service.UserService;
import org.itevents.service.VisitLogService;
import org.itevents.service.exception.EntityNotFoundServiceException;
import org.itevents.service.exception.NameNotAvailableServiceException;
import org.itevents.service.exception.TimeCollisionServiceException;
import org.itevents.test_utils.BuilderUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.inject.Inject;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by vaa25 on 04.01.2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml", "classpath:applicationContextTestAddon.xml",
        "classpath:mvc-dispatcher-servlet.xml", "classpath:spring-security.xml"})
@WebAppConfiguration
public class ControllerHandlerTest {
    @Inject
    private WebApplicationContext context;
    private MockMvc mvc;
    @Inject
    private EventService eventService;
    @Inject
    private UserService userService;
    @Inject
    private VisitLogService visitLogService;

    @Before
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void shouldNotAssignUserToEventIfEventIsAbsent() throws Exception {
        int absentId = 0;

        when(eventService.getFutureEvent(absentId)).thenThrow(EntityNotFoundServiceException.class);

        mvc.perform(post("/events/" + absentId + "/assign"))
                .andExpect(status().isNotFound());

        verify(eventService, never()).assign(any(), any());
    }

    @Test
    public void shouldNotAssignUserToEventIfEventDateIsPassed() throws Exception {
        Event event = BuilderUtil.buildEventJava();

        when(eventService.getFutureEvent(event.getId())).thenThrow(TimeCollisionServiceException.class);

        mvc.perform(post("/events/" + event.getId() + "/assign"))
                .andExpect(status().isBadRequest());

        verify(eventService, never()).assign(any(), any());
    }

    @Test
    public void shouldNotRegisterExistingSubscriber() throws Exception {
        String name = "SubscriberInDatabase";

        doThrow(NameNotAvailableServiceException.class).when(userService).checkNameAvailability(name);

        mvc.perform(post("/users/register")
                .param("username", name)
                .param("password", "anyPassword"))
                .andExpect(status().isBadRequest());

        verify(userService, never()).addUser(any(User.class));
    }

    @Test
    public void shouldNotFoundIfEventIsAbsent() throws Exception {
        Event event = BuilderUtil.buildEventRuby();
        User user = BuilderUtil.buildUserGuest();

        when(eventService.getEvent(event.getId())).thenThrow(EntityNotFoundServiceException.class);
        when(userService.getUserByName(user.getLogin())).thenReturn(user);

        mvc.perform(get("/events/" + event.getId() + "/register"))
                .andExpect(status().isNotFound());

        verify(eventService).getEvent(event.getId());
        verify(userService, never()).getUserByName(user.getLogin());
        verify(visitLogService, never()).addVisitLog(any(VisitLog.class));
    }

}
