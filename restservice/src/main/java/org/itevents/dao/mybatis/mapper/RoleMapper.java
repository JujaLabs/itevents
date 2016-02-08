package org.itevents.dao.mybatis.mapper;

import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.itevents.dao.RoleDao;
import org.itevents.dao.model.Role;

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
}
