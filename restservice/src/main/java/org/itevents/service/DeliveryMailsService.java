package org.itevents.service;

import org.itevents.model.User;

public interface DeliveryMailsService {

    void sendMail(String htmlLetter, User user);
}
