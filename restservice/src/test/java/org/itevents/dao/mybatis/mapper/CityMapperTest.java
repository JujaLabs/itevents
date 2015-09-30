package org.itevents.dao.mybatis.mapper;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.itevents.AbstractDbTest;
import org.itevents.model.City;
import org.itevents.model.Location;
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
    @DatabaseSetup(TEST_PATH + "CityMapperTest_initial.xml")
    public void testGetCitySuccess() throws Exception {
        double kyivLatitude = 50.4505;
        double kyivLongitude = 30.523;
        Location kyivLocation = new Location(kyivLatitude, kyivLongitude);
        City expectedCity = new City("Kyiv", null, kyivLocation);
        City returnedCity = cityMapper.getCity(ID_1);
        assertEquals(expectedCity, returnedCity);
    }

    @Test
//    @Ignore
    @DatabaseSetup(TEST_PATH + "CityMapperTest_initial.xml")
    public void testGetCityFail() throws Exception {
        City returnedCity = cityMapper.getCity(ID_0);
        assertNull(returnedCity);
    }
}