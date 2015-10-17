package org.itevents.dao;

import org.itevents.model.Filter;
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

    void putFilter(User user, Filter filter);

    void removeFilter(User user);

    Filter getFilter(User user);
}
