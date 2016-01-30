package org.itevents.service.transactional;

import org.itevents.dao.UserDao;
import org.itevents.model.Event;
import org.itevents.model.User;
import org.itevents.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

@Service("userService")
@Transactional
public class MyBatisUserService implements UserService {

    @Inject
    private UserDao userDao;

    @Override
    public void addUser(User user, String password) {
        userDao.addUser(user, password);
    }

    @Override
    public User getUser(int id) {
        return userDao.getUser(id);
    }

    @Override
    public User getUserByName(String name) {
        return userDao.getUserByName(name);
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public User getAuthorizedUser() {
        return getUserByName(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public void activateUserSubscription(User user) {
        user.setSubscribed(true);
        userDao.updateUser(user);
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public void deactivateUserSubscription(User user) {
        user.setSubscribed(false);
        userDao.updateUser(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Override
    public List<User> getSubscribedUsers() {
        return userDao.getSubscribedUsers();
    }

    @Override
    public List<User> getUsersByEvent(Event event) {
        return userDao.getUsersByEvent(event);
    }

    @Override
    public String getUserPassword(User user) {
        return userDao.getUserPassword(user);
    }

    @Override
    public void setUserPassword(User user, String password) {
        userDao.setUserPassword(user, password);
    }
}
