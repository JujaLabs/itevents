package org.itevents.integration;

import org.codehaus.jackson.map.ObjectMapper;
import org.itevents.dao.model.Event;
import org.itevents.service.EventService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by ramax on 3/10/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:applicationContext.xml",
                        "classpath:applicationContextTestAddon.xml",
                        "classpath:mvc-dispatcher-servlet.xml",
                        "classpath:spring-security.xml"})
@WebAppConfiguration
public class ControllerTest {
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
    public void shouldGetJsonFormatEvent() throws Exception {
        Event event = BuilderUtil.buildEventJava();
        String expectedEventInJson = new ObjectMapper().writeValueAsString(event);

        when(eventService.getEvent(event.getId())).thenReturn(event);

        mvc.perform(get("/events/" + event.getId()))
                .andExpect(content().json(expectedEventInJson))
                .andExpect(status().isOk());
    }
}
