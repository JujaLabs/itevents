package org.itevents.service.transactional;

import com.google.common.collect.Multimap;
import org.itevents.dao.EventDao;
import org.itevents.model.Event;
import org.itevents.model.User;
import org.itevents.service.mail.MailReminderAboutEventService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

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


    private int getDaysTillEventTest() {
        long result;
        // Get difference of days between today and date of event with id -1
        result = (eventDao.getEvent(-1).getEventDate().getTime()) //day of event with id -1
                - (new Date().getTime()); //today
        return (int) (result / MILLISECONDS_TO_DAYS) + 1;
    }

    @Test
    public void getEventByDaysTillEventTest() {
        List<Event> eventsByDaysTillEvent = mailReminderAboutEventService.getEventsByDaysTillEvent(getDaysTillEventTest());
        assertFalse(eventsByDaysTillEvent.isEmpty());
    }

    @Test
    public void getUsersAndEventsByDaysTillEventTest() {
        Multimap<User, Event> usersAndEvents = mailReminderAboutEventService.getUsersAndEventsByDaysTillEvent(getDaysTillEventTest());
        assertFalse(usersAndEvents.isEmpty());
    }
}