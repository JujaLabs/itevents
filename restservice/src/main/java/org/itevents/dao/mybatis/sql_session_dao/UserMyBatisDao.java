package org.itevents.dao.mybatis.sql_session_dao;

import org.itevents.dao.UserDao;
import org.itevents.dao.exception.EntityAlreadyExistsDaoException;
import org.itevents.dao.exception.EntityNotFoundDaoException;
import org.itevents.dao.model.Event;
import org.itevents.dao.model.Role;
import org.itevents.dao.model.User;
import org.itevents.util.OneTimePassword.OneTimePassword;
import org.postgresql.util.PSQLException;
import org.springframework.stereotype.Component;

import java.util.Date;
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
    public void addUser(User user, String password) {
        try {
            getSqlSession().insert("org.itevents.dao.mybatis.mapper.UserMapper.addUser", new UserPassword(user, password));
        } catch (Throwable e) {
            Throwable t = e;
            while (t.getCause() != null) {
                t = t.getCause();
                if (t instanceof PSQLException) {
                    throw new EntityAlreadyExistsDaoException(t.getMessage());
                }
            }
        }
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

    @Override
    public String getUserPassword(User user) {
        return getSqlSession().selectOne("org.itevents.dao.mybatis.mapper.UserMapper.getUserPassword", user);
    }

    @Override
    public void setUserPassword(User user, String password) {
        getSqlSession().update("org.itevents.dao.mybatis.mapper.UserMapper.setUserPassword", new UserPassword(user, password));
    }

    @Override
    public void setOtpToUser(User user, OneTimePassword oneTimePassword) {
        getSqlSession().insert("org.itevents.dao.mybatis.mapper.UserMapper.setOtpToUser", new UserOtp(user, oneTimePassword));
    }

    @Override
    public OneTimePassword getOtp(String password) {
        OneTimePassword oneTimePassword = getSqlSession().selectOne("org.itevents.dao.mybatis.mapper.UserMapper.getOtp", password);
        if (oneTimePassword == null) {
            throw new EntityNotFoundDaoException("password " + password + " is not found");
        } else {
            return oneTimePassword;
        }
    }

    @Override
    public User getUserByOtp(OneTimePassword oneTimePassword) {
        User user = getSqlSession().selectOne("org.itevents.dao.mybatis.mapper.UserMapper.getUserByOtp", oneTimePassword);
        if (user == null) {
            throw new EntityNotFoundDaoException("user set to otp" + oneTimePassword.getPassword() + " not found");
        }
        return user;
    }

    private class UserPassword {

        private User user;
        private Role role;
        private String password;

        public UserPassword(User user, String password) {
            this.user = user;
            role = user.getRole();
            this.password = password;
        }

        public int getId() {
            return user.getId();
        }

        public void setId(int id) {
            user.setId(id);
        }

        public String getLogin() {
            return user.getLogin();
        }

        public void setLogin(String login) {
            user.setLogin(login);
        }

        public Role getRole() {
            return role;
        }

        public void setRole(Role role) {
            user.setRole(role);
            this.role = role;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public boolean isSubscribed() {
            return user.isSubscribed();
        }

        public void setSubscribed(boolean subscribed) {
            user.setSubscribed(subscribed);
        }
    }

    private class UserOtp {
        private User user;
        private OneTimePassword oneTimePassword;

        public UserOtp(User user, OneTimePassword oneTimePassword) {
            this.oneTimePassword = oneTimePassword;
            this.user = user;
        }

        public String getPassword() {
            return oneTimePassword.getPassword();
        }

        public void setPassword(String oneTimePssword) {
            oneTimePassword.setPassword(oneTimePssword);
        }

        public Date getExpirationDate() {
            return oneTimePassword.getExpirationDate();
        }

        public void setExpirationDate(Date expirationDate) {
            oneTimePassword.setExpirationDate(expirationDate);
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

        public OneTimePassword getOneTimePassword() {
            return oneTimePassword;
        }

        public void setOneTimePassword(OneTimePassword oneTimePassword) {
            this.oneTimePassword = oneTimePassword;
        }
    }

}
