package org.itevents.mybatis.mapper;

import org.apache.ibatis.annotations.*;
import org.itevents.dao.EventDao;
import org.itevents.model.City;
import org.itevents.model.Currency;
import org.itevents.model.Event;
import org.itevents.model.Location;
import org.itevents.mybatis.mapper.util.SQLBuilder;
import org.itevents.parameter.FilteredEventsParameter;

import java.util.Date;
import java.util.List;

public interface EventMapper extends EventDao {

    @Results(value = {
            @Result(property = "id", column = "id", id = true),
            @Result(property = "eventDate", column = "event_date"),
            @Result(property = "createDate", column = "create_date"),
            @Result(property = "regLink", column = "reg_link"),
            @Result(property = "location", column = "id", javaType = Location.class,
                    one = @One(select = "org.itevents.mybatis.mapper.LocationMapper.selectLocation")),
            @Result(property = "currency", column = "currency_id", javaType = Currency.class,
                    one = @One(select = "org.itevents.mybatis.mapper.CurrencyMapper.getCurrency")),
            @Result(property = "city", column = "city_id", javaType = City.class,
                    one = @One(select = "org.itevents.mybatis.mapper.CityMapper.getCity"))
    })
    @Select("SELECT * FROM events WHERE id = #{id}")
    Event getEvent(int id);

    @ResultMap("getEvent-int")
    @Select("SELECT * FROM events")
    List<Event> getAllEvents();

    @ResultMap(value = "getEvent-int")
    @Select("SELECT * FROM events" +
            " WHERE ST_DWithin(point::geography," +
            " ST_MakePoint(#{location.longitude},#{location.latitude})::geography, #{radius})")
    List<Event> getEventsInRadius(@Param("location")Location location, @Param("radius")int radius);

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

    @SelectProvider(type = org.itevents.mybatis.mapper.util.SQLBuilder.class, method = "selectFilteredEvent")
    @ResultMap(value = "getEvent-int")
    List<Event> getFilteredEvents(@Param("params")FilteredEventsParameter params);

    @SelectProvider(type = org.itevents.mybatis.mapper.util.SQLBuilder.class, method = "selectFutureFilteredEvents")
    @ResultMap(value = "getEvent-int")
    List<Event> getFutureFilteredEvents(@Param("params")FilteredEventsParameter params,@Param("dateStart") Date date);
}