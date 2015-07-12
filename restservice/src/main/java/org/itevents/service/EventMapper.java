package org.itevents.service;

import org.apache.ibatis.annotations.*;
import org.itevents.model.Event;
import org.itevents.model.Location;

import java.util.List;

public interface EventMapper {
    @Select("SELECT id, name, date FROM event WHERE id = #{id}")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "location", column = "id", javaType = Location.class, one=@One(select="selectLocation"))
    })
    Event getEvent(long id);

    @Select("SELECT id, name, date FROM event")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "location", column = "id", javaType = Location.class, one=@One(select="selectLocation"))
    })
    List<Event> getAllEvents();

    @Select("SELECT id, name, date FROM event WHERE ST_DWithin(ST_MakePoint(point[0],point[1])::geography, ST_MakePoint(#{location.longitude},#{location.latitude})::geography, #{radius})")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "location", column = "id", javaType = Location.class, one=@One(select="selectLocation"))
    })
    List<Event> getFutureEventsInRadius(@Param("location")Location location, @Param("radius")long radius);

    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "longitude", column = "longitude"),
            @Result(property = "latitude", column = "latitude")
    })
    @Select("SELECT point[0] AS longitude, point[1] AS latitude FROM event WHERE id = #{id}")
    Location selectLocation(long id);

    @Insert("INSERT INTO event(name, date, point) VALUES(#{name}, #{date}, point(#{location.longitude},#{location.latitude}))")
    void addEvent(Event event);

    @Update("UPDATE event SET name=#{name}, date =#{date}, point= point(#{location.longitude},#{location.latitude}) WHERE id =#{id}")
    void updateEvent(Event event);

    @Delete("DELETE FROM event WHERE id =#{id}")
    void removeEvent(long id);
}