package org.itevents.mybatis.mapper;

import org.apache.ibatis.annotations.*;
import org.itevents.model.City;
import org.itevents.model.Location;

public interface CityMapper {
    @Results(value = {
            @Result(property = "id", column = "id", id = true),
            @Result(property = "location", javaType = Location.class,
                    column = "id", one = @One(select = "org.itevents.mybatis.mapper.LocationMapper.getCityLocation"))
    })
    @Select("SELECT id, name FROM city WHERE id = #{id}")
    City getCity(int id);

    @Insert("INSERT INTO city(name, point) " +
            "VALUES(#{name}, ST_MakePoint(#{location.longitude},#{location.latitude}")
    @Options(useGeneratedKeys = true)
    void addCity(City city);

    @Update("UPDATE city SET name=#{name}, point=ST_MakePoint(#{location.longitude},#{location.latitude} " +
            "WHERE id =#{id}")
    void updateCity(City city);

    @Delete("DELETE FROM city WHERE id =#{id}")
    void removeCity(int id);

}