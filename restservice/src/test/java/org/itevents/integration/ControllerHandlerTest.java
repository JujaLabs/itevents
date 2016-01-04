package org.itevents.integration;

import org.itevents.model.Event;
import org.itevents.service.EventService;
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

import javax.inject.Inject;

import static org.mockito.Mockito.when;
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
    }

    @Test
    public void shouldNotAssignUserToEventIfEventDateIsPassed() throws Exception {
        Event event = BuilderUtil.buildEventJava();

        when(eventService.getFutureEvent(event.getId())).thenThrow(TimeCollisionServiceException.class);

        mvc.perform(post("/events/" + event.getId() + "/assign"))
                .andExpect(status().isBadRequest());
    }
}
