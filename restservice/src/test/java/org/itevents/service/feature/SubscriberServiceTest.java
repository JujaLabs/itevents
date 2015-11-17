package org.itevents.service.feature;

import org.itevents.model.User;
import org.itevents.service.UserService;
import org.itevents.test_utils.BuilderUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by alex-anakin on 17.11.2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class SubscriberServiceTest {
    @InjectMocks
    private SubscriberService subscriberService = new SubscriberServiceImpl();
    @Mock
    private UserService userService;
    private MailService mailService;

    @Test
    public void shouldSendInviteToEmail() throws Exception {
        //given
        String email = "address@mail.com";
        int id = -1;
        User testUser = BuilderUtil.buildSubscriberTest();
        when(userService.getUser(id)).thenReturn(testUser);
        //when
        String actualMessage = subscriberService.sendInvite(id, email);
        //then
        verify(mailService).sendEmail(testUser, email);

    }
}
