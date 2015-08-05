package org.itevents.mapper;

import org.apache.ibatis.annotations.*;
import org.itevents.model.Event;
import org.itevents.parameter.FilteredEventsParameter;

import java.util.List;

public interface EventMapper {

    @ResultMap("eventMap")
    @Select("SELECT * FROM events WHERE id = #{id}")
    Event getEvent(int id);

    @Select("SELECT * FROM events")
    @ResultMap("eventMap")
    List<Event> getAllEvents();

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

    List<Event> getFilteredEvents(FilteredEventsParameter params);
}