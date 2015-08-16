package org.itevents.service;

import org.itevents.dao.CityDao;
import org.itevents.model.City;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

@Service("cityService")
@Transactional
public class CityServiceImpl implements CityService {

    @Inject
    private CityDao cityDao;

    @Override
    public void addCity(City city) {
        cityDao.addCity(city);
    }

    @Override
    public City getCity(int id) {
        return cityDao.getCity(id);
    }

    @Override
    public List<City> getAllCities() {
        return cityDao.getAllCities();
    }

    @Override
    public City removeCity(City city) {
        City deletingCity = cityDao.getCity(city.getId());
        if (deletingCity != null) {
            cityDao.removeCity(city);
        }
        return deletingCity;
    }
}
