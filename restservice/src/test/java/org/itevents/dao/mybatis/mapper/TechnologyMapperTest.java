package org.itevents.dao.mybatis.mapper;

import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import org.itevents.AbstractDbTest;
import org.itevents.model.Technology;
import org.itevents.util.BuilderUtil;
import org.junit.Test;
import org.springframework.dao.DuplicateKeyException;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by vaa25 on 22.07.2015.
 */
public class TechnologyMapperTest extends AbstractDbTest {

    private final String TEST_PATH = PATH + "TechnologyMapperTest/";
    private final int SIZE_11 = 11;
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
    @DatabaseSetup(value = TEST_PATH + "testGetTechnologiesByEventId_initial.xml", type = DatabaseOperation.REFRESH)
    @DatabaseTearDown(value = TEST_PATH + "testGetTechnologiesByEventId_initial.xml", type = DatabaseOperation.DELETE_ALL)
    public void testGetTechnologiesByEventId() throws Exception {
        Set<Technology> expectedTechnologies = BuilderUtil.buildEventJava().getTechnologies();
        Set<Technology> returnedTechnologies = technologyMapper.getTechnologiesByEventId(ID_1);
        assertEquals(expectedTechnologies, returnedTechnologies);
    }

    @Test
    @DatabaseSetup(value = TEST_PATH + "TechnologyMapperTest_initial.xml", type = DatabaseOperation.REFRESH)
    @DatabaseTearDown(value = TEST_PATH + "TechnologyMapperTest_initial.xml", type = DatabaseOperation.DELETE_ALL)
    public void testGetTechnologyFail() throws Exception {
        Technology returnedTechnology = technologyMapper.getTechnology(ID_0);
        assertNull(returnedTechnology);
    }

    @Test
    @DatabaseSetup(value = TEST_PATH + "TechnologyMapperTest_initial.xml", type = DatabaseOperation.REFRESH)
    @DatabaseTearDown(value = TEST_PATH + "TechnologyMapperTest_initial.xml", type = DatabaseOperation.DELETE_ALL)
    public void testGetAllTechnologies() throws Exception {
        int expectedSize = SIZE_11;
        int returnedSize = technologyMapper.getAllTechnologies().size();
        assertEquals(expectedSize, returnedSize);
    }

    @Test
    @DatabaseSetup(value = TEST_PATH + "TechnologyMapperTest_initial.xml", type = DatabaseOperation.REFRESH)
    @DatabaseTearDown(value = TEST_PATH + "TechnologyMapperTest_initial.xml", type = DatabaseOperation.DELETE_ALL)
    public void testGetTechnologiesByName() throws Exception {
        String[] names = {"Java", "JavaScript"};
        List<Technology> expectedList = new ArrayList<>();
        expectedList.add(BuilderUtil.buildTechnologyJava());
        expectedList.add(BuilderUtil.buildTechnologyJavaScript());
        List<Technology> returnedTechnologies = technologyMapper.getTechnologiesByNames(names);
        assertEquals(expectedList, returnedTechnologies);
    }

    @Test
    @DatabaseSetup(value = TEST_PATH + "TechnologyMapperTest_initial.xml", type = DatabaseOperation.REFRESH)
    @ExpectedDatabase(value = TEST_PATH + "testAddTechnology_expected.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    @DatabaseTearDown(value = TEST_PATH + "TechnologyMapperTest_initial.xml", type = DatabaseOperation.DELETE_ALL)
    public void testAddTechnology() throws Exception {
        Technology expectedTechnology = BuilderUtil.buildTechnologyTest();
        technologyMapper.addTechnology(expectedTechnology);
    }

    @Test(expected = DuplicateKeyException.class)
    @DatabaseSetup(value = TEST_PATH + "TechnologyMapperTest_initial.xml", type = DatabaseOperation.REFRESH)
    @DatabaseTearDown(value = TEST_PATH + "TechnologyMapperTest_initial.xml", type = DatabaseOperation.DELETE_ALL)
    public void testAddExistingCity() throws Exception {
        Technology existingTechnology = BuilderUtil.buildTechnologyJava();
        technologyMapper.addTechnology(existingTechnology);
    }

    @Test
    @DatabaseSetup(value = TEST_PATH + "testRemoveTechnology_initial.xml", type = DatabaseOperation.REFRESH)
    @ExpectedDatabase(value = TEST_PATH + "TechnologyMapperTest_initial.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    @DatabaseTearDown(value = TEST_PATH + "TechnologyMapperTest_initial.xml", type = DatabaseOperation.DELETE_ALL)
    public void testRemoveTechnologySuccess() throws Exception {
        Technology expectedTechnology = BuilderUtil.buildTechnologyTest();
        technologyMapper.removeTechnology(expectedTechnology);
    }

    @Test
    @DatabaseSetup(value = TEST_PATH + "testRemoveTechnology_initial.xml", type = DatabaseOperation.REFRESH)
    @ExpectedDatabase(value = TEST_PATH + "TechnologyMapperTest_initial.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    @DatabaseTearDown(value = TEST_PATH + "TechnologyMapperTest_initial.xml", type = DatabaseOperation.DELETE_ALL)
    public void testRemoveTechnologyFail() {
        Technology expectedTechnology = BuilderUtil.buildTechnologyTest();
        technologyMapper.removeTechnology(expectedTechnology);
    }
}