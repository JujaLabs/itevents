package org.itevents.mybatis.mapper;

import org.itevents.model.Event;
import org.itevents.model.Location;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by vaa25 on 22.07.2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext.xml"})
@Transactional
public class EventMapperTest {

    private final int ID_0 = 0;
    private final int ID_1 = 1;
    private final int ID_4 = 4;
    private final int ID_8 = 8;
    private final int ID_9 = 9;

    @Inject
    private EventMapper eventMapper;

    @Inject
    private CityMapper cityMapper;

    @Inject
    private TechnologyMapper technologyMapper;

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
                "java@gmail.com", true, null, null, cityMapper.getCity(ID_1), technologyMapper.getTechnologiesByEventId(ID_1));
        Event returnedEvent = eventMapper.getEvent(ID_1);
        assertEquals(expectedEvent, returnedEvent);
    }

    @Test
    public void testGetEvent0() {
        Event returnedEvent = eventMapper.getEvent(ID_0);
        assertNull(returnedEvent);
    }
}