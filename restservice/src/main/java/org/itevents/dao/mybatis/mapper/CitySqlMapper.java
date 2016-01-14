package org.itevents.dao.mybatis.mapper;

import org.apache.ibatis.annotations.*;
import org.itevents.dao.CityDao;
import org.itevents.model.City;
import org.itevents.model.Location;

import java.util.List;

public interface CitySqlMapper extends CityDao {
    @Results(value = {
            @Result(property = "id", column = "id", id = true),
            @Result(property = "location", javaType = Location.class,
                    column = "id", one = @One(select = "org.itevents.dao.mybatis.mapper.LocationSqlMapper.getCityLocation"))
    })
    @Override
    @Select("SELECT id, name, details FROM city WHERE id = #{id}")
    City getCity(int id);

    @Override
    @ResultMap("getCity-int")
    @Select("SELECT * FROM city ORDER BY name")
    List<City> getAllCities();

    @Override
    @Insert("INSERT INTO city(name, details, point) " +
            "VALUES(#{name}, #{details}, ST_MakePoint(#{location.longitude},#{location.latitude}))")
    @Options(useGeneratedKeys = true)
    void addCity(City city);
}