package org.itevents.dao.mybatis.mapper;

import org.apache.ibatis.annotations.*;
import org.itevents.dao.UserDao;
import org.itevents.model.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vaa25 on 17.07.2015.
 */
public interface UserMapper extends UserDao {

    @Results({
            @Result(property = "id", column = "id", id = true),
            @Result(property = "role", javaType = Role.class, column = "roles_id",
                    one = @One(select = "org.itevents.dao.mybatis.mapper.RoleMapper.getRole")),
            @Result(property = "events", column = "event_id", javaType = ArrayList.class,
            many = @Many(select = "org.itevents.dao.mybatis.mapper.EventMapper.getEvent"))
    })
    @Select("SELECT * FROM users WHERE id = #{id}")
    User getUser(int id);

    @ResultMap("getUser-int")
    @Select("SELECT * FROM users WHERE login = #{name}")
    User getUserByName(String name);

    @ResultMap("getUser-int")
    @Select("SELECT * FROM users")
    List<User> getAllUsers();

    @Insert("INSERT INTO users (login, password, roles_id) VALUES(#{login}, #{password}, #{role.id})")
    @Options(useGeneratedKeys = true)
    void addUser(User user);

    @Delete("DELETE FROM users WHERE id =#{id}")
    void removeUser(User user);

//    @PreAuthorize("isAuthenticated()")
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
                one = @One(select = "org.itevents.dao.mybatis.mapper.CityMapper.getCity"))
})
    @Select("SELECT * FROM events e JOIN user_event ue ON e.id=ue.event_id WHERE ue.user_id = #{user.id}")
    List<Event> getUserEvents(@Param("user") User user);
}
