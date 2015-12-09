package org.itevents.dao;

import org.itevents.model.Event;
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

    void updateUser(User user);

    List<User> getUsersByEvent(Event event);
}
