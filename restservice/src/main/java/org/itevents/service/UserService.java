package org.itevents.service;

import org.itevents.dao.model.Event;
import org.itevents.dao.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    void addSubscriber(String name, String password);

    User getUser(int userId);

    User getUserByName(String name);

    User getAuthorizedUser();

    void activateUserSubscription(User user);

    void deactivateUserSubscription(User user);

    List<User> getAllUsers();

    List<User> getSubscribedUsers();

    List<User> getUsersByEvent(Event event);

    void setAndEncodeUserPassword(User user, String password);

    String getUserPassword(User user);

    void checkPassword(User user, String password);
}
