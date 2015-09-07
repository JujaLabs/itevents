package org.itevents.service;

import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import org.junit.Assert;
import org.itevents.model.Event;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import org.itevents.mybatis.mapper.dbunit.SpatialAwareFlatXmlDataSetLoader;
import javax.inject.Inject;
import java.util.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext.xml"})
@Transactional
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionDbUnitTestExecutionListener.class,})
@DbUnitConfiguration(databaseConnection = "org.itevents.mybatis.mapper.dbunit.GeoDBDatabaseConnection",
        dataSetLoader = SpatialAwareFlatXmlDataSetLoader.class)
public class RatingServiceTest {

    @Inject
    private UserService userService;
    @Inject
    private RatingService ratingService;

    @Test
    @DatabaseSetup("classpath:dbunit/initialEvents.xml")
    public void testChooseMostPopularEventsForUser(){
        int expectedSize = 4;
        List<Event> returnedEvents = ratingService.chooseMostPopularEventsForUser(userService.getUser(4));
        Assert.assertEquals(expectedSize, returnedEvents.size());
    }
}
