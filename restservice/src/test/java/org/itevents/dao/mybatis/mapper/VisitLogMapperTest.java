package org.itevents.dao.mybatis.mapper;

import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import org.itevents.AbstractDbTest;
import org.itevents.model.Event;
import org.itevents.model.VisitLog;
import org.itevents.util.BuilderUtil;
import org.junit.Test;

import javax.inject.Inject;
import java.text.ParseException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by vaa25 on 21.07.2015.
 */
public class VisitLogMapperTest extends AbstractDbTest {
    private final String TEST_PATH = PATH + "VisitLogMapperTest/";
    //    private final Date date1 = new GregorianCalendar(2016, 6, 20).getTime();
    @Inject
    private VisitLogMapper visitLogMapper;

    @Test
    @DatabaseSetup(value = TEST_PATH + "VisitLogMapperTest_initial.xml", type = DatabaseOperation.REFRESH)
    public void testGetVisitLogSuccess() throws ParseException {
        VisitLog expectedVisitLog = BuilderUtil.buildFirstVisitLog();
        VisitLog returnedVisitLog = visitLogMapper.getVisitLog(ID_1);
        assertEquals(expectedVisitLog, returnedVisitLog);
    }

    @Test
    @DatabaseSetup(value = TEST_PATH + "VisitLogMapperTest_initial.xml", type = DatabaseOperation.REFRESH)
    public void testGetVisitLogFail() throws Exception {
        assertNull(visitLogMapper.getVisitLog(ID_0));
    }

    @Test
    @DatabaseSetup(value = TEST_PATH + "VisitLogMapperTest_initial.xml", type = DatabaseOperation.REFRESH)
    public void testGetVisitLogByEvent() throws Exception {
        List<VisitLog> expectedVisitLogs = BuilderUtil.buildListVisitLogJava();
        Event eventJava = BuilderUtil.buildEventJava();
        List<VisitLog> returnedVisitLogs = visitLogMapper.getVisitLogsByEvent(eventJava);
        assertEquals(expectedVisitLogs, returnedVisitLogs);
    }

    @Test
    @DatabaseSetup(value = TEST_PATH + "VisitLogMapperTest_initial.xml", type = DatabaseOperation.REFRESH)
    @ExpectedDatabase(value = TEST_PATH + "testAddVisitLog_expected.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    @DatabaseTearDown(value = TEST_PATH + "VisitLogMapperTest_initial.xml")
    public void testAddVisitLog() throws Exception {
        VisitLog testVisitLog = BuilderUtil.buildVisitLogTest();
        visitLogMapper.addVisitLog(testVisitLog);
    }

    @Test
    @DatabaseSetup(value = TEST_PATH + "VisitLogMapperTest_initial.xml", type = DatabaseOperation.REFRESH)
    public void testGetAllVisitLogs() {
        int expectedSize = 7;
        int returnedSize = visitLogMapper.getAllVisitLogs().size();
        assertEquals(expectedSize, returnedSize);
    }

    @Test
    @DatabaseSetup(value = TEST_PATH + "testRemoveVisitLog_initial.xml", type = DatabaseOperation.REFRESH)
    @ExpectedDatabase(value = TEST_PATH + "VisitLogMapperTest_initial.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    @DatabaseTearDown(value = TEST_PATH + "VisitLogMapperTest_initial.xml")
    public void testRemoveVisitLog() throws ParseException {
        VisitLog testVisitLog = BuilderUtil.buildVisitLogTest();
        visitLogMapper.removeVisitLog(testVisitLog);
    }

}
