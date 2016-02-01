package org.itevents.dao.mybatis.mapper;

import org.apache.ibatis.annotations.*;
import org.itevents.dao.UserDao;
import org.itevents.model.Event;
import org.itevents.model.Role;
import org.itevents.model.User;

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
    @Insert("INSERT INTO user_profile (login, password, role_id, subscribed) VALUES(#{user.login}, #{password}, " +
            "#{user.role.id}, #{user.subscribed})")
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
            "WHERE ue.event_id = #{event.id} AND deleted_date IS NULL")
    List<User> getUsersByEvent(@Param("event") Event event);

    @ResultMap("getUser-int")
    @Select("SELECT * FROM user_profile WHERE subscribed = TRUE")
    List<User> getSubscribedUsers();

    @Override
    @Select("SELECT password FROM user_profile WHERE login = #{login}")
    String getEncodedUserPassword(User user);

    @Override
    @Update("UPDATE user_profile SET password=#{password}" +
            "WHERE login = #{user.login}")
    void setEncodedUserPassword(@Param("user") User user,
                                @Param("password") String password);
}
