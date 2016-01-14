package org.itevents.dao;

import org.itevents.model.City;

import java.util.List;

public interface CityDao {

    City getCity(int id);

    List<City> getAllCities();

    void addCity(City city);
}