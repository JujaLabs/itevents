package org.itevents.mapper;

import org.apache.ibatis.annotations.*;
import org.itevents.model.RegLinkClick;

public interface RegLinkClickMapper {

    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "eventId", column = "event_id"),
            @Result(property = "date", column = "date"),
            @Result(property = "userId", column = "user_id")
    })
    @Select("SELECT id, event_id, date, user_id FROM reg_link_clicks WHERE event_id = #{event_id}")
    RegLinkClick selectStatistic(@Param("event_id") int eventId);

    @Insert("INSERT INTO reg_link_clicks(event_id, date, user_id) VALUES(#{event_id}, NOW(),  #{user_id})")
    void addClick(@Param("event_id") int eventId, @Param("user_id") int userId);

    @Delete("DELETE FROM reg_link_clicks WHERE id =#{id}")
    void deleteClick(@Param("id") int id);
}