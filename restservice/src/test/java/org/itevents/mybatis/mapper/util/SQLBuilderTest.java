package org.itevents.mybatis.mapper.util;

import org.itevents.model.Technology;
import org.itevents.parameter.FilteredEventsParameter;
import org.itevents.service.TechnologyService;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext.xml"})
@Transactional
public class SQLBuilderTest {

    @Inject
    private TechnologyService technologyService;

    @Test
    @Ignore
    public void testBuildingFilteredEventsQuery(){
        int javaId = 1;
        int phpId = 3;
        int antId = 7;
        int sqlId = 10;
        List<Technology> testTechnologies = new ArrayList<>();
        testTechnologies.add(technologyService.getTechnology(javaId));
        testTechnologies.add(technologyService.getTechnology(phpId));
        testTechnologies.add(technologyService.getTechnology(antId));
        testTechnologies.add(technologyService.getTechnology(sqlId));

        FilteredEventsParameter eventsParameter = new FilteredEventsParameter();
        eventsParameter.setTechnologies(testTechnologies);
        eventsParameter.setFree(true);
        SQLBuilder sqlBuilder = new SQLBuilder();
        String expected = "\"SELECT *\\nFROM events\\nWHERE (id = #{id})\"";
        String actual = sqlBuilder.selectFilteredEvent(eventsParameter);
        assertEquals(expected, actual);
    }
}
