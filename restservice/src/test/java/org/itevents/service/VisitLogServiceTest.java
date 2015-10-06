package org.itevents.service;

import org.itevents.dao.VisitLogDao;
import org.itevents.model.Event;
import org.itevents.model.User;
import org.itevents.model.VisitLog;
import org.itevents.util.BuilderUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.text.ParseException;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;

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
    private EventService eventService;
    @Inject
    private UserService userService;
    private Event event1;
    private User user1;
    private VisitLog testVisitLog;
    @InjectMocks
    @Inject
    private VisitLogService visitLogService;
    @Mock
    private VisitLogDao visitLogDao;

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

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetVisitlog() throws Exception {
        VisitLog expectedVisitlog = BuilderUtil.buildVisitLogFirst();
        when(visitLogDao.getVisitLog(expectedVisitlog.getId())).thenReturn(expectedVisitlog);
        VisitLog returnedVisitlog = visitLogService.getVisitLog(expectedVisitlog.getId());
        verify(visitLogDao).getVisitLog(expectedVisitlog.getId());
        assertEquals(expectedVisitlog, returnedVisitlog);
    }

    @Test
    public void testGetAllVisitlogs() {
        visitLogService.getAllVisitLogs();
        verify(visitLogDao).getAllVisitLogs();
    }

    @Test
    public void testAddVisitlog() throws Exception {
        VisitLog testVisitLog = BuilderUtil.buildVisitLogTest();
        visitLogService.addVisitLog(testVisitLog);
        verify(visitLogDao).addVisitLog(testVisitLog);
    }

    @Test
    public void testRemoveVisitlogSuccess() throws ParseException {
        VisitLog expectedVisitlog = BuilderUtil.buildVisitLogTest();
        when(visitLogDao.getVisitLog(expectedVisitlog.getId())).thenReturn(expectedVisitlog);
        doNothing().when(visitLogDao).removeVisitLog(expectedVisitlog);
        VisitLog returnedVisitlog = visitLogService.removeVisitLog(expectedVisitlog);
        assertEquals(expectedVisitlog, returnedVisitlog);
    }

    @Test
    public void testRemoveVisitlogFail() throws ParseException {
        VisitLog testVisitlog = BuilderUtil.buildVisitLogTest();
        when(visitLogDao.getVisitLog(testVisitlog.getId())).thenReturn(null);
        doNothing().when(visitLogDao).removeVisitLog(testVisitlog);
        VisitLog returnedVisitlog = visitLogService.removeVisitLog(testVisitlog);
        assertNull(returnedVisitlog);
    }

    @Test
    public void testGetVisitLogByEvent() throws Exception {
        Set<VisitLog> expectedVisitlogs = BuilderUtil.buildListVisitLogJava();
        Event eventJava = BuilderUtil.buildEventJava();
        when(visitLogDao.getVisitLogsByEvent(eventJava)).thenReturn(expectedVisitlogs);
        Set<VisitLog> returnedVisitlogs = visitLogService.getVisitLogsByEvent(eventJava);
        verify(visitLogDao).getVisitLogsByEvent(eventJava);
        assertEquals(expectedVisitlogs, returnedVisitlogs);
    }
}
