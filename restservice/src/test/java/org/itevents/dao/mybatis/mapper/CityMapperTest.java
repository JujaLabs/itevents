package org.itevents.dao.mybatis.mapper;

import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import org.itevents.AbstractDbTest;
import org.itevents.model.City;
import org.itevents.util.BuilderUtil;
import org.junit.Test;
import org.springframework.dao.DuplicateKeyException;

import javax.inject.Inject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by vaa25 on 22.07.2015.
 */

public class CityMapperTest extends AbstractDbTest {

    private final int SIZE_4 = 4;
    private final String TEST_PATH = PATH + "CityMapperTest/";
    @Inject
    private CityMapper cityMapper;

    @Test
    @DatabaseSetup(value = TEST_PATH + "CityMapperTest_initial.xml", type = DatabaseOperation.REFRESH)
    @DatabaseTearDown(value = TEST_PATH + "CityMapperTest_initial.xml", type = DatabaseOperation.DELETE_ALL)
    public void testGetCitySuccess() throws Exception {
        City expectedCity = BuilderUtil.buildCityKyiv();
        City returnedCity = cityMapper.getCity(ID_1);
        assertEquals(expectedCity, returnedCity);
    }

    @Test
    @DatabaseSetup(value = TEST_PATH + "CityMapperTest_initial.xml", type = DatabaseOperation.REFRESH)
    @DatabaseTearDown(value = TEST_PATH + "CityMapperTest_initial.xml", type = DatabaseOperation.DELETE_ALL)
    public void testGetCityFail() throws Exception {
        City returnedCity = cityMapper.getCity(ID_0);
        assertNull(returnedCity);
    }

    @Test
    @DatabaseSetup(value = TEST_PATH + "CityMapperTest_initial.xml", type = DatabaseOperation.REFRESH)
    @ExpectedDatabase(value = TEST_PATH + "testAddCity_expected.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    @DatabaseTearDown(value = TEST_PATH + "CityMapperTest_initial.xml", type = DatabaseOperation.DELETE_ALL)
    public void testAddCity() throws Exception {
        City testCity = BuilderUtil.buildCityTest();
        cityMapper.addCity(testCity);
    }

    @Test(expected = DuplicateKeyException.class)
    @DatabaseSetup(value = TEST_PATH + "testAddExistingCity_Initial.xml", type = DatabaseOperation.REFRESH)
    @ExpectedDatabase(value = TEST_PATH + "testAddCity_expected.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    @DatabaseTearDown(value = TEST_PATH + "CityMapperTest_initial.xml", type = DatabaseOperation.DELETE_ALL)
    public void testAddExistingCity() throws Exception {
        City testCity = BuilderUtil.buildCityTest();
        cityMapper.addCity(testCity);
    }

    @Test
    @DatabaseSetup(value = TEST_PATH + "CityMapperTest_initial.xml", type = DatabaseOperation.REFRESH)
    @DatabaseTearDown(value = TEST_PATH + "CityMapperTest_initial.xml", type = DatabaseOperation.DELETE_ALL)
    public void testGetAllCities() {
        int expectedSize = SIZE_4;
        int returnedSize = cityMapper.getAllCities().size();
        assertEquals(expectedSize, returnedSize);
    }

    @Test
    @DatabaseSetup(value = TEST_PATH + "testAddExistingCity_Initial.xml", type = DatabaseOperation.REFRESH)
    @ExpectedDatabase(value = TEST_PATH + "CityMapperTest_initial.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    @DatabaseTearDown(value = TEST_PATH + "CityMapperTest_initial.xml", type = DatabaseOperation.DELETE_ALL)
    public void testRemoveCitySuccess() {
        City testCity = BuilderUtil.buildCityTest();
        cityMapper.removeCity(testCity);
    }

    @Test
    @DatabaseSetup(value = TEST_PATH + "CityMapperTest_initial.xml", type = DatabaseOperation.REFRESH)
    @DatabaseTearDown(value = TEST_PATH + "CityMapperTest_initial.xml", type = DatabaseOperation.DELETE_ALL)
    public void testRemoveCityFail() {
        City testCity = BuilderUtil.buildCityTest();
        cityMapper.removeCity(testCity);
    }
}