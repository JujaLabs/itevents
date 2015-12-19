package org.itevents.service.transactional;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by ramax on 11/7/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:applicationContext.xml")
public class MailReminderAboutEventServiceTest {

    @Inject
    private MailReminderAboutEventService mailReminderAboutEventService;

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
        List<Event> expectedEvents = new ArrayList<Event>();
        expectedEvents.add(BuilderUtil.buildEventJava());
        List <Event> returnedEvents = mailReminderAboutEventService.getEventsByDaysTillEvent(shouldReturnDaysTillEvent());
        assertEquals(expectedEvents,returnedEvents);
    }

    @Test
    public void shouldReturnUsersAndEventsByDaysTillEventTest() throws ParseException {
        Multimap<User, Event> expectedUsersAndEvents = HashMultimap.create();
        Multimap<User, Event> returnedUsersAndEvents = mailReminderAboutEventService.getUsersAndEventsByDaysTillEvent(shouldReturnDaysTillEvent());
        expectedUsersAndEvents.put(BuilderUtil.buildUserGuest(), BuilderUtil.buildEventJava());
        expectedUsersAndEvents.put(BuilderUtil.buildUserAnakin(), BuilderUtil.buildEventJava());
        assertEquals(expectedUsersAndEvents,returnedUsersAndEvents);
    }
}