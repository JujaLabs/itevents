package org.itevents.service;

import org.apache.ibatis.annotations.*;
import org.itevents.model.Statistic;

public interface StatisticMapper {
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "event_id", column = "event_id"),
            @Result(property = "count", column = "count")
    })
    @Select("SELECT id, event_id, event_id FROM statistic WHERE event_id = #{event_id}")
    Statistic selectStatistic(long event_id);

    @Insert("INSERT INTO statistic(event_id, count) VALUES(#{id}, 0)")
    void addStatistic(long event_id);

    @Update("UPDATE statistic SET count=count+1 WHERE event_id =#{event_id}")
    void incCounter(long event_id);

    @Delete("DELETE FROM statistic WHERE event_id =#{event_id}")
    void deleteEvent(long event_id);
}