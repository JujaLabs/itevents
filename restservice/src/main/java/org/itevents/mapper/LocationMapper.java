package org.itevents.mapper;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.itevents.model.Location;

public interface LocationMapper {
    @Results({
            @Result(property = "longitude", column = "longitude"),
            @Result(property = "latitude", column = "latitude")
    })
    @Select("SELECT ST_X(point) AS longitude, ST_Y(point) AS latitude FROM event WHERE id = #{id}")
    Location selectLocation(int id);
}
