package org.itevents.mapper;

import org.itevents.model.Event;
import org.itevents.model.User;
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
    private final Event event1 = eventMapper.getEvent(1);
    @Autowired
    private UserMapper userMapper;
    private final User user1 = userMapper.getUser(1);

    @Before
    public void addVisit11() {
        visitLogMapper.addVisit(event1, user1);
    }

    @After
    public void deleteVisits() {
        visitLogMapper.removeVisits();
    }

    @Test
    @Ignore
    public void testGetVisit() throws Exception {
        VisitLog visitLog = new VisitLog(event1, new GregorianCalendar().getTime(), user1);
        List<VisitLog> expected = new ArrayList<>();
        expected.add(visitLog);
        List<VisitLog> real = visitLogMapper.getVisitsByEvent(event1);
//        real.setDate(null);
        assertEquals(expected, real);

    }


}
