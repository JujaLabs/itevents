package org.itevents.controller;

import org.junit.Test;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Alex Anakin
 * on 09.11.2015 20:40.
 */
public class SubscriberControllerTest extends AbstractControllerTest {

    @Test
    public void shouldReceivePostWithInvitingFriends() throws Exception {
        int id = 1;
        String email = "a@a.com";

        mvc.perform(post("/users/" + id + "/invites")
                .param("email", email))
                .andExpect(status().isOk());
    }

    @Test
    public void expectBadRequestIfEmailsNull() throws Exception {
        int id = 1;
        String email = null;

        mvc.perform(post("/users/" + id + "/invites")
                .param("email", email))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void expectInvalidEmailExceptionWhenEmailIsNotValid() throws Exception {
        int id = 1;
        String email = "email";

        mvc.perform(post("/users/" + id + "/invites")
                .param("email", email))
                .andExpect(status().isBadRequest());
    }
}
