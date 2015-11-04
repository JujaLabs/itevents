package org.itevents.service;

import org.itevents.model.Otp;
import org.itevents.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    void addUser(User user);

    User getUser(int id);

    User getUserByName(String name);

    List<User> getAllUsers();

    User removeUser(User user);

    void activateUser(User user);

    void deactivateUser(User user);

    void addOtp(User user, Otp otp);

    Otp getOtp(User user);

    void DeleteOtp(User user);
}
