package org.itevents.dao.mybatis.mapper;

import org.apache.ibatis.annotations.*;
import org.itevents.dao.UserDao;
import org.itevents.model.Otp;
import org.itevents.model.Role;
import org.itevents.model.User;
import org.itevents.model.*;

import java.util.ArrayList;
import java.util.List;

public interface UserMapper extends UserDao {

    @Results({
            @Result(property = "id", column = "id", id = true),
            @Result(property = "role", javaType = Role.class, column = "role_id",
                    one = @One(select = "org.itevents.dao.mybatis.mapper.RoleMapper.getRole"))
    })
    @Select("SELECT * FROM user_profile WHERE id = #{id}")
    User getUser(int id);

    @ResultMap("getUser-int")
    @Select("SELECT * FROM user_profile WHERE login = #{name}")
    User getUserByName(String name);

    @ResultMap("getUser-int")
    @Select("SELECT * FROM user_profile ORDER BY login")
    List<User> getAllUsers();

    @Insert("INSERT INTO user_profile (login, password, role_id) VALUES(#{login}, #{password}, #{role.id})")
    @Options(useGeneratedKeys = true)
    void addUser(User user);

    @Delete("DELETE FROM user_profile WHERE id =#{id}")
    void removeUser(User user);

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
    @Select("SELECT * FROM event e JOIN user_event ue ON e.id=ue.event_id WHERE ue.user_id = #{user.id}")
    List<Event> getUserEvents(@Param("user") User user);

    @Insert("UPDATE user_profile SET isActive = true WHERE id = #{id}")
    void activateUser(User user);

    @Insert("UPDATE user_profile SET isActive = false WHERE id = #{id}")
    void deactivateUser(User user);

    @Insert("INSERT INTO user_otp(user_id, otp, creationDate, expirationDate) VALUES(#user_id, #otp, #creationDate, #expirationDate) ")
    void addOtp(User user, Otp otp);

    @Results({
            @Result(property = "otp", column = "otp"),
            @Result(property = "creationDate", column = "creationDate"),
            @Result(property = "expirationDate", column = "creationDate")
    })
    @Select("Select * FROM user_otp WHERE user_id = #{id}")
    Otp getOtp(User user);

    @Delete("DELETE FROM user_otp WHERE user_id = #user_id")
    void DeleteOtp(User user);
}
