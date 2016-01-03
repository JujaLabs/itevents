package org.itevents.service;

import org.itevents.util.OneTimePassword.OtpGenerator;
import org.itevents.model.Event;
import org.itevents.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    void addUser(User user);

    User getUser(int userId);

    User getUserByName(String name);

    User getAuthorizedUser();

    void activateUserSubscription(User user);

    void deactivateUserSubscription(User user);

    List<User> getAllUsers();

    User removeUser(User user);

    void activateUser(User user);

    void deactivateUser(User user);

    void addOtp(User user, OtpGenerator otpGenerator);

    OtpGenerator getOtp(User user);

    void DeleteOtp(User user);

    List<Event> getUserEvents(User user);

    List<User> getSubscribedUsers();

    List<User> getUsersByEvent(Event event);
}
