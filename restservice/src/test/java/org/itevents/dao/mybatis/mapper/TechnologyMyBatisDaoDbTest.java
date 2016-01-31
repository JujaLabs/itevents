package org.itevents.dao.mybatis.mapper;

import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import org.itevents.AbstractDbTest;
import org.itevents.dao.exception.EntityNotFoundDaoException;
import org.itevents.dao.mybatis.sql_session_dao.TechnologyMyBatisDao;
import org.itevents.model.Technology;
import org.itevents.test_utils.BuilderUtil;
import org.junit.Test;
import org.springframework.dao.DuplicateKeyException;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by vaa25 on 22.07.2015.
 */
@DatabaseSetup(value = "file:src/test/resources/dbunit/TechnologyMapperTest/TechnologyMapperTest_initial.xml",
        type = DatabaseOperation.REFRESH)
@DatabaseTearDown(value = "file:src/test/resources/dbunit/TechnologyMapperTest/testGetTechnologiesByEventId_initial.xml",
        type = DatabaseOperation.DELETE_ALL)
public class TechnologyMyBatisDaoDbTest extends AbstractDbTest {

    private final String TEST_PATH = PATH + "TechnologyMapperTest/";
    @Inject
    private TechnologyMyBatisDao technologyMyBatisDao;

    @Test
    public void shouldFindTechnologyById() throws Exception {
        Technology expectedTechnology = BuilderUtil.buildTechnologyJava();
        Technology returnedTechnology = technologyMyBatisDao.getTechnology(ID_1);
        assertEquals(expectedTechnology, returnedTechnology);
    }

    @Test
    @DatabaseSetup(value = TEST_PATH + "testGetTechnologiesByEventId_initial.xml", type = DatabaseOperation.REFRESH)
    @DatabaseTearDown(value = TEST_PATH + "testGetTechnologiesByEventId_initial.xml",
            type = DatabaseOperation.DELETE_ALL)
    public void shouldFindTechnologiesInGivenEvent() throws Exception {
        List<Technology> expectedTechnologies = new ArrayList<>();
        expectedTechnologies.add(BuilderUtil.buildTechnologyJava());
        expectedTechnologies.add(BuilderUtil.buildTechnologyLiquibase());
        expectedTechnologies.add(BuilderUtil.buildTechnologyMyBatis());
        expectedTechnologies.add(BuilderUtil.buildTechnologySpring());
        List<Technology> returnedTechnologies = technologyMyBatisDao.getTechnologiesByEventId(ID_1);
        assertEquals(expectedTechnologies, returnedTechnologies);

    }

    @Test(expected = EntityNotFoundDaoException.class)
    public void shouldThrowEntityNotFoundDaoExceptionWhenTechnologyIsAbsent() throws Exception {
        technologyMyBatisDao.getTechnology(ABSENT_ID);
    }

    @Test
    public void shouldGetAllTechnologies() throws Exception {
        int expectedSize = 10;
        int returnedSize = technologyMyBatisDao.getAllTechnologies().size();
        assertEquals(expectedSize, returnedSize);
    }

    @Test
    public void shouldFindTechnologiesByNames() throws Exception {
        String[] names = {"Java", "JavaScript"};
        List<Technology> expectedTechnologies = new ArrayList<>();
        expectedTechnologies.add(BuilderUtil.buildTechnologyJava());
        expectedTechnologies.add(BuilderUtil.buildTechnologyJavaScript());
        List<Technology> returnedTechnologies = technologyMyBatisDao.getTechnologiesByNames(names);
        assertEquals(expectedTechnologies, returnedTechnologies);
    }

    @Test
    @ExpectedDatabase(value = TEST_PATH + "testAddTechnology_expected.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    public void shouldAddTechnology() throws Exception {
        Technology expectedTechnology = BuilderUtil.buildTechnologyTest();
        technologyMyBatisDao.addTechnology(expectedTechnology);
    }

    @Test(expected = DuplicateKeyException.class)
    public void shouldThrowDuplicateKeyExceptionWhenAddExistingTechnology() throws Exception {
        Technology existingTechnology = BuilderUtil.buildTechnologyJava();
        technologyMyBatisDao.addTechnology(existingTechnology);
    }
}