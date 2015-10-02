package org.itevents.dao.mybatis.mapper;

import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.itevents.AbstractDbTest;
import org.itevents.model.City;
import org.itevents.util.BuilderUtil;
import org.junit.Test;

import javax.inject.Inject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by vaa25 on 22.07.2015.
 */

public class CityMapperTest extends AbstractDbTest {

    private final String TEST_PATH = PATH + "CityMapperTest/";
    @Inject
    private CityMapper cityMapper;

    @Test
    @DatabaseSetup(value = TEST_PATH + "CityMapperTest_initial.xml", type = DatabaseOperation.REFRESH)
    public void testGetCitySuccess() throws Exception {
        City expectedCity = BuilderUtil.buildCityKyiv();
        City returnedCity = cityMapper.getCity(ID_1);
        assertEquals(expectedCity, returnedCity);
    }

    @Test
    @DatabaseSetup(value = TEST_PATH + "CityMapperTest_initial.xml", type = DatabaseOperation.REFRESH)
    public void testGetCityFail() throws Exception {
        City returnedCity = cityMapper.getCity(ID_0);
        assertNull(returnedCity);
    }
}