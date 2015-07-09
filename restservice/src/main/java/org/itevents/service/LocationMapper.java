package org.itevents.service;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.itevents.model.Location;

public interface LocationMapper {
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "longitude", column = "longitude"),
            @Result(property = "latitude", column = "latitude")
    })
    @Select("SELECT point[0] as longitude, point[1] as latitude from event WHERE id = #{id}")
    Location selectLocation(long id);
}