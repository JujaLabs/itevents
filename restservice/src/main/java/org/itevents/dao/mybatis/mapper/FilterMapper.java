package org.itevents.dao.mybatis.mapper;

import org.apache.ibatis.annotations.*;
import org.itevents.dao.mybatis.FilterDao;
import org.itevents.model.City;
import org.itevents.model.Filter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vaa25 on 17.07.2015.
 */
public interface FilterMapper extends FilterDao {

    @Results({
            @Result(property = "id", column = "id", id = true),
            @Result(property = "city", javaType = City.class, column = "city_id",
                    one = @One(select = "org.itevents.dao.mybatis.mapper.CityMapper.getCity")),
            @Result(property = "technologies", column = "id", javaType = ArrayList.class,
                    many = @Many(select = "org.itevents.dao.mybatis.mapper.TechnologyMapper.getTechnologiesByFilterId"))

    })
    @Select("SELECT * FROM filter WHERE user_id = #{id}")
    Filter getFilter(int id);

    @ResultMap("getFilter-int")
    @Select("SELECT * FROM filter ORDER BY id")
    List<Filter> getAllFilters();

    @Insert("INSERT INTO filter (limit, city_id, free, longitude, latitude, radius)" +
            "VALUES (#{limit}, #{city.id}, #{free}, #{longitude}, #{latitude}, #{radius})")
    @Options(useGeneratedKeys = true)
    void addFilter(Filter filter);

    @Delete("DELETE FROM filter WHERE id = #{id}")
    void removeFilter(Filter filter);
}
