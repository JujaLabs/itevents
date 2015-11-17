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
        int id = -1;
        String email = "address@mail.com";

        mvc.perform(post("/users/" + id + "/invites")
                .param("email", email))
                .andExpect(status().isOk());
    }

    @Test
    public void expectBadRequestIfEmailNotValid() throws Exception {
        int id = -1;
        String email = "some_not_valid_email";

        mvc.perform(post("/users/" + id + "/invites")
                .param("email", email))
                .andExpect(status().isBadRequest());
    }

}
