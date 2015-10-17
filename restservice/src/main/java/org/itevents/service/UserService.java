package org.itevents.service;

import org.itevents.model.Filter;
import org.itevents.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    void addUser(User user);

    User getUser(int id);

    User getUserByName(String name);

    User getAuthorizedUser();

    List<User> getAllUsers();

    User removeUser(User user);

    void putFilter(User user, Filter filter);

    Filter removeFilter(User user);
}
