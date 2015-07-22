package org.itevents.mapper;

import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.itevents.model.City;
import org.itevents.model.Location;

public interface CityMapper {
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "location", javaType = Location.class,
                    column = "id", one = @One(select = "org.itevents.mapper.LocationMapper.getCityLocation"))
    })
    @Select("SELECT id, name FROM cities WHERE id = #{id}")
    City getCity(int id);
}