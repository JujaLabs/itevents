package org.itevents.service;

import org.itevents.model.City;
import org.itevents.model.Location;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext.xml"})
@Transactional
public class CityServiceTest {

    private final int ID_0 = 0;
    private final int ID_1 = 1;
    private final int SIZE_4 = 4;
    private City testCity;
    @Inject
    private CityService cityService;

    @Before
    public void setup() {
        testCity = new City("TestCity", "Test city details", new Location(0.0, 0.0));
    }

    @Test
    public void testGetCity1() throws Exception {
        double kyivLatitude = 50.4505;
        double kyivLongitude = 30.523;
        City expectedCity = new City("Kyiv", null, new Location(kyivLatitude, kyivLongitude));
        expectedCity.setId(ID_1);
        City returnedCity = cityService.getCity(ID_1);
        assertEquals(expectedCity, returnedCity);
    }

    @Test
    public void testGetCity0() throws Exception {
        City returnedCity = cityService.getCity(ID_0);
        assertNull(returnedCity);
    }


    @Test
    public void testAddCity() throws Exception {
        City expectedCity = testCity;
        cityService.addCity(expectedCity);
        City returnedCity = cityService.getCity(expectedCity.getId());
        assertEquals(expectedCity, returnedCity);
        cityService.removeCity(testCity);
    }

    @Test(expected = DuplicateKeyException.class)
    public void testAddExistingCity() throws Exception {
        City existingCity = cityService.getCity(ID_1);
        cityService.addCity(existingCity);
    }

    @Test
    public void testGetAllCities() {
        int expectedSize = SIZE_4;
        int returnedSize = cityService.getAllCities().size();
        assertEquals(expectedSize, returnedSize);
    }

    @Test
    public void testRemoveCitySuccess() {
        City expectedCity = testCity;
        cityService.addCity(expectedCity);
        City returnedCity = cityService.removeCity(expectedCity);
        assertEquals(expectedCity, returnedCity);
    }

    @Test
    public void testRemoveCityFail() {
        City returnedCity = cityService.removeCity(testCity);
        assertNull(returnedCity);
    }
}
