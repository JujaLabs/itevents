package org.itevents.dao;

import org.itevents.model.Event;
import org.itevents.model.User;
import org.itevents.util.OneTimePassword.OtpGenerator;

import java.util.List;

/**
 * Created by vaa25 on 17.07.2015.
 */
public interface UserDao {

    User getUser(int id);

    User getUserByName(String name);

    List<User> getAllUsers();

    void addUser(User user, String password);

    void setOtpToUser(User user, OtpGenerator otpGenerator);

    OtpGenerator getOtp(String password);

    User getUserByOtp(OtpGenerator otpGenerator);

    void updateUser(User user);

    List<User> getUsersByEvent(Event event);

    List<User> getSubscribedUsers();

    String getUserPassword(User user);

    void setUserPassword(User user, String password);
}
