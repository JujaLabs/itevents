package org.itevents.dao.mybatis.builder;

import org.itevents.dao.model.Event;
import org.itevents.dao.model.Filter;
import org.itevents.dao.model.Technology;
import org.itevents.test_utils.BuilderUtil;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by vaa25 on 06.10.2015.
 */
public class EventSqlBuilderTest {

    private EventSqlBuilder builder = new EventSqlBuilder();
    private Filter filter;

    @Before
    public void setup() {
        filter = new Filter();
    }

    @Test
    public void shouldBuildSqlQueryToAddTechnologiesOfGivenEventJavaToEventTechnologyTable() throws Exception {
        Event event = BuilderUtil.buildEventJava();
        String expectedSql = "INSERT INTO event_technology (event_id, technology_id) " +
                "VALUES (-1, -1), (-1, -4), (-1, -9), (-1, -8)";

        String returnedSql = builder.addEventTechnology(event);

        assertEquals(expectedSql, returnedSql);
    }

    @Test
    public void shouldReturnEmptyStringIfEventTechnologiesAreAbsent() throws Exception {
        Event event = BuilderUtil.buildEventRuby();
        String expectedSql = "";

        String returnedSql = builder.addEventTechnology(event);

        assertEquals(expectedSql, returnedSql);
    }

    @Test
    public void shouldBuildSqlQueryWithEmptyParametersAndPagination() throws Exception {
        String expectedSql = "SELECT * FROM event e WHERE (e.event_date > NOW()) " +
                "ORDER BY event_date LIMIT #{limit} OFFSET #{offset}";

        String returnedSql = builder.getFilteredEvents(filter).replace('\n', ' ');

        assertEquals(expectedSql, returnedSql);
    }

    @Test
    public void shouldBuildSqlQueryByDateCityIdEventIdAndPagination() throws Exception {
        filter.setCity(BuilderUtil.buildCityKyiv());
        List<Technology> testTechnologies = new ArrayList<>();
        testTechnologies.add(BuilderUtil.buildTechnologyJava());
        filter.setTechnologies(testTechnologies);

        String expectedSql = "SELECT * FROM event e JOIN event_technology et ON et.technology_id=-1 " +
                "WHERE (e.event_date > NOW() AND city_id = #{city.id} AND e.id=et.event_id) " +
                "ORDER BY event_date LIMIT #{limit} OFFSET #{offset}";

        String returnedSql = builder.getFilteredEvents(filter).replace('\n', ' ');

        assertEquals(expectedSql, returnedSql);
    }

    @Test
    public void shouldBuildSqlQueryByDateCityIdPriceAndPagination() throws Exception {
        filter.setCity(BuilderUtil.buildCityBoyarka());
        filter.setFree(false);

        String expectedSql = "SELECT * FROM event e " +
                "WHERE (e.event_date > NOW() AND city_id = #{city.id} AND price > 0) " +
                "ORDER BY event_date LIMIT #{limit} OFFSET #{offset}";

        String returnedSql = builder.getFilteredEvents(filter).replace('\n', ' ');

        assertEquals(expectedSql, returnedSql);
    }

    @Test
    public void shouldBuiltSqlQueryByFewTechnologiesAndPagination() throws Exception {

        List<Technology> testTechnologies = new ArrayList<>();
        testTechnologies.add(BuilderUtil.buildTechnologyPhp());
        testTechnologies.add(BuilderUtil.buildTechnologyAnt());
        testTechnologies.add(BuilderUtil.buildTechnologySql());
        filter.setTechnologies(testTechnologies);

        Iterator<Technology> iterator = testTechnologies.iterator();
        String expectedSql = "SELECT * FROM event e JOIN event_technology et " +
                "ON et.technology_id=" + iterator.next().getId() + " or et.technology_id=" + iterator.next().getId() +
                " or et.technology_id=" + iterator.next().getId() + " WHERE (e.event_date > NOW() AND e.id=et.event_id) " +
                "ORDER BY event_date LIMIT #{limit} OFFSET #{offset}";

        String returnedSql = builder.getFilteredEvents(filter).replace('\n', ' ');

        assertEquals(expectedSql, returnedSql);
    }

    @Test
    public void shouldBuildSqlQueryWithPaginationToGetEventsInRadius() throws Exception {
        double testLatitude = 50.454605;
        double testLongitude = 30.403965;
        int testRadius = 5000;

        filter.setLatitude(testLatitude);
        filter.setLongitude(testLongitude);
        filter.setRadius(testRadius);

        String expectedSql = "SELECT * FROM event e " +
                "WHERE (e.event_date > NOW() " +
                "AND ST_DWithin((point)::geography, ST_MakePoint(#{longitude},#{latitude})::geography, #{radius})) " +
                "ORDER BY event_date LIMIT #{limit} OFFSET #{offset}";

        String returnedSql = builder.getFilteredEvents(filter).replace('\n', ' ');

        assertEquals(expectedSql, returnedSql);
    }

    @Test
    public void shouldBuildSqlQueryWithDateRangeWithRating() throws Exception {
        Filter filter = BuilderUtil.buildTestFilter();
        String expectedSql = "SELECT * FROM event e " +
                "JOIN event_technology et ON et.technology_id=-1 or et.technology_id=-5 or et.technology_id=-8 " +
                "LEFT OUTER JOIN  (SELECT event_id, COUNT(*) as visits FROM visit_log GROUP BY event_id) " +
                "AS visits ON e.id = visits.event_id " +
                "WHERE (" +
                "e.event_date > NOW() " +
                "AND city_id = #{city.id} " +
                "AND (price IS NULL OR price = 0) " +
                "AND e.id=et.event_id " +
                "AND e.event_date < NOW() + (#{rangeInDays} || ' DAYS')::INTERVAL" +
                ") " +
                "ORDER BY event_date " +
                "LIMIT #{limit}";

        String returnedSql = builder.getFilteredEventsInDateRangeWithRating(filter)
                .replace('\n', ' ');

        assertEquals(expectedSql, returnedSql);
    }
}