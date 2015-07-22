package org.itevents.mapper;

import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.itevents.model.Role;
import org.itevents.model.User;

/**
 * Created by vaa25 on 17.07.2015.
 */
public interface UserMapper {

    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "login", column = "login"),
            @Result(property = "password", column = "password"),
            @Result(property = "role", javaType = Role.class, column = "role_id", one = @One(select = "org.itevents.mapper.RoleMapper.getRole"))
    })
    @Select("SELECT id, login, password FROM users WHERE id = #{id}")
    User getUser(int id);
}
