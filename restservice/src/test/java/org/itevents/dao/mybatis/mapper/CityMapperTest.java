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

    private final int ID_0 = 0;
    private final int ID_1 = 1;
    private final String TEST_PATH = PATH + "CityMapperTest/";
    @Inject
    private CityMapper cityMapper;

    @Test
    @DatabaseSetup(TEST_PATH + "CityMapperTest_initial.xml")
    public void testGetCity1() throws Exception {
        double kyivLatitude = 50.4505;
        double kyivLongitude = 30.523;
        City expectedCity = new City("Kyiv", null, new Location(kyivLatitude, kyivLongitude));
        expectedCity.setId(ID_1);
        City returnedCity = cityMapper.getCity(ID_1);
        assertEquals(expectedCity, returnedCity);
    }

    @Test
    @DatabaseSetup(TEST_PATH + "CityMapperTest_initial.xml")
    public void testGetCity0() throws Exception {
        City returnedCity = cityMapper.getCity(ID_0);
        assertNull(returnedCity);
    }
}