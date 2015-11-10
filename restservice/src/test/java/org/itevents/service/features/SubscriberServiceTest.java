package org.itevents.service.features;

import org.itevents.service.transactional.MyBatisUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;

/**
 * Created by alex-anakin on 09.11.2015.
 */

@RunWith(MockitoJUnitRunner.class)
public class SubscriberServiceTest {

    @InjectMocks
    private SubscriberService subscriberService;
    @Mock
    private MyBatisUserService myBatisUserService;


    @Test
    public void testInviteFriends() throws Exception {
        String emails = "a@a.com";
        String expectedMessage = "E-mails were invited: " + emails;
        int id = 1;
        String actualMessage = subscriberService.inviteFriends(id, emails);
        verify(myBatisUserService).getUser(id);
        assertThat(actualMessage, is(expectedMessage));


    }
}