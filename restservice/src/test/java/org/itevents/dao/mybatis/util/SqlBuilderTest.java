package org.itevents.dao.mybatis.util;

import org.itevents.model.Technology;
import org.itevents.parameter.FilteredEventsParameter;
import org.itevents.service.CityService;
import org.itevents.service.TechnologyService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by vaa25 on 17.09.2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext.xml"})
@Transactional
public class SqlBuilderTest {

    @Inject
    private CityService cityService;
    @Inject
    private TechnologyService technologyService;

    private FilteredEventsParameter parameter;

    @Before
    public void setup() {
        parameter = new FilteredEventsParameter();
    }

    @Test
    public void testGetFilteredEventsEmpty() throws Exception {
        String expectedSql = "SELECT * FROM events e WHERE (e.event_date > NOW())";
        String returnedSql = new SqlBuilder().getFilteredEvents(parameter).replace('\n', ' ');
        assertEquals(expectedSql, returnedSql);
    }

    @Test
    public void testGetFilteredEventsKyivJava() {
        int javaId = 1;
        int kyivId = 1;

        parameter.setCity(cityService.getCity(kyivId));
        List<Technology> testTechnologies = new ArrayList<>();
        testTechnologies.add(technologyService.getTechnology(javaId));
        parameter.setTechnologies(testTechnologies);

        String expectedSql = "SELECT * FROM events e JOIN event_technology et " +
                "ON et.technology_id=1 " +
                "WHERE (e.event_date > NOW() AND city_id = #{city.id} AND e.id=et.event_id)";

        String returnedSql = new SqlBuilder().getFilteredEvents(parameter).replace('\n', ' ');
        assertEquals(expectedSql, returnedSql);
    }

    @Test
    public void testGetFilteredEventsBoyarkaPayed() {
        int boyarkaId = 3;

        parameter.setCity(cityService.getCity(boyarkaId));
        parameter.setFree(false);

        String expectedSql = "SELECT * FROM events e " +
                "WHERE (e.event_date > NOW() AND city_id = #{city.id} AND price > 0)";

        String returnedSql = new SqlBuilder().getFilteredEvents(parameter).replace('\n', ' ');
        assertEquals(expectedSql, returnedSql);
    }

    @Test
    public void testGetFilteredEventsPhpAntSql() {
        int phpId = 3;
        int antId = 7;
        int sqlId = 10;

        List<Technology> testTechnologies = new ArrayList<>();
        testTechnologies.add(technologyService.getTechnology(phpId));
        testTechnologies.add(technologyService.getTechnology(antId));
        testTechnologies.add(technologyService.getTechnology(sqlId));
        parameter.setTechnologies(testTechnologies);

        String expectedSql = "SELECT * FROM events e JOIN event_technology et " +
                "ON et.technology_id=3 or et.technology_id=7 or et.technology_id=10 " +
                "WHERE (e.event_date > NOW() AND e.id=et.event_id)";

        String returnedSql = new SqlBuilder().getFilteredEvents(parameter).replace('\n', ' ');
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

        String expectedSql = "SELECT * FROM events e " +
                "WHERE (e.event_date > NOW() " +
                "AND ST_DWithin((point)::geography, ST_MakePoint(#{longitude},#{latitude})::geography, #{radius}))";

        String returnedSql = new SqlBuilder().getFilteredEvents(parameter).replace('\n', ' ');
        assertEquals(expectedSql, returnedSql);
    }

}