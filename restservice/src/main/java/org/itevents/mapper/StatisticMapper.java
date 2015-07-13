package org.itevents.mapper;

import org.apache.ibatis.annotations.*;
import org.itevents.model.Statistic;

public interface StatisticMapper {

    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "eventId", column = "event_id"),
            @Result(property = "date", column = "date"),
            @Result(property = "userId", column = "user_id")
    })
    @Select("SELECT id, event_id, date, user_id FROM statistic WHERE event_id = #{event_id}")
    Statistic selectStatistic(@Param("event_id") int eventId);

    @Insert("INSERT INTO statistic(event_id, date, user_id) VALUES(#{event_id}, NOW(),  #{user_id})")
    void addClick(@Param("event_id") int eventId, @Param("user_id") int userId);

    @Delete("DELETE FROM statistic WHERE id =#{id}")
    void deleteClick(@Param("id") int id);
}