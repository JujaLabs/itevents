package org.itevents.dao.mybatis.mapper;

import org.itevents.model.City;
import org.itevents.model.Location;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by vaa25 on 22.07.2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext.xml"})
@Transactional
public class CityMapperTest {

    private final int ID_0 = 0;
    private final int ID_1 = 1;

    @Inject
    private CityMapper cityMapper;

    @Test
    public void testGetCity1() throws Exception {
        double kyivLatitude = 50.4505;
        double kyivLongitude = 30.523;
        City expectedCity = new City("Kyiv", null, new Location(kyivLatitude, kyivLongitude));
        expectedCity.setId(ID_1);
        City returnedCity = cityMapper.getCity(ID_1);
        assertEquals(expectedCity, returnedCity);
    }

    @Test
    public void testGetCity0() throws Exception {
        City returnedCity = cityMapper.getCity(ID_0);
        assertNull(returnedCity);
    }
}