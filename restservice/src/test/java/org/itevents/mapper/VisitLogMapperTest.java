package org.itevents.mapper;

import org.itevents.model.Event;
import org.itevents.model.VisitLog;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by vaa25 on 21.07.2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext.xml"})
public class VisitLogMapperTest {

    @Autowired
    private VisitLogMapper visitLogMapper;

    @Autowired
    private EventMapper eventMapper;

    @Autowired
    private UserMapper userMapper;

    @Before
    public void addVisit11() {
        int eventId = 1;
        int userId = 1;
        visitLogMapper.addVisit(eventId, userId);
    }

    @After
    public void deleteVisits() {
        visitLogMapper.deleteVisits();
    }

    @Test
    @Ignore
    public void testGetVisit() throws Exception {
        Event event = eventMapper.getEvent(1);
        VisitLog visitLog = new VisitLog(1, event, new GregorianCalendar().getTime(), userMapper.getUserById(1));
        List<VisitLog> expected = new ArrayList<>();
        expected.add(visitLog);
        List<VisitLog> real = visitLogMapper.getVisitsByEvent(event.getId());
//        real.setDate(null);
        assertEquals(expected, real);

    }


}
