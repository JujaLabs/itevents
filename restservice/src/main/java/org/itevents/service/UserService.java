package org.itevents.service;

import org.itevents.util.OneTimePassword.OtpGen;
import org.itevents.model.Event;
import org.itevents.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    void addUser(User user);

    User getUser(int userId);

    User getUserByName(String name);

    List<User> getAllUsers();

    User removeUser(User user);

    void activateUser(User user);

    void deactivateUser(User user);

    void addOtp(User user, OtpGen otpGen);

    OtpGen getOtp(User user);

    void DeleteOtp(User user);

    List<Event> getUserEvents(User user);

    List<User> getUsersByEvent(Event event);
}
