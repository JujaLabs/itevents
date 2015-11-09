package org.itevents.service.transactional;

import org.itevents.dao.mybatis.mapper.EventMapper;
import org.itevents.dao.mybatis.mapper.VisitLogMapper;
import org.itevents.model.Event;
import org.itevents.model.VisitLog;
import org.itevents.parameter.FilteredEventsParameter;
import org.itevents.service.VisitLogService;
import org.itevents.service.mail.MailReminderAboutEventService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by ramax on 11/7/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:applicationContext.xml")
public class MailReminderAboutEventServiceTest {

    @InjectMocks
    @Inject
    private MailReminderAboutEventService mailReminderAboutEventService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Inject
    EventMapper eventMapper;


    public int getDaysTillEvent(){
        int result;
        // Get difference of days between today and date of event with id -1
        result = (int)(new Date().getTime()/(24*60*60*1000)) - (int)(eventMapper.getEvent(-1).getEventDate().getTime()/(24*60*60*1000));
        return -(result-1);
    }

    @Test
    public void getEventByDaysTillEventTest(){
        List<Event> eventsByDaysTillEvent = mailReminderAboutEventService.getEventsByDaysTillEvent(getDaysTillEvent());
        assertNotNull(eventsByDaysTillEvent);
    }

    @Test
    public void getVisitLogsByFilteredEvents(){
        List<VisitLog> visitLogs = mailReminderAboutEventService.getVisitLogListByDaysTillEvent(getDaysTillEvent());
        assertNotNull(visitLogs);
    }

}
