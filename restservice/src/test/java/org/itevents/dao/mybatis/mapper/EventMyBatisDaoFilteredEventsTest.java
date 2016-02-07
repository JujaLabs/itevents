package org.itevents.dao.mybatis.mapper;

import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import org.itevents.AbstractDbTest;
import org.itevents.dao.mybatis.sql_session_dao.EventMyBatisDao;
import org.itevents.model.Event;
import org.itevents.model.Filter;
import org.itevents.test_utils.BuilderUtil;
import org.itevents.test_utils.dbunit.dataset_loader.EventDateReplacementDataSetLoader;
import org.junit.Test;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

@DbUnitConfiguration(databaseConnection = "dbUnitDatabaseConnection", dataSetLoader = EventDateReplacementDataSetLoader.class)
@DatabaseSetup(value = EventMyBatisDaoFilteredEventsTest.TEST_PATH + "MailFilterUtilTest_initial.xml", type = DatabaseOperation.CLEAN_INSERT)
@DatabaseTearDown(value = EventMyBatisDaoFilteredEventsTest.TEST_PATH + "MailFilterUtilTest_initial.xml", type = DatabaseOperation.DELETE_ALL)
public class EventMyBatisDaoFilteredEventsTest extends AbstractDbTest {

    public static final String TEST_PATH = PATH + "EventMapperTest/";

    private static final int FILTER_RANGE_IN_DAYS = 10;
    private static final int COUNT_OF_EVENTS_IN_EMAIL = 7;

    @Inject
    private EventMyBatisDao eventMyBatisDao;

    @Test
    public void shouldReturnKyivEvents() throws Exception {
        Filter filter = BuilderUtil.buildKyivFilter();
        filter.setLimit(FILTER_RANGE_IN_DAYS);
        filter.setRangeInDays(COUNT_OF_EVENTS_IN_EMAIL);
        List<Event> expectedFilteredEvents = new ArrayList<>(Arrays.asList(BuilderUtil.buildFreeKyivJavaEvent(),
                BuilderUtil.buildPayedKyivJavaEvent()));
        List<Event> returnedFilteredEvents = eventMyBatisDao.getFilteredEventsWithRating(filter);
        clearDateTimeInEventList(expectedFilteredEvents);
        clearDateTimeInEventList(returnedFilteredEvents);
        assertEquals(expectedFilteredEvents, returnedFilteredEvents);
    }

    @Test
    public void shouldReturnFreeEvents() throws Exception {
        Filter filter = BuilderUtil.buildFreeFilter();
        filter.setLimit(FILTER_RANGE_IN_DAYS);
        filter.setRangeInDays(COUNT_OF_EVENTS_IN_EMAIL);
        List<Event> expectedFilteredEvents = new ArrayList<>(Arrays.asList(BuilderUtil.buildFreeKyivJavaEvent(),
                BuilderUtil.buildFreeBoyarkaGradleEvent()));
        List<Event> returnedFilteredEvents = eventMyBatisDao.getFilteredEventsWithRating(filter);
        clearDateTimeInEventList(expectedFilteredEvents);
        clearDateTimeInEventList(returnedFilteredEvents);
        assertEquals(expectedFilteredEvents, returnedFilteredEvents);
    }

    @Test
    public void shouldReturnJavaEvents() throws Exception {
        Filter filter = BuilderUtil.builderFilterJava();
        filter.setLimit(FILTER_RANGE_IN_DAYS);
        filter.setRangeInDays(COUNT_OF_EVENTS_IN_EMAIL);
        List<Event> expectedFilteredEvents = new ArrayList<>(Arrays.asList(BuilderUtil.buildFreeKyivJavaEvent(),
                BuilderUtil.buildPayedKyivJavaEvent()));
        List<Event> returnedFilteredEvents = eventMyBatisDao.getFilteredEventsWithRating(filter);
        clearDateTimeInEventList(expectedFilteredEvents);
        clearDateTimeInEventList(returnedFilteredEvents);
        assertEquals(expectedFilteredEvents, returnedFilteredEvents);
    }

    private void clearDateTimeInEventList(List<Event> events) {
        for (Event event : events) {
            event.setEventDate(null);
        }
    }
}