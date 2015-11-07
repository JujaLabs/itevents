package org.itevents.dao.mybatis.mapper;

import org.apache.ibatis.annotations.*;
import org.itevents.dao.EventDao;
import org.itevents.dao.mybatis.util.EventTechnologySqlBuilder;
import org.itevents.dao.mybatis.util.FilteredEventsSqlBuilder;
import org.itevents.model.*;

import java.util.ArrayList;
import java.util.List;

public interface EventMapper extends EventDao {

    @Results(value = {
            @Result(property = "id", column = "id", id = true),
            @Result(property = "eventDate", column = "event_date"),
            @Result(property = "createDate", column = "create_date"),
            @Result(property = "regLink", column = "reg_link"),
            @Result(property = "location", column = "id", javaType = Location.class,
                    one = @One(select = "org.itevents.dao.mybatis.mapper.LocationMapper.getEventLocation")),
            @Result(property = "currency", column = "currency_id", javaType = Currency.class,
                    one = @One(select = "org.itevents.dao.mybatis.mapper.CurrencyMapper.getCurrency")),
            @Result(property = "city", column = "city_id", javaType = City.class,
                    one = @One(select = "org.itevents.dao.mybatis.mapper.CityMapper.getCity")),
            @Result(property = "technologies", column = "id", javaType = ArrayList.class,
                    many = @Many(select = "org.itevents.dao.mybatis.mapper.TechnologyMapper.getTechnologiesByEventId"))
    })
    @Override
    @Select("SELECT * FROM event WHERE id = #{id}")
    Event getEvent(int id);

    @Override
    @ResultMap("getEvent-int")
    @Select("SELECT * FROM event ORDER BY title")
    List<Event> getAllEvents();

    @Override
    @Insert("INSERT INTO event(title, event_date, create_date, reg_link, address, point, contact, price, " +
            "currency_id, city_id) VALUES(#{title}, #{eventDate}, #{createDate}, #{regLink}, #{address}, " +
            "ST_MakePoint(#{location.longitude},#{location.latitude}), #{contact}, #{price}, #{currency.id}," +
            "#{city.id})")
    @Options(useGeneratedKeys = true)
    void addEvent(Event event);

    @Override
    @InsertProvider(type = EventTechnologySqlBuilder.class, method = "addEventTechnology")
    void addEventTechnology(Event event);

    @Override
    @Update("UPDATE event SET title=#{title}, event_date=#{eventDate}, create_date=#{createDate}, " +
            "reg_link=#{regLink}, address=#{address}, point= ST_MakePoint(#{location.longitude},#{location.latitude}), " +
            "contact=#{contact}, price=#{price}, currency_id=#{currency.id}, city_id=#{city.id} WHERE id =#{id}")
    void updateEvent(Event event);

    @Override
    @Delete("DELETE FROM event WHERE id =#{id}")
    void removeEvent(Event event);

    @Override
    @Delete("DELETE FROM event_technology WHERE event_id=#{id}")
    void removeEventTechnology(Event event);

    @Override
    @SelectProvider(type = FilteredEventsSqlBuilder.class, method = "getFilteredEvents")
    @ResultMap("getEvent-int")
    List<Event> getFilteredEvents(Filter params);
}