package org.itevents.mapper;

import org.apache.ibatis.annotations.*;
import org.itevents.model.VisitLog;

//@Transactional
public interface VisitLogMapper {

    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "eventId", column = "event_id"),
            @Result(property = "date", column = "date"),
            @Result(property = "userId", column = "user_id")
    })
    @Select("SELECT id, event_id, date, user_id FROM visit_log WHERE event_id = #{event_id}")
    VisitLog selectVisitsByEvent(@Param("event_id") int eventId);

    @Insert("INSERT INTO visit_log(event_id, date, user_id) VALUES(#{event_id}, NOW(),  #{user_id})")
    void addVisit(@Param("event_id") int eventId, @Param("user_id") int userId);

    @Delete("DELETE FROM visit_log WHERE id =#{id}")
    void deleteVisit(@Param("id") int id);
}