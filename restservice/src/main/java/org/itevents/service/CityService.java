package org.itevents.service;

import org.itevents.model.City;

import java.util.List;

public interface CityService {

    void addCity(City city);

    City getCity(int id);

    List<City> getAllCities();

    City removeCity(City city);
}
