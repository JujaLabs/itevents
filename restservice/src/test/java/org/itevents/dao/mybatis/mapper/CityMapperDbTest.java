package org.itevents.dao.mybatis.mapper;

import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import org.itevents.AbstractDbTest;
import org.itevents.model.City;
import org.itevents.test_utils.BuilderUtil;
import org.junit.Test;
import org.springframework.dao.DuplicateKeyException;

import javax.inject.Inject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by vaa25 on 22.07.2015.
 */

@DatabaseSetup(value = "file:src/test/resources/dbunit/CityMapperTest/CityMapperTest_initial.xml",
        type = DatabaseOperation.REFRESH)
@DatabaseTearDown(value = "file:src/test/resources/dbunit/CityMapperTest/CityMapperTest_initial.xml",
        type = DatabaseOperation.DELETE_ALL)
public class CityMapperDbTest extends AbstractDbTest {

    private final String TEST_PATH = PATH + "CityMapperTest/";
    @Inject
    private CityMapper cityMapper;

    @Test
    public void shouldFindCityById() throws Exception {
        City expectedCity = BuilderUtil.buildCityKyiv();
        City returnedCity = cityMapper.getCity(ID_1);
        assertEquals(expectedCity, returnedCity);
    }

    @Test
    public void expectNullWhenCityIsAbsent() throws Exception {
        City returnedCity = cityMapper.getCity(ABSENT_ID);
        assertNull(returnedCity);
    }

    @Test
    @ExpectedDatabase(value = TEST_PATH + "testAddCity_expected.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    public void shouldAddCity() throws Exception {
        City testCity = BuilderUtil.buildCityTest();
        cityMapper.addCity(testCity);
    }

    @Test(expected = DuplicateKeyException.class)
    @DatabaseSetup(value = TEST_PATH + "testAddExistingCity_initial.xml", type = DatabaseOperation.REFRESH)
    @ExpectedDatabase(value = TEST_PATH + "testAddCity_expected.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    public void shouldThrowDuplicateKeyExceptionWhenAddExistingCity() throws Exception {
        City testCity = BuilderUtil.buildCityTest();
        cityMapper.addCity(testCity);
    }

    @Test
    public void shouldGetAllCities() {
        int expectedSize = 4;
        int returnedSize = cityMapper.getAllCities().size();
        assertEquals(expectedSize, returnedSize);
    }

    @Test
    @DatabaseSetup(value = TEST_PATH + "testAddExistingCity_initial.xml", type = DatabaseOperation.REFRESH)
    @ExpectedDatabase(value = TEST_PATH + "CityMapperTest_initial.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    public void shouldRemoveCity() {
        City testCity = BuilderUtil.buildCityTest();
        cityMapper.removeCity(testCity);
    }

    @Test
    @ExpectedDatabase(value = TEST_PATH + "CityMapperTest_initial.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    public void shouldNotRemoveAbsentCity() {
        City testCity = BuilderUtil.buildCityTest();
        cityMapper.removeCity(testCity);
    }
}