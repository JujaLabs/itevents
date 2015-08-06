package org.itevents.service;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import org.itevents.model.Event;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("../../../spring-test-context.xml")
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
        DbUnitTestExecutionListener.class })
public class EventServiceDbTest {

    @Autowired
    private EventService eventService;

    @Test
    @DatabaseSetup("../../../dbunit/initialEvents.xml")
    public void testGetAllEvents() {
        List<Event> events = eventService.getAllEvents();
        int expectedValue = 4;
        int actualValue = events.size();
        Assert.assertEquals(expectedValue, actualValue);
    }

    @Test
    @DatabaseSetup(value = "../../../dbunit/initialEvents.xml")
    @ExpectedDatabase(value = "../../../dbunit/expectedEvents.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
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
