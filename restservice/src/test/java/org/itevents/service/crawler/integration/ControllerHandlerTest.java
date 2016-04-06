package org.itevents.service.crawler.integration;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.ArrayList;
import javax.inject.Inject;
import org.itevents.dao.model.Event;
import org.itevents.dao.model.User;
import org.itevents.dao.model.VisitLog;
import org.itevents.service.EventService;
import org.itevents.service.UserService;
import org.itevents.service.VisitLogService;
import org.itevents.service.exception.ActionAlreadyDoneServiceException;
import org.itevents.service.exception.EntityAlreadyExistsServiceException;
import org.itevents.service.exception.EntityNotFoundServiceException;
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

/**
 * Created by vaa25 on 04.01.2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:applicationContext.xml",
                        "classpath:applicationContextTestAddon.xml",
                        "classpath:mvc-dispatcher-servlet.xml",
                        "classpath:spring-security.xml"})
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
        this.mvc = MockMvcBuilders.webAppContextSetup(this.context).build();
    }

    @Test
    public void shouldNotAssignUserToEventIfEventIsAbsent() throws Exception {
        int absentId = 0;

        when(this.eventService.getFutureEvent(absentId)).thenThrow(EntityNotFoundServiceException.class);

        this.mvc.perform(post("/events/" + absentId + "/assign"))
                .andExpect(status().isNotFound());

        verify(this.eventService, never()).assignUserToEvent(any(), any());
    }

    @Test
    public void shouldNotAssignUserToEventIfEventDateIsPassed() throws Exception {
        Event event = BuilderUtil.buildEventJava();

        when(this.eventService.getFutureEvent(event.getId())).thenThrow(TimeCollisionServiceException.class);

        this.mvc.perform(post("/events/" + event.getId() + "/assign"))
                .andExpect(status().isBadRequest());

        verify(this.eventService, never()).assignUserToEvent(any(), any());
    }

    @Test
    public void shouldNotRegisterExistingSubscriber() throws Exception {
        String name = "SubscriberInDatabase";
        String password = "anyPassword";

        doThrow(EntityAlreadyExistsServiceException.class).when(this.userService).addSubscriber(name, password);

        this.mvc.perform(post("/users/register")
                .param("username", name)
                .param("password", password))
                .andExpect(status().is(422));
    }

    @Test
    public void shouldNotFoundIfEventIsAbsent() throws Exception {
        Event event = BuilderUtil.buildEventRuby();
        User user = BuilderUtil.buildUserGuest();

        when(this.eventService.getEvent(event.getId())).thenThrow(EntityNotFoundServiceException.class);
        when(this.userService.getUserByName(user.getLogin())).thenReturn(user);

        this.mvc.perform(get("/events/" + event.getId() + "/register"))
                .andExpect(status().isNotFound());

        verify(this.eventService).getEvent(event.getId());
        verify(this.userService, never()).getUserByName(user.getLogin());
        verify(this.visitLogService, never()).addVisitLog(any(VisitLog.class));
    }

    @Test
    public void shouldNotFoundUserById() throws Exception {
        int absentId = 0;

        when(this.userService.getUser(absentId)).thenThrow(EntityNotFoundServiceException.class);

        this.mvc.perform(get("/users/" + absentId))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldNotAssignUserToEventIfAssigned() throws Exception {
        User user = BuilderUtil.buildUserAnakin();
        Event event = BuilderUtil.buildEventJava();
        ArrayList<Event> expectedEvents = new ArrayList<>();
        expectedEvents.add(event);

        when(this.eventService.getFutureEvent(event.getId())).thenReturn(event);
        when(this.userService.getAuthorizedUser()).thenReturn(user);
        doThrow(ActionAlreadyDoneServiceException.class)
            .when(this.eventService).assignUserToEvent(user, event);

        this.mvc.perform(post("/events/" + event.getId() + "/assign"))
                .andExpect(status().isConflict());

        reset(this.eventService);
    }

    @Test
    public void shouldNotUnassignAssignUserFromEventIfParamLengthIsNotValid() throws Exception {
        Event event = BuilderUtil.buildEventJava();
        User user = BuilderUtil.buildUserAnakin();
        ArrayList <Event> expectedEvents = new ArrayList<>();
        expectedEvents.add(event);
        String invalidParameter = "invalid";
        for (int i = 0; i <250 ; i++) {
            invalidParameter = invalidParameter.concat("s");
        }

        when(this.eventService.getEvent(event.getId())).thenReturn(event);
        when(this.userService.getAuthorizedUser()).thenReturn(user);
        when(this.eventService.getEventsByUser(user)).thenReturn(expectedEvents);

        this.mvc.perform(post("/events/" + event.getId() + "/unassign")
                .param("unassign_reason", invalidParameter))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldUnassignUserIfParameterIsEmpty() throws Exception {
        Event event = BuilderUtil.buildEventJava();
        User user = BuilderUtil.buildUserAnakin();
        ArrayList <Event> expectedEvents = new ArrayList<>();
        expectedEvents.add(event);
        String validParameter = "";

        when(this.eventService.getEvent(event.getId())).thenReturn(event);
        when(this.userService.getAuthorizedUser()).thenReturn(user);
        when(this.eventService.getEventsByUser(user)).thenReturn(expectedEvents);

        this.mvc.perform(post("/events/" + event.getId() + "/unassign")
                .param("unassign_reason", validParameter))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldExpect404IfOtpNotValid() throws Exception {
        String otp = "NotValidOtp";

        doThrow(EntityNotFoundServiceException.class)
            .when(this.userService).activateUserWithOtp(otp);

        this.mvc.perform(get("/users/activate/" + otp))
                .andExpect(status().isNotFound());
    }
}