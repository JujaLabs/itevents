package org.itevents.service.sendmail;

import org.itevents.dao.model.Event;
import org.itevents.dao.model.Filter;
import org.itevents.dao.model.User;
import org.itevents.service.EventService;
import org.itevents.service.FilterService;
import org.itevents.service.UserService;
import org.itevents.test_utils.BuilderUtil;
import org.itevents.util.mail.MailBuilderUtil;
import org.itevents.util.mail.MailBuilderUtilException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import java.util.List;

import static org.mockito.Mockito.*;
/**
 * Created by ramax on 11/6/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:applicationContext.xml"})
public class MailNotificationServiceTest {

    @InjectMocks
    @Inject
    private MailNotificationService mailNotificationEventService;

    @Mock
    private MailService mailService;

    @Mock
    private UserService userService;

    @Mock
    private EventService eventService;

    @Mock
    private FilterService filterService;

    @Mock
    private MailBuilderUtil mailBuilderUtil;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void performNotifyTest() throws Exception {
        List<User> users = BuilderUtil.buildAllUser();
        users.forEach(a->a.setSubscribed(true));
        List<Event> events = BuilderUtil.buildEventsForMailUtilTest();
        Filter kievFilter = BuilderUtil.buildKyivFilter();

        when(userService.getSubscribedUsers()).thenReturn(users);
        when(eventService.getFilteredEventsWithRating(any(Filter.class))).thenReturn(events);
        doNothing().when(mailService).sendMail(anyString(), anyString());
        when(filterService.getLastFilterByUser(any(User.class))).thenReturn(kievFilter);

        mailNotificationEventService.performNotify();

        verify(userService).getSubscribedUsers();
        verify(mailService, times(users.size())).sendMail(anyString(), anyString());
        verify(eventService, times(users.size())).getFilteredEventsWithRating(any(Filter.class));
        verify(filterService, times(users.size())).getLastFilterByUser(any(User.class));
    }

    @Test(expected = NotificationServiceException.class)
    public void shouldThrowNotificationServiceException() throws Exception {
        List<User> users = BuilderUtil.buildAllUser();
        users.forEach(a->a.setSubscribed(true));
        List<Event> events = BuilderUtil.buildEventsForMailUtilTest();
        Filter kievFilter = BuilderUtil.buildKyivFilter();

        when(userService.getSubscribedUsers()).thenReturn(users);
        when(eventService.getFilteredEventsWithRating(any(Filter.class))).thenReturn(events);
        when(filterService.getLastFilterByUser(any(User.class))).thenReturn(kievFilter);
        doThrow(MailBuilderUtilException.class).when(mailBuilderUtil).buildHtmlFromEventsList(events);

        mailNotificationEventService.performNotify();
    }
}
