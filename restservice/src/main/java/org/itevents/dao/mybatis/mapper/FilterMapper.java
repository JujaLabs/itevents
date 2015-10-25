package org.itevents.dao.mybatis.mapper;

import org.apache.ibatis.annotations.*;
import org.itevents.dao.FilterDao;
import org.itevents.dao.mybatis.util.AddFilterTechnologySqlBuilder;
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
            @Result(property = "city", javaType = City.class, column = "city_id",
                    one = @One(select = "org.itevents.dao.mybatis.mapper.CityMapper.getCity")),
            @Result(property = "technologies", column = "id", javaType = ArrayList.class,
                    many = @Many(select = "org.itevents.dao.mybatis.mapper.TechnologyMapper.getTechnologiesByFilterId"))

    })
    @Select("SELECT * FROM filter WHERE user_id = #{id}")
    Filter getFilter(int id);

    @ResultMap("getFilter-int")
    @Select("SELECT * FROM filter f JOIN user_filter uf ON f.id=uf.filter_id AND uf.user_id = #{id} " +
            "ORDER BY create_date DESC LIMIT 1")
    Filter getLastFilterByUser(User user);

    @ResultMap("getFilter-int")
    @Select("SELECT * FROM filter ORDER BY id")
    List<Filter> getAllFilters();

    @Insert("INSERT INTO filter (limit, city_id, create_date, free, longitude, latitude, radius)" +
            "VALUES (#{limit}, #{createDate}, #{city.id}, #{free}, #{longitude}, #{latitude}, #{radius})")
    @Options(useGeneratedKeys = true)
    void addFilter(Filter filter);

    @InsertProvider(type = AddFilterTechnologySqlBuilder.class, method = "addFilterTechnology")
    void addFilterTechnology(Filter filter);

    @Delete("DELETE FROM filter WHERE id = #{id}")
    void removeFilter(Filter filter);

    @Delete("DELETE FROM user_filter WHERE user_id = #{id}")
    void removeFilterTechnology(User user);

    @Update("UPDATE filter SET limit =#{newFilter.limit}, create_date=#{createDate}, city_id =#{newFilter.city_id}," +
            " free =#{newFilter.free}, longitude =#{newFilter.longitude}, latitude =#{newFilter.latitude}," +
            " radius =#{newFilter.radius} " +
            "WHERE id=#{oldFilter.id")
    void updateFilter(Filter oldFilter, Filter newFilter);

    @Insert("INSERT INTO user_filter (user_id, filter_id) VALUES (#{user.id}, #{filter.id})")
    void addUserFilter(User user, Filter filter);
}
