package org.itevents.service;

import org.itevents.dao.model.City;

import java.util.List;

public interface CityService {

    void addCity(City city);

    City getCity(int id);

    List<City> getAllCities();
}
