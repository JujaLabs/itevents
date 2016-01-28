package org.itevents.dao.mybatis.sql_session_dao;

import org.itevents.dao.RoleDao;
import org.itevents.dao.exception.EntityNotFoundDaoException;
import org.itevents.model.Role;
import org.mybatis.spring.support.SqlSessionDaoSupport;

/**
 * Created by vaa25 on 09.12.2015.
 */
public class RoleMyBatisDao extends SqlSessionDaoSupport implements RoleDao {
    @Override
    public Role getRole(int id) {
        Role role = getSqlSession().selectOne("org.itevents.dao.mybatis.mapper.RoleMapper.getRole", id);
        if (role == null) {
            throw new EntityNotFoundDaoException("Role with id = " + id + " not found");
        }
        return role;
    }

    @Override
    public Role getRoleByName(String name) {
        Role role = getSqlSession().selectOne("org.itevents.dao.mybatis.mapper.RoleMapper.getRoleByName", name);
        if (role == null) {
            throw new EntityNotFoundDaoException("Role with name = " + name + " not found");
        }
        return role;
    }
}
