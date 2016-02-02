package org.itevents.service.sendmail;

import org.itevents.dao.EventDao;
import org.itevents.dao.UserDao;
import org.itevents.model.Event;
import org.itevents.model.User;
import org.itevents.test_utils.BuilderUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Created by ramax on 11/7/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:applicationContext.xml")
public class MailReminderAboutEventServiceTest {

    @InjectMocks
    @Inject
    private MailReminderAboutEventService mailReminderAboutEventService;

    @Mock
    private EventDao eventDao;

    @Mock
    private UserDao userDao;

    @Mock
    private MailService mailService;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldGetUsersByEventDateAndSendMail() throws Exception {

        List<Event> events = new ArrayList<Event>();
        events.add(BuilderUtil.buildEventJava());
        when(eventDao.getEventsByDate(any(Date.class))).thenReturn(events);

        List<User> user = new ArrayList<User>();
        user.add(BuilderUtil.buildSubscriberTest());
        when(userDao.getUsersByEvent(any(Event.class))).thenReturn(user);

        doNothing().when(mailService).sendMail(anyString(), anyString());

        mailReminderAboutEventService.execute();

        verify(eventDao).getEventsByDate(any(Date.class));
        verify(userDao, times(events.size())).getUsersByEvent(any((Event.class)));
        verify(mailService, times(user.size())).sendMail(anyString(), anyString());
    }
}