package org.itevents.mapper;

import org.apache.ibatis.annotations.*;
import org.itevents.model.*;

import java.util.Collection;

//@Transactional
public interface VisitLogMapper {

    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "event", javaType = Event.class, column = "event_id", one = @One(select = "getEvent")),
            @Result(property = "date", column = "date"),
            @Result(property = "user", javaType = User.class, column = "user_id", one = @One(select = "getUser"))
    })
    @Select("SELECT id, event_id, date, user_id FROM visit_log WHERE event_id = #{event_id}")
    Collection<VisitLog> selectVisitsByEvent(@Param("event_id") int eventId);

    @Insert("INSERT INTO visit_log(event_id, date, user_id) VALUES(#{event_id}, NOW(),  #{user_id})")
    void addVisit(@Param("event_id") int eventId, @Param("user_id") int userId);

    @Delete("DELETE FROM visit_log WHERE id =#{id}")
    void deleteVisit(@Param("id") int id);


    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "title", column = "title"),
            @Result(property = "eventDate", column = "event_date"),
            @Result(property = "createDate", column = "create_date"),
            @Result(property = "regLink", column = "reg_link"),
            @Result(property = "address", column = "address"),
            @Result(property = "contact", column = "contact"),
            @Result(property = "location", column = "id", javaType = Location.class, one = @One(select = "selectLocation"))
    })
    @Select("SELECT id, title, event_date, create_date, reg_link, address, contact FROM events WHERE id = #{id}")
    Event getEvent(int id);

    @Results({
            @Result(property = "longitude", column = "longitude"),
            @Result(property = "latitude", column = "latitude")
    })
    @Select("SELECT ST_X(point) AS longitude, ST_Y(point) AS latitude FROM events WHERE id = #{id}")
    Location selectLocation(int id);

    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "login", column = "login"),
            @Result(property = "password", column = "password"),
            @Result(property = "role", javaType = Role.class, column = "role_id", one = @One(select = "getRole"))
    })
    @Select("SELECT id, login, password FROM users WHERE id = #{id}")
    User getUser(int id);

    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
    })
    @Select("SELECT id, name FROM roles WHERE id = #{id}")
    Role getRole(int id);
}