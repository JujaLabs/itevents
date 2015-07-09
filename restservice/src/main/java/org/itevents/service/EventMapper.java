package org.itevents.service;

import org.apache.ibatis.annotations.*;
import org.itevents.model.Event;

public interface EventMapper {
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "date", column = "date")
    })
    @Select("SELECT id, name, date FROM event WHERE id = #{id}")
    Event selectEvent(long id);

    @Insert("INSERT INTO event(name, date, point) VALUES(#{name}, #{date}, point(#{location.longitude},#{location.latitude}))")
    void insertEvent(Event event);

    @Update("UPDATE event SET name=#{name}, date =#{date}, point= point(#{location.longitude},#{location.latitude}) WHERE id =#{id}")
    void updateEvent(Event event);

    @Delete("DELETE FROM event WHERE id =#{id}")
    void deleteEvent(long id);
}