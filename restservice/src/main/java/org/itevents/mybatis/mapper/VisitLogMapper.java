package org.itevents.mybatis.mapper;

import org.apache.ibatis.annotations.*;
import org.itevents.dao.VisitLogDao;
import org.itevents.model.Event;
import org.itevents.model.User;
import org.itevents.model.VisitLog;

import java.util.List;

public interface VisitLogMapper extends VisitLogDao {

    @Results({
            @Result(property = "id", column = "id", id = true),
            @Result(property = "event", javaType = Event.class, column = "event_id",
                    one = @One(select = "org.itevents.mybatis.mapper.EventMapper.getEvent")),
            @Result(property = "user", javaType = User.class, column = "user_id",
                    one = @One(select = "org.itevents.mybatis.mapper.UserMapper.getUser"))
    })
    @Select("SELECT * FROM visit_log WHERE id = #{id}")
    VisitLog getVisitLog(int id);

    @ResultMap("getVisitLog-int")
    @Select("SELECT * FROM visit_log")
    List<VisitLog> getAllVisitLogs();

    @ResultMap("getVisitLog-int")
    @Select("SELECT * FROM visit_log WHERE event_id = #{id}")
    List<VisitLog> getVisitLogsByEvent(Event event);

    @Insert("INSERT INTO visit_log(event_id, date, user_id) VALUES(#{event.id}, NOW(),  #{user.id})")
    @Options(useGeneratedKeys = true)
    void addVisitLog(VisitLog visitLog);

    @Delete("DELETE FROM visit_log WHERE id=#{id}")
    void removeVisitLog(VisitLog visitLog);

}