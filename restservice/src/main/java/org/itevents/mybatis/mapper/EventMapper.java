package org.itevents.mybatis.mapper;

import org.apache.ibatis.annotations.*;
import org.itevents.dao.EventDao;
import org.itevents.model.City;
import org.itevents.model.Currency;
import org.itevents.model.Event;
import org.itevents.model.Location;
import org.itevents.parameter.FilteredEventsParameter;
import org.itevents.model.Event;
import org.itevents.model.Location;

import java.util.List;

public interface EventMapper extends EventDao {

    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "title", column = "title"),
            @Result(property = "eventDate", column = "event_date"),
            @Result(property = "createDate", column = "create_date"),
            @Result(property = "regLink", column = "reg_link"),
            @Result(property = "address", column = "address"),
            @Result(property = "contact", column = "contact"),
            @Result(property = "location", column = "id", javaType = Location.class,
                    one=@One(select="org.itevents.mybatis.mapper.LocationMapper.selectLocation"))
    })
    @Select("SELECT id, title, event_date, create_date, reg_link, address, contact FROM events WHERE id = #{id}")
    Event getEvent(int id);

    @ResultMap(value = "getEvent-int")
    @Select("SELECT id, title, event_date, create_date, reg_link, address, contact FROM events")
    List<Event> getAllEvents();

    @ResultMap(value = "getEvent-int")
    @Select("SELECT id, title, event_date, create_date, reg_link, address, contact FROM events" +
            " WHERE ST_DWithin(point::geography," +
            " ST_MakePoint(#{location.longitude},#{location.latitude})::geography, #{radius})")
    List<Event> getEventsInRadius(@Param("location")Location location, @Param("radius")int radius);

    @Insert("INSERT INTO events(title, event_date, create_date, reg_link, address, point, contact)" +
            " VALUES(#{title}, #{eventDate}, #{createDate}, #{regLink}, #{address}," +
            " ST_MakePoint(#{location.longitude},#{location.latitude}), #{contact})")
    @Options(useGeneratedKeys=true, keyProperty="id")
    void addEvent(Event event);

    @Update("UPDATE events SET title=#{title}, event_date=#{eventDate}, create_date=#{createDate}," +
            " reg_link=#{regLink}, address=#{address}, point= point(#{location.longitude},#{location.latitude})," +
            " contact=#{contact} WHERE id =#{id}")
    void updateEvent(Event event);

    @Delete("DELETE FROM events WHERE id =#{id}")
    void removeEvent(Event event);
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

    @Select("SELECT * FROM events")
    @ResultMap("getEvent-int")
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
    void removeEvent(int id);

    @Select({"<script>",
            "SELECT * FROM events",
            "<where>",
            "   <if test='city != null'>",
            "       city_id = #{city.id}",
            "   </if>",
            "   <if test='city == null'>",
            "       <if test='radius != null'>",
            "           AND ST_DWithin((point)::geography, ST_MakePoint(#{longitude},#{latitude})::geography, #{radius})",
            "       </if>",
            "   </if>",
            "   <if test='free != null'>",
            "       AND free = #{free}",
            "   </if>",
            "   <if test='technologies!=null'>",
            "       AND id IN (SELECT event_id FROM event_technology WHERE technology_id IN",
            "       <foreach item='technology' index='index' collection='technologies' open='(' separator=',' close=')'>",
            "           #{technology.id}",
            "       </foreach>",
            "       )",
            "   </if>",
            "</where>",
            "</script>"})
    @ResultMap("getEvent-int")
    List<Event> getFilteredEvents(FilteredEventsParameter params);
}