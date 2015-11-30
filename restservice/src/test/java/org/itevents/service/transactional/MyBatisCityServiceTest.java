package org.itevents.service.transactional;

import org.itevents.dao.CityDao;
import org.itevents.model.City;
import org.itevents.service.CityService;
import org.itevents.test_utils.BuilderUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext.xml"})
public class MyBatisCityServiceTest {

    @InjectMocks
    @Inject
    private CityService cityService;
    @Mock
    private CityDao cityDao;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldFindCityById() throws Exception {
        int ID_1 = 1;

        cityService.getCity(ID_1);

        verify(cityDao).getCity(ID_1);
    }

    @Test
    public void shouldAddCity() throws Exception {
        City testCity = BuilderUtil.buildCityTest();

        cityService.addCity(testCity);

        verify(cityDao).addCity(testCity);
    }

    @Test
    public void shouldGetAllCities() {
        cityService.getAllCities();

        verify(cityDao).getAllCities();
    }

    @Test
    public void shouldRemoveCity() {
        City expectedCity = BuilderUtil.buildCityTest();

        when(cityDao.getCity(expectedCity.getId())).thenReturn(expectedCity);
        doNothing().when(cityDao).removeCity(expectedCity);

        City returnedCity = cityService.removeCity(expectedCity);

        verify(cityDao).getCity(expectedCity.getId());
        verify(cityDao).removeCity(expectedCity);
        assertEquals(expectedCity, returnedCity);
    }

    @Test
    public void shouldNotRemoveNonExistingCity() {
        City testCity = BuilderUtil.buildCityTest();

        when(cityDao.getCity(testCity.getId())).thenReturn(null);
        doNothing().when(cityDao).removeCity(testCity);

        City returnedCity = cityService.removeCity(testCity);

        verify(cityDao).getCity(testCity.getId());
        verify(cityDao, never()).removeCity(any(City.class));
        assertNull(returnedCity);
    }
}
