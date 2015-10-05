package org.itevents.dao.mybatis.mapper;

import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import org.itevents.AbstractDbTest;
import org.itevents.model.Event;
import org.itevents.util.BuilderUtil;
import org.junit.Test;

import javax.inject.Inject;

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
}