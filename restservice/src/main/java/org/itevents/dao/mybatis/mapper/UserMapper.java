package org.itevents.dao.mybatis.mapper;

import org.apache.ibatis.annotations.*;
import org.itevents.dao.UserDao;
import org.itevents.model.Event;
import org.itevents.model.Role;
import org.itevents.model.User;

import java.util.List;

/**
 * Created by vaa25 on 17.07.2015.
 */
public interface UserMapper extends UserDao {

    @Results({
            @Result(property = "id", column = "id", id = true),
            @Result(property = "role", javaType = Role.class, column = "roles_id",
                    one = @One(select = "org.itevents.dao.mybatis.mapper.RoleMapper.getRole"))
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
    @Insert("INSERT INTO user_event(user_id, event_id) VALUES(#{user.id}, #{event.id})")
    void willGoToEvent(@Param("user") User user,
                       @Param("event") Event event);

//    @PreAuthorize("isAuthenticated()")
    @Delete("DELETE FROM user_event WHERE user_id = #{{user.id} AND event_id = #{event.id}")
    void willNotGoToEvent(@Param("user") User user,
                          @Param("event") Event event);

//    @PreAuthorize("isAuthenticated()")
    @Select("SELECT * FROM user_event WHERE user_id = #{user.id}")
    List<Event> getUserEvents(@Param("user") User user);
}
