package org.itevents.controller;

import org.codehaus.jackson.map.ObjectMapper;
import org.itevents.controller.converter.FilterConverter;
import org.itevents.controller.wrapper.FilterWrapper;
import org.itevents.dao.model.Event;
import org.itevents.dao.model.Filter;
import org.itevents.dao.model.User;
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

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
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
        String expectedEventInJson = new ObjectMapper().writeValueAsString(event);

        when(eventService.getEvent(event.getId())).thenReturn(event);

        mockMvc.perform(get("/events/" + event.getId())
                .header("Accept", "application/json"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedEventInJson));

        verify(eventService).getEvent(event.getId());
    }

    @Test
    public void shouldAssignUserToEvent() throws Exception {
        Event event = BuilderUtil.buildEventJava();

        mockMvc.perform(post("/events/" + event.getId() + "/assign"))
                .andExpect(status().isOk());

        verify(eventService).assignAuthorizedUserToEvent(event.getId());
    }

    @Test
    public void shouldUnassignUserFromEvent() throws Exception{
        Event event = BuilderUtil.buildEventJava();
        String validUnassignReason = "test";

        mockMvc.perform(post("/events/" + event.getId() + "/unassign")
                .param("unassign_reason", validUnassignReason))
                .andExpect(status().isOk());

        verify(eventService).unassignAuthorizedUserFromEvent(event.getId(), validUnassignReason);
    }

    @Test
    public void shouldReturnUsersByEvent() throws Exception {
        Event event = BuilderUtil.buildEventJava();
        List<User> expectedUsers = new ArrayList<>();
        expectedUsers.add(BuilderUtil.buildUserAnakin());
        String expectedUsersInJson = new ObjectMapper().writeValueAsString(expectedUsers);

        when(userService.getUsersByEvent(event.getId())).thenReturn(expectedUsers);

        mockMvc.perform(get("/events/" + event.getId() + "/visitors"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedUsersInJson));

        verify(userService).getUsersByEvent(event.getId());
    }

    @Test
    public void shouldAddVisitLogToEventJavaWithAnonymous() throws Exception {
        Event event = BuilderUtil.buildEventJava();

        when(eventService.redirectToEventSite(event.getId())).thenReturn(event.getRegLink());

        mockMvc.perform(get("/events/" + event.getId() + "/register"))
                .andExpect(content().string(event.getRegLink()))
                .andExpect(status().isOk());

        verify(eventService).redirectToEventSite(event.getId());
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
