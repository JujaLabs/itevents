package org.itevents.mapper;

import org.apache.ibatis.annotations.*;

public interface StatisticMapper {

    @Select("SELECT COUNT(*) FROM statistic WHERE event_id = #{event_id};")
    int selectStatistic(@Param("event_id") int eventId);

    @Insert("INSERT INTO statistic(event_id, date, user_id) VALUES(#{event_id}, NOW(),  #{user_id})")
    void addClick(@Param("event_id") int eventId, @Param("user_id") int userId);

    @Delete("DELETE FROM statistic WHERE id =#{id}")
    void deleteClick(@Param("id") int id);

}