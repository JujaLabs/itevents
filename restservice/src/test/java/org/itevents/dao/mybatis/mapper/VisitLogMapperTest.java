package org.itevents.dao.mybatis.mapper;

import org.itevents.model.Event;
import org.itevents.model.User;
import org.itevents.model.VisitLog;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by vaa25 on 21.07.2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext.xml"})
@Transactional
public class VisitLogMapperTest {

    private final int ID_0 = 0;
    private final int ID_1 = 1;
    private final int ID_2 = 2;
    private final int ID_3 = 3;
    private final int ID_7 = 7;
    private final Date date1 = new GregorianCalendar(2016, 6, 20).getTime();
    @Inject
    private VisitLogMapper visitLogMapper;
    @Inject
    private EventMapper eventMapper;

    @Inject
    private UserMapper userMapper;
    private Event event1;
    private User user1;
    private VisitLog testVisitLog;

    @Before
    public void setup() {
        event1 = eventMapper.getEvent(ID_1);
        user1 = userMapper.getUser(ID_1);
        testVisitLog = new VisitLog(eventMapper.getEvent(ID_3), user1);
        testVisitLog.setDate(date1);
    }

    @After
    public void teardown() {
        testVisitLog = null;
        user1 = null;
        event1 = null;
    }

    @Test
    public void testGetVisitLog1() {
        VisitLog expectedVisitLog = new VisitLog(event1, user1);
        expectedVisitLog.setDate(date1);
        expectedVisitLog.setId(ID_1);
        VisitLog returnedVisitLog = visitLogMapper.getVisitLog(ID_1);
        assertEquals(expectedVisitLog, returnedVisitLog);
    }

    @Test
    public void testGetVisitLog0() throws Exception {
        assertNull(visitLogMapper.getVisitLog(ID_0));
    }

    @Test
    public void testGetVisitLogByEvent() throws Exception {
        List<VisitLog> expectedVisitLogs = new ArrayList<>();
        expectedVisitLogs.add(visitLogMapper.getVisitLog(ID_1));
        expectedVisitLogs.add(visitLogMapper.getVisitLog(ID_2));
        expectedVisitLogs.add(visitLogMapper.getVisitLog(ID_7));
        List<VisitLog> returnedVisitLogs = visitLogMapper.getVisitLogsByEvent(event1);
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
        int returnedSize = visitLogMapper.getAllVisitLogs().size();
        assertTrue(returnedSize > 0);
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
