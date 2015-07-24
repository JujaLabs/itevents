package org.itevents.mapper;

import org.apache.ibatis.annotations.*;
import org.itevents.model.Event;
import org.itevents.model.User;
import org.itevents.model.VisitLog;

import java.util.List;

public interface VisitLogMapper {

    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "event", javaType = Event.class, column = "id", one = @One(select = "org.itevents.mapper.EventMapper.getEvent")),
            @Result(property = "date", column = "date"),
            @Result(property = "user", javaType = User.class, column = "id", one = @One(select = "org.itevents.mapper.UserMapper.getUser"))
    })
    @Select("SELECT id, date FROM visit_log WHERE id = #{id}")
    VisitLog getVisitLog(int id);

    @ResultMap("getVisitLog-int")
    @Select("SELECT id, date FROM visit_log WHERE event_id = #{event.id}")
    List<VisitLog> getAllVisits();

    @ResultMap("getVisitLog-int")
    @Select("SELECT id, date FROM visit_log WHERE event_id = #{event.id}")
    List<VisitLog> getVisitsByEvent(@Param("event") Event event);

    @Insert("INSERT INTO visit_log(event_id, date, user_id) VALUES(#{event.id}, NOW(),  #{user.id})")
    void addVisit(@Param("event") Event event, @Param("user") User user);

    @Delete("DELETE FROM visit_log ")
    void removeVisits();

    @Delete("DELETE FROM visit_log WHERE id=#{visitLog.id")
    void removeVisitLog(VisitLog visitLog);

}