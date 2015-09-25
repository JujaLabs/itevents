package org.itevents.mapper;

import org.apache.ibatis.annotations.*;
import org.itevents.model.Role;

import java.util.List;

/**
 * Created by vaa25 on 17.07.2015.
 */
public interface RoleMapper {

    @ResultType(Role.class)
    @Select("SELECT * FROM role WHERE id = #{id}")
    Role getRole(int id);

    @ResultType(Role.class)
    @Select("SELECT * FROM role")
    List<Role> getAllRoles();

    @Insert("INSERT INTO role (name) VALUES(#{name})")
    @Options(useGeneratedKeys = true)
    void addRole(Role role);

    @Delete("DELETE FROM role WHERE id =#{id}")
    void removeRole(Role role);
}
