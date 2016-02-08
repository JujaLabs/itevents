package org.itevents.dao.mybatis.mapper;

import org.apache.ibatis.annotations.*;
import org.itevents.dao.UserDao;
import org.itevents.model.Event;
import org.itevents.model.Role;
import org.itevents.model.User;
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
    @Insert("INSERT INTO user_profile (login, password, role_id, subscribed) " +
            "VALUES(#{login}, #{password}, #{role.id}, #{subscribed})")
    @Options(useGeneratedKeys = true)
    void addUser(@Param("user") User user,
                 @Param("password") String password);

    @Override
    @Update("UPDATE user_profile SET login=#{login}, role_id=#{role.id}, subscribed=#{subscribed} " +
            "WHERE id=#{id}")
    void updateUser(User user);

    @Override
    @ResultMap("getUser-int")
    @Select("SELECT * FROM user_profile up JOIN user_event ue ON up.id=ue.user_id " +
            "WHERE ue.event_id = #{id} AND deleted_date IS NULL")
    List<User> getUsersByEvent(Event event);

    @Override
    @ResultMap("getUser-int")
    @Select("SELECT * FROM user_profile WHERE subscribed = TRUE")
    List<User> getSubscribedUsers();

    @Override
    @Select("SELECT password FROM user_profile WHERE login = #{login}")
    String getUserPassword(User user);

    @Override
    @Update("UPDATE user_profile SET password=#{password}" +
            "WHERE login = #{user.login}")
    void setUserPassword(@Param("user") User user,
                         @Param("password") String password);

    @Insert("INSERT INTO one_time_password(user_id, password, creation_date, expiration_date) " +
            "VALUES(#{user.id}, #{password}, #{creationDate}, #{expirationDate})")
    void setOtpToUser(User user,
                       OtpGenerator otpGenerator);

    @Results(value = {
            @Result(property = "password", column = "password"),
            @Result(property = "creationDate", column = "creation_date"),
            @Result(property = "expirationDate", column = "expiration_date"),
            @Result(property = "isActive", column = "active")
    })
    @Select("Select * FROM one_time_password WHERE password = #{password}")
    OtpGenerator getOtp(String password);

    @Override
    @ResultMap("getUser-int")
    @Select("SELECT * FROM user_profile up JOIN one_time_password otp ON up.id = otp.user_id " +
            "WHERE otp.password = #{password}")
    User getUserByOtp(OtpGenerator otpGenerator);
}