package org.itevents.service;

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
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext.xml"})
@Transactional
public class VisitLogServiceTest {

    private final int ID_0 = 0;
    private final int ID_1 = 1;
    private final int ID_2 = 2;
    private final int ID_3 = 3;
    private final int ID_7 = 7;
    private final int SIZE_7 = 7;
    private final Date date1 = new GregorianCalendar(2016, 6, 20).getTime();
    @Inject
    private VisitLogService visitLogService;
    @Inject
    private EventService eventService;
    @Inject
    private UserService userService;
    private Event event1;
    private User user1;
    private VisitLog testVisitLog;

    @Before
    public void setup() {
        event1 = eventService.getEvent(ID_1);
        user1 = userService.getUser(ID_1);
        testVisitLog = new VisitLog(eventService.getEvent(ID_3), user1);
        testVisitLog.setDate(date1);
    }

    @After
    public void teardown() {
        event1 = null;
        user1 = null;
        testVisitLog = null;
    }

    @Test
    public void testGetVisitLog1() {
        VisitLog expectedVisitLog = new VisitLog(event1, user1);
        expectedVisitLog.setDate(date1);
        expectedVisitLog.setId(ID_1);
        VisitLog returnedVisitLog = visitLogService.getVisitLog(ID_1);
        assertEquals(expectedVisitLog, returnedVisitLog);
    }

    @Test
    public void testGetVisitLog0() throws Exception {
        VisitLog returnedVisitLog = visitLogService.getVisitLog(ID_0);
        assertNull(returnedVisitLog);
    }

    @Test
    public void testGetVisitLogByEvent() throws Exception {
        List<VisitLog> expectedVisitLogs = new ArrayList<>();
        expectedVisitLogs.add(visitLogService.getVisitLog(ID_1));
        expectedVisitLogs.add(visitLogService.getVisitLog(ID_2));
        expectedVisitLogs.add(visitLogService.getVisitLog(ID_7));
        Set<VisitLog> returnedVisitLogs = visitLogService.getVisitLogsByEvent(event1);
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
        int expectedSize = SIZE_7;
        int returnedSize = visitLogService.getAllVisitLogs().size();
        assertEquals(expectedSize, returnedSize);
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
    public void testRemoveVisitLogFail() {
        VisitLog returnedVisitLog = visitLogService.removeVisitLog(testVisitLog);
        assertNull(returnedVisitLog);
    }
}
