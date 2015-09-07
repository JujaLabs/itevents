package org.itevents.dao;

import org.itevents.model.User;

import java.util.List;

/**
 * Created by vaa25 on 17.07.2015.
 */
public interface UserDao {

    User getUser(int id);

    User getUserByName(String name);

    List<User> getAllUsers();

    void addUser(User user);

    void removeUser(User user);

    void subscribeToEvent(User user);

    void getUserEvents(User user);
}
