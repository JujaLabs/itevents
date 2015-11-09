package org.itevents.controller;

import org.itevents.service.SubscriberService;
import org.junit.Test;

import javax.inject.Inject;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Alex Anakin
 * on 09.11.2015 20:40.
 */
public class SubscriberControllerTest extends AbstractControllerTest {

    @Inject
    private SubscriberService subscriberService;

    @Test
    public void shouldReceivePostWithInvitingFriends() throws Exception {

        int id = 1;
        String emails = "a@a.com";

        mvc.perform(post("/user/" + id + "/invite")
                .param("emails", emails))
                .andExpect(status().isOk());

        verify(subscriberService).inviteFriends(id, emails);
    }
}
