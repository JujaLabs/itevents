package org.itevents.dao.mybatis.mapper;

import org.apache.ibatis.annotations.*;
import org.itevents.dao.RoleDao;
import org.itevents.model.Role;

import java.util.List;

/**
 * Created by vaa25 on 17.07.2015.
 */
public interface RoleMapper extends RoleDao {

    @Override
    @ResultType(Role.class)
    @Select("SELECT * FROM role WHERE id = #{id}")
    Role getRole(int id);

    @Override
    @ResultType(Role.class)
    @Select("SELECT * FROM role WHERE name = #{name}")
    Role getRoleByName(String name);

    @Override
    @ResultType(Role.class)
    @Select("SELECT * FROM role ORDER BY name")
    List<Role> getAllRoles();

    @Override
    @Insert("INSERT INTO role (name) VALUES(#{name})")
    @Options(useGeneratedKeys = true)
    void addRole(Role role);

    @Override
    @Delete("DELETE FROM role WHERE id =#{id}")
    void removeRole(Role role);
}
