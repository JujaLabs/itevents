package org.itevents.service;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.itevents.model.Event;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
        DbUnitTestExecutionListener.class })
public class EventServiceDbTest {

    @Autowired
    private EventService eventService;

    @Test
    @DatabaseSetup("events.xml")
    public void testGetEventById() {
        List<Event> events = eventService.getAllEvents();
        int expectedValue = 4;
        int actualValue = events.size();
        Assert.assertEquals(expectedValue, actualValue);
    }

}
