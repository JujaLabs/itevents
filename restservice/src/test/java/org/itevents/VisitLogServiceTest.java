package org.itevents;

import org.itevents.mybatis.mapper.UserMapper;
import org.itevents.model.Event;
import org.itevents.model.User;
import org.itevents.model.VisitLog;
import org.itevents.service.EventService;
import org.itevents.service.VisitLogService;
import org.itevents.service.VisitLogServiceImpl;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class VisitLogServiceTest {

    private static VisitLogService visitLogService;
    private static Event event1;
    private static User user1;
    private static VisitLog testVisitLog;
    private static Date date1;

    @BeforeClass
    public static void setup() {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        visitLogService = context.getBean("visitLogService", VisitLogServiceImpl.class);
        EventService eventService = context.getBean("eventService", EventService.class);
        event1 = eventService.getEvent(1);
        user1 = context.getBean("userMapper", UserMapper.class).getUser(1);
        testVisitLog = new VisitLog(eventService.getEvent(3), user1);
        date1 = new GregorianCalendar(2016, 6, 20).getTime();
        testVisitLog.setDate(date1);
    }

    @AfterClass
    public static void teardown() {
        visitLogService = null;
        event1 = null;
        user1 = null;
        testVisitLog = null;
        date1 = null;
    }

    @Test
    public void testGetVisitLog1() {
        VisitLog expectedVisitLog = new VisitLog(event1, user1);
        expectedVisitLog.setDate(date1);
        expectedVisitLog.setId(1);

        VisitLog returnedVisitLog = visitLogService.getVisitLog(1);
        assertEquals(expectedVisitLog, returnedVisitLog);
    }

    @Test
    public void testGetVisitLog0() throws Exception {
        assertNull(visitLogService.getVisitLog(0));
    }

    @Test
    public void testGetVisitLogByEvent() throws Exception {
        List<VisitLog> expectedVisitLogs = new ArrayList<>();
        expectedVisitLogs.add(visitLogService.getVisitLog(1));
        expectedVisitLogs.add(visitLogService.getVisitLog(2));
        expectedVisitLogs.add(visitLogService.getVisitLog(7));

        List<VisitLog> returnedVisitLogs = visitLogService.getVisitsByEvent(event1);

        assertEquals(expectedVisitLogs, returnedVisitLogs);
    }


    @Test
    public void testAddVisitLog() throws Exception {
        VisitLog expectedVisitLog = testVisitLog;
        visitLogService.addVisitLog(expectedVisitLog);

        VisitLog returnedVisitLog = visitLogService.getVisitLog(expectedVisitLog.getId());
        returnedVisitLog.setDate(date1);

        assertEquals(expectedVisitLog, returnedVisitLog);
        visitLogService.removeVisitLog(testVisitLog);
    }

    @Test
    public void testGetAllVisitLogs() {
        assertEquals(7, visitLogService.getAllVisitLogs().size());
    }

    @Test
    public void testRemoveVisitLogSuccess() {
        VisitLog expectedVisitLog = testVisitLog;

        visitLogService.addVisitLog(expectedVisitLog);
        VisitLog returnedVisitLog = visitLogService.removeVisitLog(expectedVisitLog);
        returnedVisitLog.setDate(date1);

        assertEquals(expectedVisitLog, returnedVisitLog);
    }

    @Test
    public void testRemoveVisitLogSuccessFail() {
        assertNull(visitLogService.removeVisitLog(testVisitLog));
    }
}
