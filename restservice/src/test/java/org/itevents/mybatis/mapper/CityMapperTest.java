package org.itevents.mybatis.mapper;

import org.itevents.model.City;
import org.itevents.model.Location;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by vaa25 on 22.07.2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext.xml"})
public class CityMapperTest {

    private final static int ID_0 = 0;
    private final static int ID_1 = 1;

    @Autowired
    private CityMapper cityMapper;

    @Test
    public void testGetCity1() throws Exception {
        double kyivLatitude = 50.4505;
        double kyivLongitude = 30.523;
        City expectedCity = new City(ID_1, "Kyiv", new Location(kyivLatitude, kyivLongitude));
        City returnedCity = cityMapper.getCity(ID_1);
        assertEquals(expectedCity, returnedCity);
    }

    @Test
    public void testGetCity0() throws Exception {
        City returnedCity = cityMapper.getCity(ID_0);
        assertNull(returnedCity);
    }
}