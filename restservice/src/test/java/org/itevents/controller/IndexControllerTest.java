package org.itevents.controller;

import org.junit.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class IndexControllerTest extends AbstractControllerTest {

    @Test
    public void shouldReturnIndexPage() throws Exception {
        mvc.perform(get("/"))
                .andExpect(view().name("index"))
                .andExpect(status().isOk());
    }
}
