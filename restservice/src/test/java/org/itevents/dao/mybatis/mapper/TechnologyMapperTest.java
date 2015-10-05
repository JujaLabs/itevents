package org.itevents.dao.mybatis.mapper;

import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import org.itevents.AbstractDbTest;
import org.itevents.model.Technology;
import org.itevents.util.BuilderUtil;
import org.junit.Test;

import javax.inject.Inject;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by vaa25 on 22.07.2015.
 */
public class TechnologyMapperTest extends AbstractDbTest {

    private final String TEST_PATH = PATH + "TechnologyMapperTest/";
    @Inject
    private TechnologyMapper technologyMapper;

    @Test
    @DatabaseSetup(value = TEST_PATH + "TechnologyMapperTest_initial.xml", type = DatabaseOperation.REFRESH)
    @DatabaseTearDown(value = TEST_PATH + "TechnologyMapperTest_initial.xml", type = DatabaseOperation.DELETE_ALL)
    public void testGetTechnologySuccess() throws Exception {
        Technology expectedTechnology = BuilderUtil.buildTechnologyJava();
        Technology returnedTechnology = technologyMapper.getTechnology(ID_1);
        assertEquals(expectedTechnology, returnedTechnology);
    }

    @Test
    @DatabaseSetup(value = TEST_PATH + "TechnologyMapperTest_initial.xml", type = DatabaseOperation.REFRESH)
    @DatabaseTearDown(value = TEST_PATH + "TechnologyMapperTest_initial.xml", type = DatabaseOperation.DELETE_ALL)
    public void testGetTechnologiesByEventId() throws Exception {
        List<Technology> expectedTechnologies = BuilderUtil.buildEventJava().getTechnologies();
        List<Technology> returnedTechnologies = technologyMapper.getTechnologiesByEventId(ID_1);
        assertEquals(expectedTechnologies, returnedTechnologies);
    }

    @Test
    @DatabaseSetup(value = TEST_PATH + "TechnologyMapperTest_initial.xml", type = DatabaseOperation.REFRESH)
    @DatabaseTearDown(value = TEST_PATH + "TechnologyMapperTest_initial.xml", type = DatabaseOperation.DELETE_ALL)
    public void testGetTechnologyFail() throws Exception {
        Technology returnedTechnology = technologyMapper.getTechnology(ID_0);
        assertNull(returnedTechnology);
    }
}