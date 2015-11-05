package org.itevents.dao.mybatis.mapper;

import org.apache.ibatis.annotations.*;
import org.itevents.model.Role;
import org.itevents.model.User;

import java.util.List;

/**
 * Created by vaa25 on 17.07.2015.
 */
public interface UserMapperSql {

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

    @Insert("INSERT INTO user_profile (login, password, role_id, subscribed) VALUES(#{login}, #{password}, " +
            "#{role.id}, #{subscribed})")
    @Options(useGeneratedKeys = true)
    void addUser(User user);

    @Update("UPDATE user_profile SET login=#{login}, password=#{password}, role_id=#{role.id}, subscribed=#{subscribed} " +
            "WHERE id=#{id}")
    void updateUser(User user);
}