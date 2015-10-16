package org.itevents.controller;

import org.junit.Ignore;
import org.junit.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class IndexControllerTest extends AbstractControllerTest {

    @Test
    public void shouldReturnIndexPage() throws Exception {
        mvc.perform(get("/"))
                .andExpect(view().name("index"))
                .andExpect(status().isOk());
    }

    @Ignore  //Ignore because of corrupting database table visit_log
    @Test
    public void testRedirect() throws Exception {
        mvc.perform(get("/events/1/register"))
                .andExpect(redirectedUrl("http://www.java.com.ua"))
                .andExpect(status().is3xxRedirection());
    }
}
