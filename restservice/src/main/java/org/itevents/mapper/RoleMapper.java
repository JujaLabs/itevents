package org.itevents.mapper;

import org.apache.ibatis.annotations.*;
import org.itevents.model.Role;

import java.util.List;

/**
 * Created by vaa25 on 17.07.2015.
 */
public interface RoleMapper {

    @ResultType(Role.class)
    @Select("SELECT id, name FROM roles WHERE id = #{id}")
    Role getRole(int id);

    @ResultType(Role.class)
    @Select("SELECT id, name FROM roles")
    List<Role> getAllRoles();

    @Insert("INSERT INTO roles (name) VALUES(#{name})")
    @SelectKey(statement = "SELECT id FROM roles WHERE name=#{name}",
            keyProperty = "id", keyColumn = "id", before = false, resultType = int.class)
    void addRole(Role role);

    @Delete("DELETE FROM roles WHERE id =#{id}")
    void removeRole(Role role);
}
