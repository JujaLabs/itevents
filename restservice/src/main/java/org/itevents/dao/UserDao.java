package org.itevents.dao;

import org.itevents.model.User;

import java.util.List;

/**
 * Created by vaa25 on 17.07.2015.
 */
public interface UserDao {

    User getUser(int id);

    List<User> getAllUsers();

    void addUser(User user);

    void removeUser(User user);
}
