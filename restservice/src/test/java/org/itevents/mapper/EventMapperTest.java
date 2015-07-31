package org.itevents.mapper;

import org.itevents.model.Event;
import org.itevents.model.Location;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.GregorianCalendar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by vaa25 on 22.07.2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext.xml"})
public class EventMapperTest {
    @Autowired
    private EventMapper eventMapper;

    @Autowired
    private CurrencyMapper currencyMapper;

    @Autowired
    private CityMapper cityMapper;

    @Test
    public void testGetEvent1() throws Exception {
        Event expectedEvent = new Event(1, "Java", new GregorianCalendar(2015, 6, 10).getTime(), null,
                "http://www.java.com.ua", "Beresteyska", new Location(50.458585, 30.742017), "java@gmail.com", 0,
                currencyMapper.getCurrency(1), cityMapper.getCity(1));
        Event returnedEvent = eventMapper.getEvent(1);
        assertEquals(expectedEvent, returnedEvent);
    }

    @Test
    public void testGetEvent0() {
        Event returnedEvent = eventMapper.getEvent(0);
        assertNull(returnedEvent);
    }
}