package org.itevents.dao.mybatis.mapper;

import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import org.itevents.AbstractDbTest;
import org.itevents.model.Event;
import org.itevents.model.Technology;
import org.itevents.parameter.FilteredEventsParameter;
import org.itevents.service.converter.EventConverter;
import org.itevents.util.BuilderUtil;
import org.itevents.wrapper.EventWrapper;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import javax.inject.Inject;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by vaa25 on 22.07.2015.
 */

public class EventMapperTest extends AbstractDbTest {

    private final String TEST_PATH = PATH + "EventMapperTest/";
    @Inject
    private EventMapper eventMapper;

    @Test
    @DatabaseSetup(value = TEST_PATH + "EventMapperTest_initial.xml", type = DatabaseOperation.REFRESH)
    @DatabaseTearDown(value = TEST_PATH + "EventMapperTest_initial.xml", type = DatabaseOperation.DELETE_ALL)
    public void testGetEventSuccess() throws Exception {
        Event expectedEvent = BuilderUtil.buildEventJava();
        Event returnedEvent = eventMapper.getEvent(ID_1);
        assertEquals(expectedEvent, returnedEvent);
    }

    @Test
    @DatabaseSetup(value = TEST_PATH + "EventMapperTest_initial.xml", type = DatabaseOperation.REFRESH)
    @DatabaseTearDown(value = TEST_PATH + "EventMapperTest_initial.xml", type = DatabaseOperation.DELETE_ALL)
    public void testGetEventFail() {
        Event returnedEvent = eventMapper.getEvent(ID_0);
        assertNull(returnedEvent);
    }

    @Test
    @DatabaseSetup(value = TEST_PATH + "EventMapperTest_initial.xml", type = DatabaseOperation.REFRESH)
    @DatabaseTearDown(value = TEST_PATH + "EventMapperTest_initial.xml", type = DatabaseOperation.DELETE_ALL)
    public void testGetAllEvents() throws ParseException {
        int expectedSize = 7;
        int returnedSize = eventMapper.getAllEvents().size();
        Assert.assertEquals(expectedSize, returnedSize);
    }

    @Test
    @DatabaseSetup(value = TEST_PATH + "testRemoveEvent_initial.xml", type = DatabaseOperation.REFRESH)
    @ExpectedDatabase(value = TEST_PATH + "EventMapperTest_initial.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    @DatabaseTearDown(value = TEST_PATH + "EventMapperTest_initial.xml", type = DatabaseOperation.DELETE_ALL)
    public void testRemoveEvent() throws ParseException {
        Event expectedEvent = BuilderUtil.buildEventRuby();
        eventMapper.removeEvent(expectedEvent);
    }

    @Test
    @DatabaseSetup(value = TEST_PATH + "EventMapperTest_initial.xml", type = DatabaseOperation.REFRESH)
    @ExpectedDatabase(value = TEST_PATH + "testAddEvent_expected.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    @DatabaseTearDown(value = TEST_PATH + "EventMapperTest_initial.xml", type = DatabaseOperation.DELETE_ALL)
    public void testAddEvent() throws ParseException {
        Event addingEvent = BuilderUtil.buildEventRuby();
        eventMapper.addEvent(addingEvent);
    }

    @Ignore
    @Test
    @DatabaseSetup(value = TEST_PATH + "EventMapperTest_initial.xml", type = DatabaseOperation.REFRESH)
    @DatabaseTearDown(value = TEST_PATH + "EventMapperTest_initial.xml", type = DatabaseOperation.DELETE_ALL)
    public void testGetFilteredEventsEmpty() {
        FilteredEventsParameter parameter = getEmptyFilteredEventsParameter();
        int expectedSize = 7;
        int returnedSize = eventMapper.getFilteredEvents(parameter).size();
        assertEquals(expectedSize, returnedSize);
    }

    private FilteredEventsParameter getEmptyFilteredEventsParameter() {
        FilteredEventsParameter parameter = new FilteredEventsParameter();
        parameter.setLimit(10);
        parameter.setOffset(0);
        return parameter;
    }

    @Ignore
    @Test
    @DatabaseSetup(value = TEST_PATH + "EventMapperTest_initial.xml", type = DatabaseOperation.REFRESH)
    @DatabaseTearDown(value = TEST_PATH + "EventMapperTest_initial.xml", type = DatabaseOperation.DELETE_ALL)
    public void testGetFilteredEventsPage3ItemsPerPage2() throws ParseException {
        EventWrapper wrapper = new EventWrapper();
        wrapper.setPage(3);
        wrapper.setItemsPerPage(2);
        FilteredEventsParameter parameter = new EventConverter().convert(wrapper);

        List<Event> expectedEvents = new ArrayList<>();
        expectedEvents.add(BuilderUtil.buildEventObjectiveC());
        expectedEvents.add(BuilderUtil.buildEventCsharp());

        List<Event> returnedEvents = eventMapper.getFilteredEvents(parameter);
        assertEquals(expectedEvents, returnedEvents);
    }

    @Ignore
    @Test
    @DatabaseSetup(value = TEST_PATH + "EventMapperTest_initial.xml", type = DatabaseOperation.REFRESH)
    @DatabaseTearDown(value = TEST_PATH + "EventMapperTest_initial.xml", type = DatabaseOperation.DELETE_ALL)
    public void testGetFilteredEventsPage30ItemsPerPageMinus2() {
        EventWrapper wrapper = new EventWrapper();
        wrapper.setPage(30);
        wrapper.setItemsPerPage(-2);
        FilteredEventsParameter parameter = new EventConverter().convert(wrapper);

        List<Event> expectedEvents = new ArrayList<>();

        List<Event> returnedEvents = eventMapper.getFilteredEvents(parameter);
        assertEquals(expectedEvents, returnedEvents);
    }

    @Ignore
    @Test
    @DatabaseSetup(value = TEST_PATH + "EventMapperTest_initial.xml", type = DatabaseOperation.REFRESH)
    @DatabaseTearDown(value = TEST_PATH + "EventMapperTest_initial.xml", type = DatabaseOperation.DELETE_ALL)
    public void testGetFilteredEventsKyivJava() throws ParseException {
        FilteredEventsParameter parameter = getEmptyFilteredEventsParameter();
        Set<Technology> technologies = new HashSet<>();
        technologies.add(BuilderUtil.buildTechnologyJava());
        parameter.setTechnologies(technologies);
        parameter.setCity(BuilderUtil.buildCityKyiv());

        List<Event> expectedEvents = new ArrayList<>();
        expectedEvents.add(BuilderUtil.buildEventJava());

        List<Event> returnedEvents = eventMapper.getFilteredEvents(parameter);
        assertEquals(expectedEvents, returnedEvents);
    }

    @Ignore
    @Test
    @DatabaseSetup(value = TEST_PATH + "EventMapperTest_initial.xml", type = DatabaseOperation.REFRESH)
    @DatabaseTearDown(value = TEST_PATH + "EventMapperTest_initial.xml", type = DatabaseOperation.DELETE_ALL)
    public void testGetFilteredEventsBoyarkaPayed() throws ParseException {
        FilteredEventsParameter parameter = getEmptyFilteredEventsParameter();
        parameter.setCity(BuilderUtil.buildCityBoyarka());
        parameter.setFree(false);

        List<Event> expectedEvents = new ArrayList<>();
        expectedEvents.add(BuilderUtil.buildEventCsharp());

        List<Event> returnedEvents = eventMapper.getFilteredEvents(parameter);
        assertEquals(expectedEvents, returnedEvents);
    }

    @Ignore
    @Test
    @DatabaseSetup(value = TEST_PATH + "EventMapperTest_initial.xml", type = DatabaseOperation.REFRESH)
    @DatabaseTearDown(value = TEST_PATH + "EventMapperTest_initial.xml", type = DatabaseOperation.DELETE_ALL)
    public void testGetFilteredEventsPhpAntSql() throws ParseException {
        List<Event> expectedEvents = new ArrayList<>();
        expectedEvents.add(BuilderUtil.buildEventCplus());
        expectedEvents.add(BuilderUtil.buildEventJs());
        expectedEvents.add(BuilderUtil.buildEventDelphi());

        Set<Technology> technologies = new HashSet<>();
        technologies.add(BuilderUtil.buildTechnologyPhp());
        technologies.add(BuilderUtil.buildTechnologyAnt());
        technologies.add(BuilderUtil.buildTechnologySql());
        FilteredEventsParameter parameter = getEmptyFilteredEventsParameter();
        parameter.setTechnologies(technologies);

        List<Event> returnedEvents = eventMapper.getFilteredEvents(parameter);
        assertEquals(expectedEvents, returnedEvents);
    }

    @Ignore
    @Test
    @DatabaseSetup(value = TEST_PATH + "EventMapperTest_initial.xml", type = DatabaseOperation.REFRESH)
    @DatabaseTearDown(value = TEST_PATH + "EventMapperTest_initial.xml", type = DatabaseOperation.DELETE_ALL)
    public void testGetFilteredEventsInRadius() throws ParseException {
        double testLatitude = 50.454605;
        double testLongitude = 30.403965;
        int testRadius = 5000;

        List<Event> expectedEvents = new ArrayList<>();
        expectedEvents.add(BuilderUtil.buildEventPhp());
        expectedEvents.add(BuilderUtil.buildEventJs());

        FilteredEventsParameter parameter = getEmptyFilteredEventsParameter();
        parameter.setLatitude(testLatitude);
        parameter.setLongitude(testLongitude);
        parameter.setRadius(testRadius);

        List<Event> returnedEvents = eventMapper.getFilteredEvents(parameter);
        assertEquals(expectedEvents, returnedEvents);
    }
}