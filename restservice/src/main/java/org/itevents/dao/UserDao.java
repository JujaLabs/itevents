package org.itevents.dao;

import org.itevents.util.OneTimePassword.OtpGen;
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

    void removeUser(User user);

    void activateUser(User user);

    void deactivateUser(User user);

    void addOtp(User user, OtpGen otpGen);

    OtpGen getOtp(User user);

    void DeleteOtp(User user);

    List<User> getUsersByEvent(Event event);
}
