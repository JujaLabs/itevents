package org.itevents.dao.mybatis.mapper;

import org.apache.ibatis.annotations.*;
import org.itevents.dao.UserDao;
import org.itevents.model.*;

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

    @ResultMap("getUser-int")
    @Select("SELECT * FROM user_profile up JOIN user_event ue ON up.id=ue.user_id WHERE ue.event_id = #{event.id}")
    List<User> getUsersByEvent(@Param("event") Event event);
}
