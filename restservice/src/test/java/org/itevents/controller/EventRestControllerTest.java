package org.itevents.controller;

import org.codehaus.jackson.map.ObjectMapper;
import org.itevents.controller.converter.FilterConverter;
import org.itevents.controller.wrapper.FilterWrapper;
import org.itevents.dao.model.Event;
import org.itevents.dao.model.Filter;
import org.itevents.dao.model.User;
import org.itevents.dao.model.VisitLog;
import org.itevents.dao.model.builder.VisitLogBuilder;
import org.itevents.service.EventService;
import org.itevents.service.UserService;
import org.itevents.service.VisitLogService;
import org.itevents.test_utils.BuilderUtil;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class EventRestControllerTest extends AbstractControllerTest {

    @Mock
    private EventService eventService;
    @Mock
    private UserService userService;
    @Mock
    private VisitLogService visitLogService;
    @Mock
    private FilterConverter filterConverter;
    @InjectMocks
    private EventRestController eventRestController;

    @Before
    public void init() {
        super.initMock(this);
        super.initMvc(eventRestController);
    }

    @Test
    public void shouldFindEventById() throws Exception {
        Event event = BuilderUtil.buildEventJava();

        when(eventService.getEvent(event.getId())).thenReturn(event);

        mockMvc.perform(get("/events/" + event.getId()))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldAssignUserToEvent() throws Exception {
        Event event = BuilderUtil.buildEventJava();
        User user = BuilderUtil.buildSubscriberTest();

        when(eventService.getFutureEvent(event.getId())).thenReturn(event);
        when(userService.getAuthorizedUser()).thenReturn(user);

        mockMvc.perform(post("/events/" + event.getId() + "/assign"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldUnassignUserFromEvent() throws Exception{
        Event event = BuilderUtil.buildEventJava();
        User user = BuilderUtil.buildUserAnakin();
        String validUnassignReason = "test";

        when(eventService.getFutureEvent(event.getId())).thenReturn(event);
        when(userService.getAuthorizedUser()).thenReturn(user);

        mockMvc.perform(post("/events/" + event.getId() + "/unassign")
                .param("unassign_reason", validUnassignReason))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnUsersByEvent() throws Exception {
        Event event = BuilderUtil.buildEventJava();
        List<User> expectedUsers = new ArrayList<>();
        expectedUsers.add(BuilderUtil.buildUserAnakin());
        String expectedUsersInJson = new ObjectMapper().writeValueAsString(expectedUsers);

        when(eventService.getEvent(event.getId())).thenReturn(event);
        when(userService.getUsersByEvent(event)).thenReturn(expectedUsers);

        mockMvc.perform(get("/events/" + event.getId() + "/visitors"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedUsersInJson));
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
    }

    @Test
    public void shouldGetFilteredEvents() throws Exception {
        FilterWrapper filterWrapperWithFreeJavaEventsInKyivAtWeek
                = BuilderUtil.buildFilterWrapperWithFreeJavaEventsInKyivAtWeek();
        Filter filterWithFreeJavaEventsInKyivAtWeek
                = BuilderUtil.buildFilterWithFreeJavaEventsInKyivAtWeek();
        List<Event> events = Arrays.asList(BuilderUtil.buildFreeKyivJavaEvent());
        String eventsInJson = new ObjectMapper().writeValueAsString(events);

        when(filterConverter.toFilter(filterWrapperWithFreeJavaEventsInKyivAtWeek))
                .thenReturn(filterWithFreeJavaEventsInKyivAtWeek);
        when(eventService.getFilteredEvents(filterWithFreeJavaEventsInKyivAtWeek))
                .thenReturn(events);

        mockMvc.perform(get("/events")
                .param("page", "1")
                .param("itemsPerPage", "10")
                .param("cityId", "-1")
                .param("free", "true")
                .param("latitude", "50.4505")
                .param("longitude", "30.523")
                .param("radius", "10")
                .param("technologyTags", "Java")
                .param("rangeInDays", "7"))
                .andExpect(content().json(eventsInJson))
                .andExpect(status().isOk());

        verify(filterConverter).toFilter(filterWrapperWithFreeJavaEventsInKyivAtWeek);
        verify(eventService).getFilteredEvents(filterWithFreeJavaEventsInKyivAtWeek);
    }
}
