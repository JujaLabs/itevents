package org.itevents.service;

import org.itevents.model.Event;
import org.itevents.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    void addUser(User user, String password);

    User getUser(int userId);

    User getUserByName(String name);

    User getAuthorizedUser();

    void activateUserSubscription(User user);

    void deactivateUserSubscription(User user);

    List<User> getAllUsers();

    List<User> getSubscribedUsers();

    List<User> getUsersByEvent(Event event);

    String getUserPassword(User user);

    void setUserPassword(User user, String password);
}
