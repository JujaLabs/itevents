package org.itevents.mapper;

import org.apache.ibatis.annotations.*;
import org.itevents.model.Event;
import org.itevents.model.User;
import org.itevents.model.VisitLog;

import java.util.List;

public interface VisitLogMapper {

    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "event", javaType = Event.class, column = "event_id",
                    one = @One(select = "org.itevents.mapper.EventMapper.getEvent")),
            @Result(property = "date", column = "date"),
            @Result(property = "user", javaType = User.class, column = "user_id",
                    one = @One(select = "org.itevents.mapper.UserMapper.getUser"))
    })
    @Select("SELECT id, event_id, date, user_id FROM visit_log WHERE id = #{id}")
    VisitLog getVisitLog(int id);

    @ResultMap("getVisitLog-int")
    @Select("SELECT id, event_id, date, user_id FROM visit_log")
    List<VisitLog> getAllVisitLogs();

    @ResultMap("getVisitLog-int")
    @Select("SELECT id, event_id, date, user_id FROM visit_log WHERE event_id = #{id}")
    List<VisitLog> getVisitsByEvent(Event event);

    @Insert("INSERT INTO visit_log(event_id, date, user_id) VALUES(#{event.id}, NOW(),  #{user.id})")
    @Options(useGeneratedKeys = true)
    void addVisitLog(VisitLog visitLog);

    @Delete("DELETE FROM visit_log WHERE id=#{id}")
    void removeVisitLog(VisitLog visitLog);

}