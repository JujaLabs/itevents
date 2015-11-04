package org.itevents.controller;

import org.itevents.model.Event;
import org.itevents.service.EventService;
import org.itevents.service.exception.EventNotFoundServiceException;
import org.itevents.test_utils.BuilderUtil;
import org.junit.Test;
import org.springframework.test.annotation.DirtiesContext;

import javax.inject.Inject;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by vaa25 on 16.10.2015.
 */
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class EventRestControllerTest extends AbstractControllerTest {

    @Inject
    private EventService eventService;

    @Test
    public void shouldFindEventJava() throws Exception {
        Event event = BuilderUtil.buildEventJava();

        when(eventService.getEvent(event.getId())).thenReturn(event);

        mvc.perform(get("/events/" + event.getId()))
//                .andExpect(content().xml())
                .andExpect(status().isOk());

        verify(eventService).getEvent(event.getId());
    }

    @Test
    public void shouldReturn400WhenEventIsAbsent() throws Exception {
        Event event = BuilderUtil.buildEventRuby();

        when(eventService.getEvent(event.getId())).thenThrow(EventNotFoundServiceException.class);

        mvc.perform(get("/events/" + event.getId()))
                .andExpect(status().isBadRequest());

        verify(eventService).getEvent(event.getId());
    }
}