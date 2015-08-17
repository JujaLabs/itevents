package org.itevents.service;

import org.itevents.model.User;

import java.util.List;

public interface UserService {

    void addUser(User user);

    User getUser(int id);

    List<User> getAllUsers();

    User removeUser(User user);
}
