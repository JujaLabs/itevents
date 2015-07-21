package org.itevents.mapper;

import org.apache.ibatis.annotations.*;
import org.itevents.model.Event;
import org.itevents.model.Location;

public interface CityMapper {
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "title", column = "title"),
            @Result(property = "location", javaType = Location.class,
                    column = "id", one = @One(select = "org.itevents.mapper.LocationMapper.selectLocation"))
    })
    @Select("SELECT id, title FROM cities WHERE id = #{id}")
    Event getCity(int id);


    @Insert("INSERT INTO cities(id, title, point) VALUES(" +
            "#{id}, " +
            "#{title}, " +
            "ST_MakePoint(#{location.longitude},#{location.latitude})")
    void addCity(Event event);

    @Delete("DELETE FROM cities WHERE id =#{id}")
    void removeCity(int id);
}