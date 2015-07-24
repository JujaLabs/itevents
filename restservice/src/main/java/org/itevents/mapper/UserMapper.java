package org.itevents.mapper;

import org.apache.ibatis.annotations.*;
import org.itevents.model.Role;
import org.itevents.model.User;

import java.util.List;

/**
 * Created by vaa25 on 17.07.2015.
 */
public interface UserMapper {

    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "login", column = "login"),
            @Result(property = "password", column = "password"),
            @Result(property = "role", javaType = Role.class, column = "roles_id",
                    one = @One(select = "org.itevents.mapper.RoleMapper.getRole"))
    })
    @Select("SELECT id, login, password, roles_id FROM users WHERE id = #{id}")
    User getUser(int id);

    @ResultMap("getUser-int")
    @Select("SELECT id, login, password, roles_id FROM users")
    List<User> getAllUsers();

    @Insert("INSERT INTO users (login, password, roles_id) VALUES(#{login}, #{password}, #{role.id})")
    @SelectKey(statement = "SELECT id FROM users WHERE login=#{login} AND password=#{password}",
            keyProperty = "id", keyColumn = "id", before = false, resultType = int.class)
    void addUser(User user);

    @Delete("DELETE FROM users WHERE id =#{id}")
    void removeUser(User user);
}
