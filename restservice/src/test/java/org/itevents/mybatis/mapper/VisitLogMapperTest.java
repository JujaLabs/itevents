package org.itevents.mybatis.mapper;

import org.itevents.model.Event;
import org.itevents.model.User;
import org.itevents.model.VisitLog;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by vaa25 on 21.07.2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext.xml"})
@Transactional
public class VisitLogMapperTest {

    private static VisitLogMapper visitLogMapper;
    private static EventMapper eventMapper;
    private static UserMapper userMapper;

    private static Event event1;
    private static User user1;
    private static VisitLog testVisitLog;
    private static Date date1;

    @BeforeClass
    public static void setup() {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        eventMapper = context.getBean("eventMapper", EventMapper.class);
        userMapper = context.getBean("userMapper", UserMapper.class);
        visitLogMapper = context.getBean("visitLogMapper", VisitLogMapper.class);
        event1 = eventMapper.getEvent(1);
        user1 = userMapper.getUser(1);
        testVisitLog = new VisitLog(eventMapper.getEvent(3), user1);
        date1 = new GregorianCalendar(2016, 6, 20).getTime();
        testVisitLog.setDate(date1);
    }

    @AfterClass
    public static void teardown() {
        userMapper = null;
        eventMapper = null;
        visitLogMapper = null;
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

        VisitLog returnedVisitLog = visitLogMapper.getVisitLog(1);
        assertEquals(expectedVisitLog, returnedVisitLog);
    }

    @Test
    public void testGetVisitLog0() throws Exception {
        assertNull(visitLogMapper.getVisitLog(0));
    }

    @Test
    public void testGetVisitLogByEvent() throws Exception {
        List<VisitLog> expectedVisitLogs = new ArrayList<>();
        expectedVisitLogs.add(visitLogMapper.getVisitLog(1));
        expectedVisitLogs.add(visitLogMapper.getVisitLog(2));
        expectedVisitLogs.add(visitLogMapper.getVisitLog(7));

        List<VisitLog> returnedVisitLogs = visitLogMapper.getVisitsByEvent(event1);

        assertEquals(expectedVisitLogs, returnedVisitLogs);
    }


    @Test
    public void testAddVisitLog() throws Exception {
        VisitLog expectedVisitLog = testVisitLog;
        visitLogMapper.addVisitLog(expectedVisitLog);

        VisitLog returnedVisitLog = visitLogMapper.getVisitLog(expectedVisitLog.getId());
        returnedVisitLog.setDate(date1);

        assertEquals(expectedVisitLog, returnedVisitLog);
        visitLogMapper.removeVisitLog(testVisitLog);
    }

    @Test
    public void testGetAllVisitLogs() {
        assertEquals(7, visitLogMapper.getAllVisitLogs().size());
    }

    @Test
    public void testRemoveVisitLog() {
        visitLogMapper.addVisitLog(testVisitLog);
        int expectedSize = visitLogMapper.getAllVisitLogs().size() - 1;

        visitLogMapper.removeVisitLog(testVisitLog);
        int returnedSize = visitLogMapper.getAllVisitLogs().size();

        assertEquals(expectedSize, returnedSize);
    }

}
