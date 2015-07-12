package org.itevents.mapper;

import org.apache.ibatis.annotations.*;
import org.itevents.model.Event;
import org.itevents.model.Location;

import java.util.List;

public interface EventMapper {
    @Select("SELECT id, title, event_date, create_date, reg_link, address, contact FROM events WHERE id = #{id}")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "eventDate", column = "event_date"),
            @Result(property = "createDate", column = "create_date"),
            @Result(property = "regLink", column = "reg_link"),
            @Result(property = "location", column = "id", javaType = Location.class, one=@One(select="selectLocation"))
    })
    Event getEvent(long id);

    @Select("SELECT id, title, event_date, create_date, reg_link, address, contact FROM events")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "eventDate", column = "event_date"),
            @Result(property = "createDate", column = "create_date"),
            @Result(property = "regLink", column = "reg_link"),
            @Result(property = "location", column = "id", javaType = Location.class, one=@One(select="selectLocation"))
    })
    List<Event> getAllEvents();

    @Select("SELECT id, title, event_date, create_date, reg_link, address, contact FROM events WHERE ST_DWithin(point)::geography, ST_MakePoint(#{location.longitude},#{location.latitude})::geography, #{radius})")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "eventDate", column = "event_date"),
            @Result(property = "createDate", column = "create_date"),
            @Result(property = "regLink", column = "reg_link"),
            @Result(property = "location", column = "id", javaType = Location.class, one=@One(select="selectLocation"))
    })
    List<Event> getFutureEventsInRadius(@Param("location")Location location, @Param("radius")long radius);

    @Insert("INSERT INTO events(title, event_date, create_date, reg_link, address, point, contact) VALUES(#{name}, #{eventDate}, #{createDate}, #{regLink}, #{address}, point(#{location.longitude},#{location.latitude}), #{contact})")
    void addEvent(Event event);

    @Update("UPDATE events SET title=#{title}, event_date=#{eventDate}, create_date=#{createDate}, reg_link=#{regLink}, address=#{address}, point= point(#{location.longitude},#{location.latitude}), contact=#{contact} WHERE id =#{id}")
    void updateEvent(Event event);

    @Delete("DELETE FROM events WHERE id =#{id}")
    void removeEvent(long id);

    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "longitude", column = "longitude"),
            @Result(property = "latitude", column = "latitude")
    })
    @Select("SELECT ST_X(point) AS longitude, ST_Y(point) AS latitude FROM events WHERE id = #{id}")
    Location selectLocation(long id);
}