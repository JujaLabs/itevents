package org.itevents.mapper;

import org.apache.ibatis.annotations.*;
import org.itevents.model.Statistic;

public interface StatisticMapper {
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "event_id", column = "event_id"),
            @Result(property = "date", column = "date"),
            @Result(property = "user_id", column = "user_id")
    })
    @Select("SELECT id, event_id, date, user_id FROM statistic WHERE event_id = #{event_id}")
    Statistic selectStatistic(@Param("event_id") int event_id);

    @Insert("INSERT INTO statistic(event_id, user_id) VALUES(#{event_id}, CURDATE(),  user_id)")
    void addClick(@Param("event_id") int event_id, @Param("user_id") int user_id);

    @Delete("DELETE FROM statistic WHERE id =#{id}")
    void deleteClick(@Param("id") int id);
}