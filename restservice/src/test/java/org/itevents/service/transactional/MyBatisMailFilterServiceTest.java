package org.itevents.service.transactional;

import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import org.itevents.AbstractDbTest;
import org.itevents.model.Event;
import org.itevents.model.Filter;
import org.itevents.service.MailFilterService;
import org.itevents.test_utils.BuilderUtil;
import org.itevents.test_utils.dbunit.dataset_loader.EventDateReplacementDataSetLoader;
import org.junit.Test;
import javax.inject.Inject;
import java.text.ParseException;
import java.util.List;

import static org.junit.Assert.assertEquals;

@DbUnitConfiguration(databaseConnection = "dbUnitDatabaseConnection", dataSetLoader = EventDateReplacementDataSetLoader.class)
public class MyBatisMailFilterServiceTest extends AbstractDbTest {

    private final String TEST_PATH = PATH + "MailFilterUtilTest/";

    @Inject
    private MailFilterService mailFilterService;

    @Test
    @DatabaseSetup(value = TEST_PATH + "MailFilterUtilTest_initial.xml", type = DatabaseOperation.REFRESH)
    @DatabaseTearDown(value = TEST_PATH + "MailFilterUtilTest_initial.xml", type = DatabaseOperation.DELETE_ALL)
    public void testFilterUtil() throws ParseException {
        Filter filter = BuilderUtil.buildTestFilter();
        List<Event> filteredEvents = mailFilterService.getFilteredEventsInDateRangeWithRating(filter);
        assertEquals(2, filteredEvents.size()); // todo: improve assertion
    }
}