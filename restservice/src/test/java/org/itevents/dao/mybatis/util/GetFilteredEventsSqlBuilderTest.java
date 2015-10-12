package org.itevents.dao.mybatis.util;

import org.itevents.model.Technology;
import org.itevents.parameter.FilteredEventsParameter;
import org.itevents_utils.BuilderUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by vaa25 on 17.09.2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext.xml"})
public class GetFilteredEventsSqlBuilderTest {

    private FilteredEventsParameter parameter;

    @Before
    public void setup() {
        parameter = new FilteredEventsParameter();
    }

    @Test
    public void testGetFilteredEventsEmpty() throws Exception {
        String expectedSql = "SELECT * FROM event e WHERE (e.event_date > NOW()) LIMIT #{limit} OFFSET #{offset}";
        String returnedSql = new GetFilteredEventsSqlBuilder().getFilteredEvents(parameter).replace('\n', ' ');
        assertEquals(expectedSql, returnedSql);
    }

    @Test
    public void testGetFilteredEventsKyivJava() {
        parameter.setCity(BuilderUtil.buildCityKyiv());
        List<Technology> testTechnologies = new ArrayList<>();
        testTechnologies.add(BuilderUtil.buildTechnologyJava());
        parameter.setTechnologies(testTechnologies);

        String expectedSql = "SELECT * FROM event e JOIN event_technology et " +
                "ON et.technology_id=-1 " +
                "WHERE (e.event_date > NOW() AND city_id = #{city.id} AND e.id=et.event_id) " +
                "LIMIT #{limit} OFFSET #{offset}";

        String returnedSql = new GetFilteredEventsSqlBuilder().getFilteredEvents(parameter).replace('\n', ' ');
        assertEquals(expectedSql, returnedSql);
    }

    @Test
    public void testGetFilteredEventsBoyarkaPayed() {
        parameter.setCity(BuilderUtil.buildCityBoyarka());
        parameter.setFree(false);

        String expectedSql = "SELECT * FROM event e " +
                "WHERE (e.event_date > NOW() AND city_id = #{city.id} AND price > 0) " +
                "LIMIT #{limit} OFFSET #{offset}";

        String returnedSql = new GetFilteredEventsSqlBuilder().getFilteredEvents(parameter).replace('\n', ' ');
        assertEquals(expectedSql, returnedSql);
    }

    @Test
    public void testGetFilteredEventsPhpAntSql() {

        List<Technology> testTechnologies = new ArrayList<>();
        testTechnologies.add(BuilderUtil.buildTechnologyPhp());
        testTechnologies.add(BuilderUtil.buildTechnologyAnt());
        testTechnologies.add(BuilderUtil.buildTechnologySql());
        parameter.setTechnologies(testTechnologies);

        Iterator<Technology> iterator = testTechnologies.iterator();

        String expectedSql = "SELECT * FROM event e JOIN event_technology et " +
                "ON et.technology_id=" + iterator.next().getId() + " or et.technology_id=" + iterator.next().getId() +
                " or et.technology_id=" + iterator.next().getId() + " WHERE (e.event_date > NOW() AND e.id=et.event_id) " +
                "LIMIT #{limit} OFFSET #{offset}";

        String returnedSql = new GetFilteredEventsSqlBuilder().getFilteredEvents(parameter).replace('\n', ' ');
        assertEquals(expectedSql, returnedSql);
    }

    @Test
    public void testGetFilteredEventsInRadius() {
        double testLatitude = 50.454605;
        double testLongitude = 30.403965;
        int testRadius = 5000;

        parameter.setLatitude(testLatitude);
        parameter.setLongitude(testLongitude);
        parameter.setRadius(testRadius);

        String expectedSql = "SELECT * FROM event e " +
                "WHERE (e.event_date > NOW() " +
                "AND ST_DWithin((point)::geography, ST_MakePoint(#{longitude},#{latitude})::geography, #{radius})) " +
                "LIMIT #{limit} OFFSET #{offset}";

        String returnedSql = new GetFilteredEventsSqlBuilder().getFilteredEvents(parameter).replace('\n', ' ');
        assertEquals(expectedSql, returnedSql);
    }

}