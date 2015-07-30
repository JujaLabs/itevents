package org.itevents.service;

import org.itevents.model.User;

public interface SendGridService {

    void sendMail(String message, User user);
}
