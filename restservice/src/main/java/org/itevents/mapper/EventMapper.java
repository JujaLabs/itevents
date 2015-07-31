package org.itevents.mapper;

import org.apache.ibatis.annotations.*;
import org.itevents.controller.FilterEventParams;
import org.itevents.model.Event;
import org.itevents.model.Location;

import java.util.List;

public interface EventMapper {

    @ResultMap("eventMap")
    @Select("SELECT * FROM events WHERE id = #{id}")
    Event getEvent(int id);

    //    @Results(value = {
//            @Result(property = "id", column = "id"),
//            @Result(property = "title", column = "title"),
//            @Result(property = "eventDate", column = "event_date"),
//            @Result(property = "createDate", column = "create_date"),
//            @Result(property = "regLink", column = "reg_link"),
//            @Result(property = "address", column = "address"),
//            @Result(property = "contact", column = "contact"),
//            @Result(property = "location", column = "id", javaType = Location.class,
//                    one = @One(select = "org.itevents.mapper.LocationMapper.selectLocation")),
//            @Result(property = "price", column = "price"),
//            @Result(property = "currency", column = "currency_id", javaType = Currency.class,
//                    one = @One(select = "org.itevents.mapper.CurrencyMapper.getCurrency")),
//            @Result(property = "city", column = "city_id", javaType = City.class,
//                    one = @One(select = "org.itevents.mapper.CityMapper.getCity"))
//    })
    @Select("SELECT * FROM events")
    @ResultMap("eventMap")
    List<Event> getAllEvents();

    @ResultMap("eventMap")
    @Select("SELECT * FROM events " +
            "WHERE ST_DWithin(point)::geography, " +
            "ST_MakePoint(#{location.longitude},#{location.latitude})::geography, #{radius}")
    List<Event> getFutureEventsInRadius(@Param("location") Location location, @Param("radius") int radius);

    @Insert("INSERT INTO events(title, event_date, create_date, reg_link, address, point, contact, price, " +
            "currency_id, city_id) VALUES(#{title}, #{eventDate}, #{createDate}, #{regLink}, #{address}, " +
            "ST_MakePoint(#{location.longitude},#{location.latitude}), #{contact}, #{price}, #{currency.id}, +" +
            "#{city.id})")
    @Options(useGeneratedKeys = true)
    void addEvent(Event event);

    @Update("UPDATE events SET title=#{title}, event_date=#{eventDate}, create_date=#{createDate}, " +
            "reg_link=#{regLink}, address=#{address}, point= ST_MakePoint(#{location.longitude},#{location.latitude}), " +
            "contact=#{contact}, price=#{price}, currency_id=#{currency.id}, city_id=#{city.id} WHERE id =#{id}")
    void updateEvent(Event event);

    @Delete("DELETE FROM events WHERE id =#{id}")
    void removeEvent(int id);

    List<Event> getFilteredEvents(FilterEventParams params);
}