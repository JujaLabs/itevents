package org.itevents.dao.mybatis.util;

import org.itevents.model.Event;
import org.itevents.test_utils.BuilderUtil;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by vaa25 on 06.10.2015.
 */
public class EventTechnologySqlBuilderTest {


    @Test
    public void shouldBuildSqlQueryToAddTechnologiesOfGivenEventJavaToEventTechnologyTable() throws Exception {
        String expectedSql = "INSERT INTO event_technology (event_id, technology_id) " +
                "VALUES (-1, -1), (-1, -4), (-1, -9), (-1, -8)";
        Event event = BuilderUtil.buildEventJava();
        EventTechnologySqlBuilder builder = new EventTechnologySqlBuilder();
        String returnedSql = builder.addEventTechnology(event);
        assertEquals(expectedSql, returnedSql);
    }

    @Test
    public void shouldReturnEmptyStringIfEventTechnologiesAreAbsent() throws Exception {
        String expectedSql = "";
        Event event = BuilderUtil.buildEventRuby();
        EventTechnologySqlBuilder builder = new EventTechnologySqlBuilder();
        String returnedSql = builder.addEventTechnology(event);
        assertEquals(expectedSql, returnedSql);
    }
}