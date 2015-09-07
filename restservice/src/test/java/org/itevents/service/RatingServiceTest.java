package org.itevents.service;

import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.itevents.model.Location;
import org.junit.Assert;
import org.itevents.model.Event;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext.xml"})
@Transactional
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionDbUnitTestExecutionListener.class})
public class RatingServiceTest {



    @Inject
    private UserService userService;
    @Inject
    private RatingService ratingService;

    @Test
    @DatabaseSetup("classpath:dbunit/initialEvents.xml")
    public void testChooseMostPopularEventsForUser(){
        int expectedSize = 4;

        List<Event> returnedEvents = ratingService.chooseMostPopularEventsForUser(userService.getUser(1));
        Assert.assertEquals(expectedSize, returnedEvents.size());
    }
}
