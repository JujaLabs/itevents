package org.itevents.controller;

import org.itevents.model.Event;
import org.itevents.model.User;
import org.itevents.model.VisitLog;
import org.itevents.model.builder.VisitLogBuilder;
import org.itevents.service.EventService;
import org.itevents.service.UserService;
import org.itevents.service.VisitLogService;
import org.itevents.test_utils.BuilderUtil;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by vaa25 on 16.10.2015.
 */
public class VisitLogRestControllerTest extends AbstractControllerSecurityTest {

    @Mock
    private UserService userService;
    @Mock
    private VisitLogService visitLogService;
    @Mock
    private EventService eventService;
    @InjectMocks
    private VisitLogRestController visitLogRestController;

    @Before
    public void setup() {
        initMock(this);
        initMvc(visitLogRestController);
        authenticationGuest();
    }

    @Test
    public void shouldAddVisitLogToEventJavaWithAnonymous() throws Exception {
        Event event = BuilderUtil.buildEventJava();
        User userGuest = BuilderUtil.buildUserGuest();
        VisitLog visitLog = VisitLogBuilder.aVisitLog().event(event).user(userGuest).build();

        when(eventService.getEvent(event.getId())).thenReturn(event);
        when(userService.getAuthorizedUser()).thenReturn(userGuest);
        doNothing().when(visitLogService).addVisitLog(visitLog);

        mockMvc.perform(get("/events/" + event.getId() + "/register"))
                .andExpect(content().string(event.getRegLink()))
                .andExpect(status().isOk());

        verify(eventService).getEvent(event.getId());
        verify(userService).getAuthorizedUser();
        verify(visitLogService).addVisitLog(any(VisitLog.class));
    }

    @Test
    public void shouldNotFoundIfEventIsAbsent() throws Exception {
        Event event = BuilderUtil.buildEventRuby();
        User user = BuilderUtil.buildUserGuest();
        VisitLog visitLog = VisitLogBuilder.aVisitLog().event(event).user(user).build();

        when(eventService.getEvent(event.getId())).thenReturn(null);
        when(userService.getUserByName(user.getLogin())).thenReturn(user);
        doNothing().when(visitLogService).addVisitLog(visitLog);

        mockMvc.perform(get("/events/" + event.getId() + "/register"))
                .andExpect(status().isNotFound());

        verify(eventService).getEvent(event.getId());
        verify(userService, never()).getUserByName(user.getLogin());
        verify(visitLogService, never()).addVisitLog(any(VisitLog.class));
    }
}