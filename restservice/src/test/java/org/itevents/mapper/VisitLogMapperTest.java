package org.itevents.mapper;

import org.itevents.model.Event;
import org.itevents.model.User;
import org.itevents.model.VisitLog;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private VisitLogMapper visitLogMapper;
    @Autowired
    private EventMapper eventMapper;
    @Autowired
    private UserMapper userMapper;

    private Event event1;
    private User user1;
    private VisitLog testVisitLog;
    private Date date1;


    @Before
    public void setTemplate() {
        event1 = eventMapper.getEvent(1);
        user1 = userMapper.getUser(1);
        testVisitLog = new VisitLog(eventMapper.getEvent(3), user1);
        date1 = new GregorianCalendar(2016, 6, 20).getTime();
        testVisitLog.setDate(date1);
    }

    @After
    public void clearTemplate() {
        testVisitLog = null;
        user1 = null;
        event1 = null;
        date1 = null;
    }

    @Test
    public void testGetVisitLog1() {
        VisitLog expected = new VisitLog(event1, user1);
        expected.setDate(date1);
        expected.setId(1);

        assertEquals(expected, visitLogMapper.getVisitLog(1));
    }

    @Test
    public void testGetVisitLog0() throws Exception {
        assertNull(visitLogMapper.getVisitLog(0));
    }

    @Test
    public void testGetVisitLogByEvent() throws Exception {
        List<VisitLog> expected = new ArrayList<>();
        expected.add(visitLogMapper.getVisitLog(1));
        expected.add(visitLogMapper.getVisitLog(2));
        expected.add(visitLogMapper.getVisitLog(7));

        List<VisitLog> returned = visitLogMapper.getVisitsByEvent(event1);

        assertEquals(expected, returned);
    }


    @Test
    public void testAddVisitLog() throws Exception {
        visitLogMapper.addVisitLog(testVisitLog);

        VisitLog returned = visitLogMapper.getVisitLog(testVisitLog.getId());
        returned.setDate(date1);
        assertEquals(testVisitLog, returned);

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
