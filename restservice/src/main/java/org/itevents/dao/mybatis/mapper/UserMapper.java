package org.itevents.dao.mybatis.mapper;

import org.apache.ibatis.annotations.*;
import org.itevents.dao.UserDao;
import org.itevents.model.*;
import org.itevents.util.OneTimePassword.OtpGenerator;

import java.util.List;

public interface UserMapper extends UserDao {

    @Override
    @Results({
            @Result(property = "id", column = "id", id = true),
            @Result(property = "role", javaType = Role.class, column = "role_id",
                    one = @One(select = "org.itevents.dao.mybatis.mapper.RoleMapper.getRole"))
    })
    @Select("SELECT * FROM user_profile WHERE id = #{id}")
    User getUser(int id);

    @Override
    @ResultMap("getUser-int")
    @Select("SELECT * FROM user_profile WHERE login = #{name}")
    User getUserByName(String name);

    @Override
    @ResultMap("getUser-int")
    @Select("SELECT * FROM user_profile ORDER BY login")
    List<User> getAllUsers();

    @Override
    @Insert("INSERT INTO user_profile (login, password, role_id, subscribed) VALUES(#{login}, #{password}, " +
            "#{role.id}, #{subscribed})")
    @Options(useGeneratedKeys = true)
    void addUser(User user);

    @Override
    @Update("UPDATE user_profile SET login=#{login}, password=#{password}, role_id=#{role.id}, subscribed=#{subscribed} " +
            "WHERE id=#{id}")
    void updateUser(User user);

    @Override
    @ResultMap("org.itevents.dao.mybatis.mapper.EventMapper.getEvent-int")
    @Select("SELECT * FROM event e JOIN user_event ue ON e.id=ue.event_id WHERE ue.user_id = #{user.id}")
    List<Event> getUserEvents(@Param("user") User user);

    @Insert("UPDATE user_profile SET isActive = true WHERE id = #{id}")
    void activateUser(User user);

    @Insert("UPDATE user_profile SET isActive = false WHERE id = #{id}")
    void deactivateUser(User user);

    @Insert("INSERT INTO user_otp(user_id, otp, creationDate, expirationDate) VALUES(#{user.id}, #{otp.otp}, #{otp.creationDate}, #{otp.expirationDate})")
    void addOtp(@Param("user")User user,
                @Param("otp") OtpGenerator otpGenerator);

    @Results(value = {
            @Result(property = "otp", column = "otp"),
            @Result(property = "creationDate", column = "creationDate"),
            @Result(property = "expirationDate", column = "creationDate")
    })
    @Select("Select * FROM user_otp WHERE user_id = #{user.id}")
    OtpGenerator getOtp(@Param("user")User user);

    @Delete("DELETE FROM user_otp WHERE user_id = #{user.id}")
    void DeleteOtp(@Param("user")User user);
    @ResultMap("getUser-int")
    @Select("SELECT * FROM user_profile up JOIN user_event ue ON up.id=ue.user_id " +
            "WHERE ue.event_id = #{event.id} AND deleted_date IS NULL")
    List<User> getUsersByEvent(@Param("event") Event event);

    @ResultMap("getUser-int")
    @Select("SELECT * FROM user_profile WHERE subscribed = TRUE")
    List<User> getSubscribedUsers();
}
