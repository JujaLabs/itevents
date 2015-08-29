package org.itevents.mybatis.mapper.filter;

import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.itevents.dao.filter.FilterDao;
import org.itevents.model.City;
import org.itevents.model.Filter;

public interface FilterMapper extends FilterDao {

    @Results(value = {
            @Result(property = "id", column = "id", id = true),
            @Result(property = "city", column = "city_id", javaType = City.class,
                    one = @One(select = "org.itevents.mybatis.mapper.CityMapper.getCity")),
            @Result(property = "free", column = "free"),
            @Result(property = "longitude", column = "longitude"),
            @Result(property = "latitude", column = "latitude"),
            @Result(property = "radius", column = "radius")
    })
    @Select("SELECT * FROM filters WHERE id = #{id}")
    Filter getFilterById(int id);
}
