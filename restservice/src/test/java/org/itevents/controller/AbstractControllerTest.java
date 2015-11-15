package org.itevents.controller;

import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

/**
 * Created by ramax on 11/10/15.
 */
public abstract class AbstractControllerTest {

    protected MockMvc mockMvc;

    protected AbstractControllerTest initMock(Object testObject) {
        MockitoAnnotations.initMocks(testObject);
        return this;
    }

    protected void initMvc(Object controller) {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

}
