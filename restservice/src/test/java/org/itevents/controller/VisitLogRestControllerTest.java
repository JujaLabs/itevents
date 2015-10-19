package org.itevents.controller;

import org.itevents.model.Event;
import org.itevents.model.User;
import org.itevents.model.VisitLog;
import org.itevents.model.builder.VisitLogBuilder;
import org.itevents.service.EventService;
import org.itevents.service.UserService;
import org.itevents.service.VisitLogService;
import org.itevents.test_utils.BuilderUtil;
import org.junit.Test;
import org.springframework.test.annotation.DirtiesContext;

import javax.inject.Inject;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by vaa25 on 16.10.2015.
 */
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class VisitLogRestControllerTest extends AbstractControllerTest {

    @Inject
    private UserService userService;
    @Inject
    private VisitLogService visitLogService;
    @Inject
    private EventService eventService;

    @Test
    public void shouldAddVisitLogToEventJavaWithAnonymous() throws Exception {
        Event event = BuilderUtil.buildEventJava();
        User userGuest = BuilderUtil.buildUserGuest();
        VisitLog visitLog = VisitLogBuilder.aVisitLog().event(event).user(userGuest).build();

        when(eventService.getEvent(event.getId())).thenReturn(event);
        when(userService.getAuthorizedUser()).thenReturn(userGuest);
        doNothing().when(visitLogService).addVisitLog(visitLog);

        mvc.perform(get("/events/" + event.getId() + "/register"))
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

        mvc.perform(get("/events/" + event.getId() + "/register"))
                .andExpect(status().isNotFound());

        verify(eventService).getEvent(event.getId());
        verify(userService, never()).getUserByName(user.getLogin());
        verify(visitLogService, never()).addVisitLog(any(VisitLog.class));
    }
}