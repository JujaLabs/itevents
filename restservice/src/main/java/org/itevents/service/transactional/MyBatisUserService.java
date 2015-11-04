package org.itevents.service.transactional;

import org.itevents.dao.UserDao;
import org.itevents.model.Otp;
import org.itevents.model.Event;
import org.itevents.model.User;
import org.itevents.service.UserService;
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
    public void addUser(User user) {
        userDao.addUser(user);
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
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Override
    public User removeUser(User user) {
        User deletingUser = userDao.getUser(user.getId());
        if (deletingUser != null) {
            userDao.removeUser(user);
        }
        return deletingUser;
    }

    @Override
    public void activateUser(User user) {
        userDao.activateUser(user);
    }

    @Override
    public void deactivateUser(User user) {
        userDao.deactivateUser(user);
    }

    @Override
    public void addOtp(User user, Otp otp) {
        userDao.addOtp(user,otp);
    }

    @Override
    public Otp getOtp(User user) {
        return userDao.getOtp(user);
    }

    @Override
    public void DeleteOtp(User user) {
        userDao.DeleteOtp(user);
    }

    @Override
    public List<Event> getUserEvents(User user) {
       return userDao.getUserEvents(user);
    }

}
