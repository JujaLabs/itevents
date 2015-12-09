package org.itevents.dao.mybatis.mapper;

import org.apache.ibatis.annotations.*;
import org.itevents.dao.FilterDao;
import org.itevents.dao.mybatis.util.FilterTechnologySqlBuilder;
import org.itevents.model.City;
import org.itevents.model.Filter;
import org.itevents.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vaa25 on 17.07.2015.
 */
public interface FilterMapper extends FilterDao {

    @Results({
            @Result(property = "id", column = "id", id = true),
            @Result(property = "limit", column = "row_limit"),
            @Result(property = "city", javaType = City.class, column = "city_id",
                    one = @One(select = "org.itevents.dao.mybatis.mapper.CityMapper.getCity")),
            @Result(property = "technologies", column = "id", javaType = ArrayList.class,
                    many = @Many(select = "org.itevents.dao.mybatis.mapper.TechnologyMapper.getTechnologiesByFilterId"))

    })
    @Override
    @Select("SELECT * FROM filter WHERE id = #{id}")
    Filter getFilter(int id);

    @Override
    @ResultMap("getFilter-int")
    @Select("SELECT * FROM filter f JOIN user_filter uf ON f.id=uf.filter_id AND uf.user_id = #{id} " +
            "ORDER BY create_date DESC LIMIT 1")
    Filter getLastFilterByUser(User user);

    @Override
    @ResultMap("getFilter-int")
    @Select("SELECT * FROM filter ORDER BY id")
    List<Filter> getAllFilters();

    @Override
    @Insert("INSERT INTO filter (row_limit, city_id, create_date, free, longitude, latitude, radius)" +
            "VALUES (#{limit}, #{city.id}, #{createDate}, #{free}, #{longitude}, #{latitude}, #{radius})")
    @Options(useGeneratedKeys = true)
    void addFilter(Filter filter);

    @Override
    @InsertProvider(type = FilterTechnologySqlBuilder.class, method = "addFilterTechnology")
    void addFilterTechnology(Filter filter);

    @Override
    @Insert("INSERT INTO user_filter (user_id, filter_id) VALUES (#{user.id}, #{filter.id})")
    void addUserFilter(User user, Filter filter);
}
