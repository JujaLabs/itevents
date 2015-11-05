package org.itevents.dao.mybatis.exception_mapper;

import org.itevents.dao.UserDao;
import org.itevents.dao.exception.EntityNotFoundDaoException;
import org.itevents.model.User;
import org.mybatis.spring.support.SqlSessionDaoSupport;

import java.util.List;

/**
 * Created by vaa25 on 03.11.2015.
 */

public class UserMapper extends SqlSessionDaoSupport implements UserDao {

    @Override
    public User getUser(int id) {
        User user = getSqlSession().selectOne("org.itevents.dao.mybatis.mapper.UserMapperSql.getUser", id);
        if (user == null) {
            throw new EntityNotFoundDaoException("User with id = " + id + " not found");
        }
        return user;
    }

    @Override
    public User getUserByName(String name) {
        User user = getSqlSession().selectOne("org.itevents.dao.mybatis.mapper.UserMapperSql.getUserByName", name);
        if (user == null) {
            throw new EntityNotFoundDaoException("User with login '" + name + "' not found");
        }
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        return getSqlSession().selectList("org.itevents.dao.mybatis.mapper.UserMapperSql.getAllUsers");
    }

    @Override
    public void addUser(User user) {
        getSqlSession().insert("org.itevents.dao.mybatis.mapper.UserMapperSql.addUser", user);
    }

    @Override
    public void updateUser(User user) {
        getSqlSession().update("org.itevents.dao.mybatis.mapper.UserMapperSql.updateUser", user);
    }

}
