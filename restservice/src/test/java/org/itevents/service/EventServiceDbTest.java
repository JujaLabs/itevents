package org.itevents.service;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import org.itevents.AbstractDbTest;
import org.itevents.model.Event;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class EventServiceDbTest extends AbstractDbTest {

    @Autowired
    private EventService eventService;

    @Test
    @DatabaseSetup("file:src/test/resources/dbunit/initialEvents.xml")
    public void testGetAllEvents() {
        List<Event> events = eventService.getAllEvents();
        int expectedValue = 4;
        int actualValue = events.size();
        Assert.assertEquals(expectedValue, actualValue);
    }

    @Test
    @DatabaseSetup(value = "file:src/test/resources/dbunit/initialEvents.xml")
    @ExpectedDatabase(value = "file:src/test/resources/dbunit/expectedEvents.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testAddEvent() {
        Event event = new Event();
        event.setTitle("Front End");
        Calendar calendar = new GregorianCalendar(2015,11,5,14,0,0);
        event.setEventDate(calendar.getTime());
        event.setRegLink("http://www.frontend.com.ua");
        event.setAddress("Akademgorodok");
        event.setContact("frontend@gmail.com");
        eventService.addEvent(event);
    }
}
