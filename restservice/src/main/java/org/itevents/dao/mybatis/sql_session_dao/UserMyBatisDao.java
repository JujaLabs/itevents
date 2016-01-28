package org.itevents.dao.mybatis.sql_session_dao;

import org.itevents.dao.UserDao;
import org.itevents.dao.exception.EntityNotFoundDaoException;
import org.itevents.model.Event;
import org.itevents.model.User;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by vaa25 on 03.11.2015.
 */
@Component("userDao")
public class UserMyBatisDao extends AbstractMyBatisDao implements UserDao {

    @Override
    public User getUser(int id) {
        User user = getSqlSession().selectOne("org.itevents.dao.mybatis.mapper.UserMapper.getUser", id);
        if (user == null) {
            throw new EntityNotFoundDaoException("User with id = " + id + " not found");
        }
        return user;
    }

    @Override
    public User getUserByName(String name) {
        User user = getSqlSession().selectOne("org.itevents.dao.mybatis.mapper.UserMapper.getUserByName", name);
        if (user == null) {
            throw new EntityNotFoundDaoException("User with login '" + name + "' not found");
        }
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        return getSqlSession().selectList("org.itevents.dao.mybatis.mapper.UserMapper.getAllUsers");
    }

    @Override
    public void addUser(User user) {
        getSqlSession().insert("org.itevents.dao.mybatis.mapper.UserMapper.addUser", user);
    }

    @Override
    public void updateUser(User user) {
        getSqlSession().update("org.itevents.dao.mybatis.mapper.UserMapper.updateUser", user);
    }

    @Override
    public List<User> getUsersByEvent(Event event) {
        return getSqlSession().selectList("org.itevents.dao.mybatis.mapper.UserMapper.getUsersByEvent", event);
    }

    @Override
    public List<User> getSubscribedUsers() {
        return getSqlSession().selectList("org.itevents.dao.mybatis.mapper.UserMapper.getSubscribedUsers");
    }

}
