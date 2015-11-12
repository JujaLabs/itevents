package org.itevents.controller;

import org.junit.Before;
import org.junit.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by ramax on 11/12/15.
 */
public class UserControllerTest extends AbstractMockControllerTest<UserRestController> {

    @Before
    public void test() {
        init(UserRestController.class);
        authenticationGuest();
    }

    @Test
    public void shouldRegisterNewUser() throws Exception {

        String mail = "a@a.com";
        String pass = "password";

        mockMvc.perform(post("/users/register")
                .param("username", mail)
                .param("password", pass))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldRemoveExistingSubscriber() throws Exception {

        mockMvc.perform(delete("/users/delete"))
                .andExpect(status().isOk());
    }

}
