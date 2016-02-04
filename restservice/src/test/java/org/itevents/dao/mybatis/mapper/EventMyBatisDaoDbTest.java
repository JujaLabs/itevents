package org.itevents.dao.mybatis.mapper;

import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import org.itevents.AbstractDbTest;
import org.itevents.controller.converter.FilterConverter;
import org.itevents.controller.wrapper.FilterWrapper;
import org.itevents.dao.exception.EntityNotFoundDaoException;
import org.itevents.dao.mybatis.sql_session_dao.EventMyBatisDao;
import org.itevents.model.Event;
import org.itevents.model.Filter;
import org.itevents.model.Technology;
import org.itevents.model.User;
import org.itevents.test_utils.BuilderUtil;
import org.itevents.util.time.DateTimeUtil;
import org.junit.Assert;
import org.junit.Test;

import javax.inject.Inject;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by vaa25 on 22.07.2015.
 */
@DatabaseSetup(value = "file:src/test/resources/dbunit/EventMapperTest/EventMapperTest_initial.xml",
        type = DatabaseOperation.REFRESH)
@DatabaseTearDown(value = "file:src/test/resources/dbunit/EventMapperTest/EventMapperTest_initial.xml",
        type = DatabaseOperation.DELETE_ALL)
public class EventMyBatisDaoDbTest extends AbstractDbTest {

    private final String TEST_PATH = PATH + "EventMapperTest/";
    @Inject
    private EventMyBatisDao eventMyBatisDao;

    @Test
    public void testFindEventById() throws Exception {
        Event expectedEvent = BuilderUtil.buildEventJava();
        Event returnedEvent = eventMyBatisDao.getEvent(expectedEvent.getId());
        assertEquals(expectedEvent, returnedEvent);
    }

    @Test(expected = EntityNotFoundDaoException.class)
    public void shouldThrowEntityNotFoundDaoExceptionWhenEventIsAbsent() throws Exception {
        eventMyBatisDao.getEvent(ABSENT_ID);
    }

    @Test
    public void shouldGetAllEvents() throws ParseException {
        int expectedSize = 7;
        int returnedSize = eventMyBatisDao.getAllEvents().size();
        Assert.assertEquals(expectedSize, returnedSize);
    }

    @Test
    @DatabaseSetup(value = TEST_PATH + "testAddEventTechnology_expected.xml", type = DatabaseOperation.REFRESH)
    @ExpectedDatabase(value = TEST_PATH + "addEventTechnology_initial.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    public void shouldRemoveTechnologiesFromEventTechnologyTable() throws Exception {
        Event removingEvent = BuilderUtil.buildEventRuby();
        eventMyBatisDao.removeEventTechnology(removingEvent);
    }

    @Test
    @ExpectedDatabase(value = TEST_PATH + "testAddEvent_expected.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    public void shouldAddEvent() throws Exception {
        Event addingEvent = BuilderUtil.buildEventRuby();
        eventMyBatisDao.addEvent(addingEvent);
    }

    @Test
    @DatabaseSetup(value = TEST_PATH + "addEventTechnology_initial.xml", type = DatabaseOperation.REFRESH)
    @ExpectedDatabase(value = TEST_PATH + "testAddEventTechnology_expected.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    public void shouldAddTechnologiesToEventTechnologyTable() throws Exception {
        Event addingEvent = BuilderUtil.buildEventRuby();
        List<Technology> technologies = new ArrayList<>();
        technologies.add(BuilderUtil.buildTechnologyJava());
        technologies.add(BuilderUtil.buildTechnologyMyBatis());
        addingEvent.setTechnologies(technologies);
        eventMyBatisDao.addEventTechnology(addingEvent);
    }

    @Test
    public void shouldFindEventsWithDefaultParameters() throws Exception {
        Filter parameter = getDefaultFilteredEventsParameter();
        int expectedSize = 7;
        int returnedSize = eventMyBatisDao.getFilteredEvents(parameter).size();
        assertEquals(expectedSize, returnedSize);
    }

    private Filter getDefaultFilteredEventsParameter() {
        Filter result = new Filter();
        result.setLimit(10);
        result.setOffset(0);
        return result;
    }

    @Test
    public void shouldFindEventsWithPage3ItemsPerPage2() throws Exception {
        FilterWrapper wrapper = new FilterWrapper();
        wrapper.setPage(3);
        wrapper.setItemsPerPage(2);
        Filter parameter = new FilterConverter().toFilter(wrapper);

        List<Event> expectedEvents = new ArrayList<>();
        expectedEvents.add(BuilderUtil.buildEventCplus());
        expectedEvents.add(BuilderUtil.buildEventPhp());

        List<Event> returnedEvents = eventMyBatisDao.getFilteredEvents(parameter);
        assertEquals(expectedEvents, returnedEvents);
    }

    @Test
    public void shouldFindEventsWithDefaultPagination() throws Exception {
        FilterWrapper wrapper = new FilterWrapper();
        wrapper.setPage(30);
        wrapper.setItemsPerPage(-2);
        Filter parameter = new FilterConverter().toFilter(wrapper);

        List<Event> expectedEvents = new ArrayList<>();

        List<Event> returnedEvents = eventMyBatisDao.getFilteredEvents(parameter);
        assertEquals(expectedEvents, returnedEvents);
    }

    @Test
    public void shouldFindEventsInKyivWithTechnologyJava() throws Exception {
        Filter parameter = getDefaultFilteredEventsParameter();
        List<Technology> technologies = new ArrayList<>();
        technologies.add(BuilderUtil.buildTechnologyJava());
        parameter.setTechnologies(technologies);
        parameter.setCity(BuilderUtil.buildCityKyiv());

        List<Event> expectedEvents = new ArrayList<>();
        expectedEvents.add(BuilderUtil.buildEventJava());

        List<Event> returnedEvents = eventMyBatisDao.getFilteredEvents(parameter);
        assertEquals(expectedEvents, returnedEvents);
    }

    @Test
    public void shouldFindPayedEventsInBoyarka() throws Exception {
        Filter parameter = getDefaultFilteredEventsParameter();
        parameter.setCity(BuilderUtil.buildCityBoyarka());
        parameter.setFree(false);

        List<Event> expectedEvents = new ArrayList<>();
        expectedEvents.add(BuilderUtil.buildEventCsharp());

        List<Event> returnedEvents = eventMyBatisDao.getFilteredEvents(parameter);
        assertEquals(expectedEvents, returnedEvents);
    }

    @Test
    public void shouldFindEventsWithTechnologyPhpOrAntOrSql() throws Exception {
        List<Technology> technologies = new ArrayList<>();
        technologies.add(BuilderUtil.buildTechnologyPhp());
        technologies.add(BuilderUtil.buildTechnologyAnt());
        technologies.add(BuilderUtil.buildTechnologySql());
        Filter parameter = getDefaultFilteredEventsParameter();
        parameter.setTechnologies(technologies);

        List<Event> expectedEvents = new ArrayList<>();
        expectedEvents.add(BuilderUtil.buildEventDelphi());
        expectedEvents.add(BuilderUtil.buildEventCplus());
        expectedEvents.add(BuilderUtil.buildEventJs());

        List<Event> returnedEvents = eventMyBatisDao.getFilteredEvents(parameter);
        assertEquals(expectedEvents, returnedEvents);
    }

    @Test
    public void shouldFindEventsInRadiusWithGivenCenter() throws Exception {
        double testLatitude = 50.454605;
        double testLongitude = 30.403965;
        int testRadius = 5000;

        List<Event> expectedEvents = new ArrayList<>();
        expectedEvents.add(BuilderUtil.buildEventPhp());
        expectedEvents.add(BuilderUtil.buildEventJs());

        Filter parameter = getDefaultFilteredEventsParameter();
        parameter.setLatitude(testLatitude);
        parameter.setLongitude(testLongitude);
        parameter.setRadius(testRadius);

        List<Event> returnedEvents = eventMyBatisDao.getFilteredEvents(parameter);
        assertEquals(expectedEvents, returnedEvents);
    }

    @Test
    @DatabaseSetup(value = TEST_PATH + "assignUserEvent_initial.xml", type = DatabaseOperation.REFRESH)
    @ExpectedDatabase(value = TEST_PATH + "testAssignUserEvent_expected.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    public void shouldAssignUserToEvent() throws Exception {
        User user = BuilderUtil.buildUserAnakin();
        Event event = BuilderUtil.buildEventPhp();
        eventMyBatisDao.assignUserToEvent(user, event);
    }

    @Test
    @DatabaseSetup(value = TEST_PATH + "testUnassignUserEvent_initial.xml" , type = DatabaseOperation.REFRESH)
    @ExpectedDatabase(value = TEST_PATH + "testUnassignUserEvent_expected.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    public void shouldUnassignUserFromEvent() throws Exception {
        User user = BuilderUtil.buildUserAnakin();
        Event event = BuilderUtil.buildEventPhp();

        Date unassignDate = DateTimeUtil.yyyyMMddStringToDate("2115.07.20");
        String unassignReason = "test";

        eventMyBatisDao.unassignUserFromEvent(user, event, unassignDate, unassignReason);
    }

    @Test
    @DatabaseSetup(value = TEST_PATH + "assignUserEvent_initial.xml", type = DatabaseOperation.REFRESH)
    public void shouldReturnEventsByUser() throws Exception{
        User user = BuilderUtil.buildUserAnakin();
        Event event = BuilderUtil.buildEventJs();
        List expectedEvents = new ArrayList<>();
        expectedEvents.add(event);
        List returnedEvents = eventMyBatisDao.getEventsByUser(user);
        assertEquals(expectedEvents, returnedEvents);
    }
}