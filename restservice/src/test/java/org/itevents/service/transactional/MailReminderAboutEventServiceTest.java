package org.itevents.service.transactional;

import com.google.common.collect.Multimap;
import org.itevents.dao.EventDao;
import org.itevents.model.Event;
import org.itevents.model.User;
import org.itevents.service.mail.MailReminderAboutEventService;
import org.itevents.test_utils.BuilderUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import java.text.ParseException;
import java.util.*;

import static org.junit.Assert.assertFalse;

/**
 * Created by ramax on 11/7/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:applicationContext.xml")
public class MailReminderAboutEventServiceTest {

    @Inject
    private MailReminderAboutEventService mailReminderAboutEventService;

    @Inject
    EventDao eventDao;

    private static final int MILLISECONDS_TO_DAYS = 24 * 60 * 60 * 1000;


    private int shouldReturnDaysTillEvent() throws ParseException {
        long result;
        // Get difference of days between today and date of event with id -1
        result = (BuilderUtil.buildEventJava().getEventDate().getTime()) //day of event with id -1
                - (new Date().getTime()); //today
        return (int) (result / MILLISECONDS_TO_DAYS) + 1;
    }

    @Test
    public void shouldReturnEventByDaysTillEventTest() throws ParseException {
        List<Event> eventsByDaysTillEvent = mailReminderAboutEventService.getEventsByDaysTillEvent(shouldReturnDaysTillEvent());
        assertFalse(eventsByDaysTillEvent.isEmpty());
    }

    @Test
    public void shouldReturnUsersAndEventsByDaysTillEventTest() throws ParseException {
        Multimap<User, Event> usersAndEvents = mailReminderAboutEventService.getUsersAndEventsByDaysTillEvent(shouldReturnDaysTillEvent());
        assertFalse(usersAndEvents.isEmpty());
    }
}