package org.itevents.mapper;

import org.apache.ibatis.annotations.*;
import org.itevents.model.Role;

import java.util.List;

/**
 * Created by vaa25 on 17.07.2015.
 */
public interface RoleMapper {

    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
    })
    @Select("SELECT id, name FROM roles WHERE id = #{id}")
    Role getRole(int id);

    @ResultMap("getRole-int")
    @Select("SELECT id, name FROM roles")
    List<Role> getAllRoles();

    @Insert("INSERT INTO roles (name) VALUES(#{name}")
    void addRole(Role role);

    @ResultMap("getRole-int")
    @Delete("DELETE FROM roles WHERE id =#{id}")
    Role removeRole(Role role);
}
