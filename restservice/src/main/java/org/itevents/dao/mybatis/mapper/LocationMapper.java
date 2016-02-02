package org.itevents.dao.mybatis.mapper;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.itevents.dao.LocationDao;
import org.itevents.model.Location;

public interface LocationMapper extends LocationDao {

    @Results({
            @Result(property = "longitude", column = "longitude"),
            @Result(property = "latitude", column = "latitude")
    })

    @Override
    @Select("SELECT ST_X(point) AS longitude, ST_Y(point) AS latitude FROM event WHERE id = #{id}")
    Location getEventLocation(int id);

    @Results({
            @Result(property = "longitude", column = "longitude"),
            @Result(property = "latitude", column = "latitude")
    })
    @Select("SELECT ST_X(point) AS longitude, ST_Y(point) AS latitude FROM city WHERE id = #{id}")
    Location getCityLocation(int id);
}
