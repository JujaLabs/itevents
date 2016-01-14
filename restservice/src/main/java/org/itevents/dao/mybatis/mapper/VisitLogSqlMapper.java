package org.itevents.dao.mybatis.mapper;

import org.apache.ibatis.annotations.*;
import org.itevents.dao.VisitLogDao;
import org.itevents.model.Event;
import org.itevents.model.User;
import org.itevents.model.VisitLog;

import java.util.List;

public interface VisitLogSqlMapper extends VisitLogDao {

    @Results({
            @Result(property = "id", column = "id", id = true),
            @Result(property = "event", javaType = Event.class, column = "event_id",
                    one = @One(select = "org.itevents.dao.mybatis.mapper.EventSqlMapper.getEvent")),
            @Result(property = "user", javaType = User.class, column = "user_id",
                    one = @One(select = "org.itevents.dao.mybatis.mapper.UserSqlMapper.getUser"))
    })
    @Override
    @Select("SELECT * FROM visit_log WHERE id = #{id}")
    VisitLog getVisitLog(int id);

    @Override
    @ResultMap("getVisitLog-int")
    @Select("SELECT * FROM visit_log ORDER BY date")
    List<VisitLog> getAllVisitLogs();

    @Override
    @ResultMap("getVisitLog-int")
    @Select("SELECT * FROM visit_log WHERE event_id = #{id} ORDER BY date")
    List<VisitLog> getVisitLogsByEvent(Event event);

    @Override
    @Insert("INSERT INTO visit_log(event_id, date, user_id) VALUES(#{event.id}, #{date},  #{user.id})")
    @Options(useGeneratedKeys = true)
    void addVisitLog(VisitLog visitLog);
}