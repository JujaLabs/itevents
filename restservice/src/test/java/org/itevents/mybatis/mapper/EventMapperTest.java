package org.itevents.mybatis.mapper;

import org.itevents.model.Event;
import org.itevents.model.Location;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.GregorianCalendar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by vaa25 on 22.07.2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext.xml"})
public class EventMapperTest {

    private final static int ID_0 = 0;
    private final static int ID_1 = 1;

    @Autowired
    private EventMapper eventMapper;

    @Autowired
    private CityMapper cityMapper;

    @Test
    public void testGetEvent1() throws Exception {
        double eventLatitude = 50.458585;
        double eventLongitude = 30.742017;
        int eventYear = 2015;
        int eventMonth = 7 - 1;
        int eventDay = 10;
        Date createDate = null;
        Event expectedEvent = new Event(ID_1, "Java", new GregorianCalendar(eventYear, eventMonth, eventDay).getTime(),
                createDate, "http://www.java.com.ua", "Beresteyska", new Location(eventLatitude, eventLongitude),
                "java@gmail.com", true, null, null, cityMapper.getCity(ID_1));
        Event returnedEvent = eventMapper.getEvent(ID_1);
        assertEquals(expectedEvent, returnedEvent);
    }

    @Test
    public void testGetEvent0() {
        Event returnedEvent = eventMapper.getEvent(ID_0);
        assertNull(returnedEvent);
    }
}