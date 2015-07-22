package org.itevents.mapper;

import org.apache.ibatis.annotations.*;
import org.itevents.model.Event;
import org.itevents.model.User;
import org.itevents.model.VisitLog;

public interface VisitLogMapper {

    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "event", javaType = Event.class, column = "event_id", one = @One(select = "org.itevents.mapper.EventMapper.getEvent")),
            @Result(property = "date", column = "date"),
            @Result(property = "user", javaType = User.class, column = "user_id", one = @One(select = "org.itevents.mapper.UserMapper.getUser"))
    })
    @Select("SELECT id, event_id, date, user_id FROM visit_log WHERE event_id = #{event_id}")
    VisitLog[] getVisitsByEvent(@Param("event_id") int eventId);

    @Insert("INSERT INTO visit_log(event_id, date, user_id) VALUES(#{event_id}, NOW(),  #{user_id})")
    void addVisit(@Param("event_id") int eventId, @Param("user_id") int userId);

    @Delete("DELETE FROM visit_log ")
    void deleteVisits();

}