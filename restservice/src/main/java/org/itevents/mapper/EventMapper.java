package org.itevents.mapper;

import org.apache.ibatis.annotations.*;
import org.itevents.model.Event;
import org.itevents.model.Location;

import java.util.List;

public interface EventMapper {
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "title", column = "title"),
            @Result(property = "eventDate", column = "event_date"),
            @Result(property = "createDate", column = "create_date"),
            @Result(property = "regLink", column = "reg_link"),
            @Result(property = "address", column = "address"),
            @Result(property = "contact", column = "contact"),
            @Result(property = "location", column = "id", javaType = Location.class, one=@One(select="org.itevents.mapper.LocationMapper.selectLocation"))
    })
    @Select("SELECT id, title, event_date, create_date, reg_link, address, contact FROM events WHERE id = #{id}")
    Event getEvent(int id);

    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "title", column = "title"),
            @Result(property = "eventDate", column = "event_date"),
            @Result(property = "createDate", column = "create_date"),
            @Result(property = "regLink", column = "reg_link"),
            @Result(property = "address", column = "address"),
            @Result(property = "contact", column = "contact"),
            @Result(property = "location", column = "id", javaType = Location.class, one=@One(select="org.itevents.mapper.LocationMapper.selectLocation"))
    })
    @Select("SELECT id, title, event_date, create_date, reg_link, address, contact FROM events")
    List<Event> getAllEvents();

    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "title", column = "title"),
            @Result(property = "eventDate", column = "event_date"),
            @Result(property = "createDate", column = "create_date"),
            @Result(property = "regLink", column = "reg_link"),
            @Result(property = "address", column = "address"),
            @Result(property = "contact", column = "contact"),
            @Result(property = "location", column = "id", javaType = Location.class, one=@One(select="org.itevents.mapper.LocationMapper.selectLocation"))
    })
    @Select("SELECT id, title, event_date, create_date, reg_link, address, contact FROM events WHERE ST_DWithin(point)::geography, ST_MakePoint(#{location.longitude},#{location.latitude})::geography, #{radius})")
    List<Event> getFutureEventsInRadius(@Param("location") Location location, @Param("radius") int radius);

    @Insert("INSERT INTO events(title, event_date, create_date, reg_link, address, point, contact) VALUES(#{title}, #{eventDate}, #{createDate}, #{regLink}, #{address}, ST_MakePoint(#{location.longitude},#{location.latitude}), #{contact})")
    void addEvent(Event event);

    @Update("UPDATE events SET title=#{title}, event_date=#{eventDate}, create_date=#{createDate}, reg_link=#{regLink}, address=#{address}, point= point(#{location.longitude},#{location.latitude}), contact=#{contact} WHERE id =#{id}")
    void updateEvent(Event event);

    @Delete("DELETE FROM events WHERE id =#{id}")
    void removeEvent(int id);
}