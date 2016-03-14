package org.itevents.dao.mybatis.mapper;import org.apache.ibatis.annotations.*;import org.itevents.dao.EventDao;import org.itevents.dao.model.*;import org.itevents.dao.mybatis.builder.EventSqlBuilder;import java.util.ArrayList;import java.util.Date;import java.util.List;public interface EventMapper extends EventDao {    @Override    @Results(value = {            @Result(property = "id", column = "id", id = true),            @Result(property = "eventDate", column = "event_date"),            @Result(property = "createDate", column = "create_date"),            @Result(property = "regLink", column = "reg_link"),            @Result(property = "location", column = "id", javaType = Location.class,                    one = @One(select = "org.itevents.dao.mybatis.mapper.LocationMapper.getEventLocation")),            @Result(property = "currency", column = "currency_id", javaType = Currency.class,                    one = @One(select = "org.itevents.dao.mybatis.mapper.CurrencyMapper.getCurrency")),            @Result(property = "city", column = "city_id", javaType = City.class,                    one = @One(select = "org.itevents.dao.mybatis.mapper.CityMapper.getCity")),            @Result(property = "technologies", column = "id", javaType = ArrayList.class,                    many = @Many(select = "org.itevents.dao.mybatis.mapper.TechnologyMapper.getTechnologiesByEventId"))    })    @Select("SELECT * FROM event WHERE id = #{id}")    Event getEvent(int id);    @Override    @ResultMap("getEvent-int")    @Select("SELECT * FROM event ORDER BY title")    List<Event> getAllEvents();    @Override    @Insert("INSERT INTO event(title, event_date, create_date, reg_link, address, point, contact, price, " +            "currency_id, city_id, description) VALUES(#{title}, #{eventDate}, #{createDate}, #{regLink}, #{address}, " +            "ST_MakePoint(#{location.longitude},#{location.latitude}), #{contact}, #{price}, #{currency.id}," +            "#{city.id}, #{description})")    @Options(useGeneratedKeys = true)    void addEvent(Event event);    @Override    @InsertProvider(type = EventSqlBuilder.class, method = "addEventTechnology")    void addEventTechnology(Event event);    @Override    @Update("UPDATE event SET title=#{title}, event_date=#{eventDate}, create_date=#{createDate}, " +            "reg_link=#{regLink}, address=#{address}, point= ST_MakePoint(#{location.longitude},#{location.latitude}), " +            "contact=#{contact}, price=#{price}, currency_id=#{currency.id}, city_id=#{city.id}, description=#{description} " +            "WHERE id =#{id}")    void updateEvent(Event event);    @Override    @Delete("DELETE FROM event_technology WHERE event_id=#{id}")    void removeEventTechnology(Event event);    @Override    @SelectProvider(type = EventSqlBuilder.class, method = "getFilteredEvents")    @ResultMap("getEvent-int")    List<Event> getFilteredEvents(Filter filter);    @Override    @Insert("INSERT INTO user_event(user_id, event_id) VALUES (#{user.id}, #{event.id})")    void assignUserToEvent(@Param("user") User user,                           @Param("event") Event event);    @Override    @SelectProvider(type = EventSqlBuilder.class, method = "getFilteredEventsInDateRangeWithRating")    @ResultMap("getEvent-int")    List<Event> getFilteredEventsWithRating(Filter params);    @Override    @Update("UPDATE user_event " +            "SET deleted_date = #{unassignDate}, deleted_reason = #{unassignReason} " +            "WHERE deleted_date IS NULL AND user_id = #{user.id} AND event_id = #{event.id}")    void unassignUserFromEvent(@Param("user") User user,                  @Param("event") Event event,                  @Param("unassignDate") Date unassignDate,                  @Param("unassignReason") String unassignReason);    @Override    @ResultMap("getEvent-int")    @Select("SELECT * FROM event e JOIN user_event ue ON e.id=ue.event_id " +            "WHERE ue.user_id = #{id} AND deleted_date IS NULL")    List<Event> getEventsByUser(User user);}