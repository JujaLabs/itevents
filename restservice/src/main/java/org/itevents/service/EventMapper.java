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
    Event selectEvent(Long id);

    @Select("SELECT id, name, date FROM event")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "location", column = "id", javaType = Location.class, one=@One(select="selectLocation"))
    })
    List<Event> selectAllEvents();

    /*@Select("SELECT id, name, date FROM event WHERE ST_DWithin(ST_GeogFromText('SRID=4326;POINT(' || point[0] || ' ' || point[1] || ')'), ST_GeogFromText('SRID=4326;POINT(#{location.longitude} #{location.latitude})'), #{radius});")*/
    @Select("SELECT id, name, date FROM event WHERE ST_DWithin(ST_MakePoint(point[0],point[1])::geography, ST_MakePoint(#{location.longitude},#{location.latitude})::geography, #{radius})")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "location", column = "id", javaType = Location.class, one=@One(select="selectLocation"))
    })
    List<Event> selectAllEventsInRadius(@Param("location")Location location, @Param("radius")Long radius);

    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "longitude", column = "longitude"),
            @Result(property = "latitude", column = "latitude")
    })
    @Select("SELECT point[0] AS longitude, point[1] AS latitude FROM event WHERE id = #{id}")
    Location selectLocation(Long id);

    @Insert("INSERT INTO event(name, date, point) VALUES(#{name}, #{date}, point(#{location.longitude},#{location.latitude}))")
    void insertEvent(Event event);

    @Update("UPDATE event SET name=#{name}, date =#{date}, point= point(#{location.longitude},#{location.latitude}) WHERE id =#{id}")
    void updateEvent(Event event);

    @Delete("DELETE FROM event WHERE id =#{id}")
    void deleteEvent(Long id);
}