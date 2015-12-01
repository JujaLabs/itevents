package org.itevents.dao.mybatis.mapper;

import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import org.itevents.AbstractDbTest;
import org.itevents.model.Event;
import org.itevents.model.VisitLog;
import org.itevents.test_utils.BuilderUtil;
import org.junit.Test;

import javax.inject.Inject;
import java.text.ParseException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by vaa25 on 21.07.2015.
 */
@DatabaseSetup(value = "file:src/test/resources/dbunit/VisitLogMapperTest/VisitLogMapperTest_initial.xml",
        type = DatabaseOperation.REFRESH)
@DatabaseTearDown(value = "file:src/test/resources/dbunit/VisitLogMapperTest/VisitLogMapperTest_initial.xml",
        type = DatabaseOperation.DELETE_ALL)
public class VisitLogMapperDbTest extends AbstractDbTest {
    private final String TEST_PATH = PATH + "VisitLogMapperTest/";
    @Inject
    private VisitLogMapper visitLogMapper;

    @Test
    public void shouldFindVisitLogById() throws ParseException {
        VisitLog expectedVisitLog = BuilderUtil.buildVisitLogFirst();
        VisitLog returnedVisitLog = visitLogMapper.getVisitLog(ID_1);
        assertEquals(expectedVisitLog, returnedVisitLog);
    }

    @Test
    public void expectNullWhenVisitLogIsAbsent() throws Exception {
        assertNull(visitLogMapper.getVisitLog(ABSENT_ID));
    }

    @Test
    public void shouldFindVisitLogByEvent() throws Exception {
        List<VisitLog> expectedVisitLogs = BuilderUtil.buildListVisitLogJava();
        Event eventJava = BuilderUtil.buildEventJava();
        List<VisitLog> returnedVisitLogs = visitLogMapper.getVisitLogsByEvent(eventJava);
        assertEquals(expectedVisitLogs, returnedVisitLogs);
    }

    @Test
    @ExpectedDatabase(value = TEST_PATH + "testAddVisitLog_expected.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    public void shouldAddVisitLog() throws Exception {
        VisitLog testVisitLog = BuilderUtil.buildVisitLogTest();
        visitLogMapper.addVisitLog(testVisitLog);
    }

    @Test
    public void shouldGetAllVisitLogs() {
        int expectedSize = 7;
        int returnedSize = visitLogMapper.getAllVisitLogs().size();
        assertEquals(expectedSize, returnedSize);
    }

    @Test
    @DatabaseSetup(value = TEST_PATH + "testRemoveVisitLog_initial.xml", type = DatabaseOperation.REFRESH)
    @ExpectedDatabase(value = TEST_PATH + "VisitLogMapperTest_initial.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    public void shouldRemoveVisitLog() throws ParseException {
        VisitLog testVisitLog = BuilderUtil.buildVisitLogTest();
        visitLogMapper.removeVisitLog(testVisitLog);
    }

}
