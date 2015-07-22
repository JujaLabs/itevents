package org.itevents.mapper;

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
    @Autowired
    private CityMapper cityMapper;

    @Test
    public void testGetCity1() throws Exception {
        City expected = new City(1, "Kyiv", new Location(50.450500, 30.523000));
        assertEquals(expected, cityMapper.getCity(1));
    }

    @Test
    public void testGetCity0() throws Exception {
        assertNull(cityMapper.getCity(0));
    }
}