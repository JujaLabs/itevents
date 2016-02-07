package org.itevents.dao.mybatis.mapper;

import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import org.itevents.AbstractDbTest;
import org.itevents.dao.exception.EntityNotFoundDaoException;
import org.itevents.dao.mybatis.sql_session_dao.CityMyBatisDao;
import org.itevents.model.City;
import org.itevents.test_utils.BuilderUtil;
import org.junit.Test;
import org.springframework.dao.DuplicateKeyException;

import javax.inject.Inject;

import static org.junit.Assert.assertEquals;

/**
 * Created by vaa25 on 22.07.2015.
 */

@DatabaseSetup(value = "file:src/test/resources/dbunit/CityMapperTest/CityMapperTest_initial.xml",
        type = DatabaseOperation.REFRESH)
@DatabaseTearDown(value = "file:src/test/resources/dbunit/CityMapperTest/CityMapperTest_initial.xml",
        type = DatabaseOperation.DELETE_ALL)
public class CityMyBatisDaoDbTest extends AbstractDbTest {

    private final String TEST_PATH = PATH + "CityMapperTest/";
    @Inject
    private CityMyBatisDao cityMyBatisDao;

    @Test
    public void shouldFindCityById() throws Exception {
        City expectedCity = BuilderUtil.buildCityKyiv();
        City returnedCity = cityMyBatisDao.getCity(ID_1);
        assertEquals(expectedCity, returnedCity);
    }

    @Test(expected = EntityNotFoundDaoException.class)
    public void shouldThrowEntityNotFoundDaoExceptionWhenCityIsAbsent() throws Exception {
        cityMyBatisDao.getCity(ABSENT_ID);
    }

    @Test
    @ExpectedDatabase(value = TEST_PATH + "testAddCity_expected.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    public void shouldAddCity() throws Exception {
        City testCity = BuilderUtil.buildCityTest();
        cityMyBatisDao.addCity(testCity);
    }

    @Test(expected = DuplicateKeyException.class)
    @DatabaseSetup(value = TEST_PATH + "testAddExistingCity_initial.xml", type = DatabaseOperation.REFRESH)
    @ExpectedDatabase(value = TEST_PATH + "testAddCity_expected.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    public void shouldThrowDuplicateKeyExceptionWhenAddExistingCity() throws Exception {
        City testCity = BuilderUtil.buildCityTest();
        cityMyBatisDao.addCity(testCity);
    }

    @Test
    public void shouldGetAllCities() throws Exception {
        int expectedSize = 4;
        int returnedSize = cityMyBatisDao.getAllCities().size();
        assertEquals(expectedSize, returnedSize);
    }

}