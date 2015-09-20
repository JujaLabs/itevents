package org.itevents.dao.mybatis.mapper;

import org.apache.ibatis.annotations.*;
import org.itevents.dao.EventDao;
import org.itevents.dao.mybatis.util.GetFilteredEventsSqlBuilder;
import org.itevents.model.City;
import org.itevents.model.Currency;
import org.itevents.model.Event;
import org.itevents.model.Location;
import org.itevents.parameter.FilteredEventsParameter;

import java.util.ArrayList;
import java.util.List;

public interface EventMapper extends EventDao {

    @Results(value = {
            @Result(property = "id", column = "id", id = true),
            @Result(property = "eventDate", column = "event_date"),
            @Result(property = "createDate", column = "create_date"),
            @Result(property = "regLink", column = "reg_link"),
            @Result(property = "location", column = "id", javaType = Location.class,
                    one = @One(select = "org.itevents.dao.mybatis.mapper.LocationMapper.selectLocation")),
            @Result(property = "currency", column = "currency_id", javaType = Currency.class,
                    one = @One(select = "org.itevents.dao.mybatis.mapper.CurrencyMapper.getCurrency")),
            @Result(property = "city", column = "city_id", javaType = City.class,
                    one = @One(select = "org.itevents.dao.mybatis.mapper.CityMapper.getCity")),
            @Result(property = "technologies", column = "id", javaType = ArrayList.class,
                    many = @Many(select = "org.itevents.dao.mybatis.mapper.TechnologyMapper.getTechnologiesByEventId"))
    })
    @Select("SELECT * FROM events WHERE id = #{id}")
    Event getEvent(int id);

    @ResultMap("getEvent-int")
    @Select("SELECT * FROM events")
    List<Event> getAllEvents();

    @Insert("INSERT INTO events(title, event_date, create_date, reg_link, address, point, contact, free, price, " +
            "currency_id, city_id) VALUES(#{title}, #{eventDate}, #{createDate}, #{regLink}, #{address}, " +
            "ST_MakePoint(#{location.longitude},#{location.latitude}), #{contact}, #{free}, #{price}, #{currency.id}," +
            "#{city.id})")
    @Options(useGeneratedKeys = true)
    void addEvent(Event event);

    @Update("UPDATE events SET title=#{title}, event_date=#{eventDate}, create_date=#{createDate}, " +
            "reg_link=#{regLink}, address=#{address}, point= ST_MakePoint(#{location.longitude},#{location.latitude}), " +
            "contact=#{contact}, free=#{free}, price=#{price}, currency_id=#{currency.id}, city_id=#{city.id} WHERE id =#{id}")
    void updateEvent(Event event);

    @Delete("DELETE FROM events WHERE id =#{id}")
    void removeEvent(Event event);

    @SelectProvider(type = GetFilteredEventsSqlBuilder.class, method = "getFilteredEvents")
    @ResultMap("getEvent-int")
    List<Event> getFilteredEvents(FilteredEventsParameter params);
}