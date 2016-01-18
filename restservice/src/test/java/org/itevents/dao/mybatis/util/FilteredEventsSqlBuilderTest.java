package org.itevents.dao.mybatis.util;

import org.itevents.model.Filter;
import org.itevents.model.Technology;
import org.itevents.test_utils.BuilderUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;

/**
 * Created by vaa25 on 17.09.2015.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext.xml"})
public class FilteredEventsSqlBuilderTest {

    private Filter parameter;

    @Before
    public void setup() {
        parameter = new Filter();
    }

    @Test
    public void shouldBuildSqlQueryWithEmptyParametersAndPagination() throws Exception {
        String expectedSql = "SELECT * FROM event e WHERE (e.event_date > NOW()) " +
                "ORDER BY event_date LIMIT #{limit} OFFSET #{offset}";
        String returnedSql = new FilteredEventsSqlBuilder().getFilteredEvents(parameter).replace('\n', ' ');
        assertEquals(expectedSql, returnedSql);
    }

    @Test
    public void shouldBuildSqlQueryByDateCityIdEventIdAndPagination() {
        parameter.setCity(BuilderUtil.buildCityKyiv());
        List<Technology> testTechnologies = new ArrayList<>();
        testTechnologies.add(BuilderUtil.buildTechnologyJava());
        parameter.setTechnologies(testTechnologies);

        String expectedSql = "SELECT * FROM event e JOIN event_technology et ON et.technology_id=-1 " +
                "WHERE (e.event_date > NOW() AND city_id = #{city.id} AND e.id = et.event_id) " +
                "ORDER BY event_date LIMIT #{limit} OFFSET #{offset}";

        String returnedSql = new FilteredEventsSqlBuilder().getFilteredEvents(parameter).replace('\n', ' ');
        assertEquals(expectedSql, returnedSql);
    }

    @Test
    public void shouldBuildSqlQueryByDateCityIdPriceAndPagination() {
        parameter.setCity(BuilderUtil.buildCityBoyarka());
        parameter.setFree(false);

        String expectedSql = "SELECT * FROM event e " +
                "WHERE (e.event_date > NOW() AND city_id = #{city.id} AND price > 0) " +
                "ORDER BY event_date LIMIT #{limit} OFFSET #{offset}";

        String returnedSql = new FilteredEventsSqlBuilder().getFilteredEvents(parameter).replace('\n', ' ');
        assertEquals(expectedSql, returnedSql);
    }


    @Test
    public void shouldBuiltSqlQueryByFewTechnologiesAndPagination() {

        List<Technology> testTechnologies = new ArrayList<>();
        testTechnologies.add(BuilderUtil.buildTechnologyPhp());
        testTechnologies.add(BuilderUtil.buildTechnologyAnt());
        testTechnologies.add(BuilderUtil.buildTechnologySql());
        parameter.setTechnologies(testTechnologies);

        Iterator<Technology> iterator = testTechnologies.iterator();

        String expectedSql = "SELECT * FROM event e JOIN event_technology et " +
                "ON et.technology_id=" + iterator.next().getId() + " or et.technology_id=" + iterator.next().getId() +
                " or et.technology_id=" + iterator.next().getId() + " WHERE (e.event_date > NOW() AND e.id = et.event_id) " +
                "ORDER BY event_date LIMIT #{limit} OFFSET #{offset}";

        String returnedSql = new FilteredEventsSqlBuilder().getFilteredEvents(parameter).replace('\n', ' ');
        assertEquals(expectedSql, returnedSql);
    }

    @Test
    public void shouldBuildSqlQueryWithPaginationToGetEventsInRadius() {
        double testLatitude = 50.454605;
        double testLongitude = 30.403965;
        int testRadius = 5000;

        parameter.setLatitude(testLatitude);
        parameter.setLongitude(testLongitude);
        parameter.setRadius(testRadius);

        String expectedSql = "SELECT * FROM event e " +
                "WHERE (e.event_date > NOW() " +
                "AND ST_DWithin((point)::geography, ST_MakePoint(#{longitude},#{latitude})::geography, #{radius})) " +
                "ORDER BY event_date LIMIT #{limit} OFFSET #{offset}";

        String returnedSql = new FilteredEventsSqlBuilder().getFilteredEvents(parameter).replace('\n', ' ');
        assertEquals(expectedSql, returnedSql);
    }

    @Test
    public void shouldBuildSqlQueryWithDateRangeWithRating() {
        Filter filter = BuilderUtil.buildTestFilter();
        String expectedSql = "SELECT * FROM event e " +
                "JOIN event_technology et ON et.technology_id=-1 or et.technology_id=-5 or et.technology_id=-8 " +
                "LEFT OUTER JOIN  (SELECT event_id, COUNT(*) as visits FROM visit_log GROUP BY event_id) " +
                    "AS visits ON e.id = visits.event_id " +
                "WHERE (" +
                    "e.event_date > NOW() " +
                    "AND city_id = #{city.id} " +
                    "AND (price IS NULL OR price = 0) " +
                    "AND e.id = et.event_id " +
                    "AND e.event_date < NOW() + (#{rangeInDays} || ' DAYS')::INTERVAL" +
                ") " +
                "ORDER BY event_date " +
                "LIMIT #{limit}";
        String returnedSql = new FilteredEventsSqlBuilder().getFilteredEventsInDateRangeWithRating(filter)
                .replace('\n', ' ');
        assertEquals(expectedSql, returnedSql);
    }

}