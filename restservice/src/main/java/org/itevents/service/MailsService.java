package org.itevents.service;

import org.itevents.model.User;

public interface MailsService {
    void sendMail(String htmlLetter, String mail);
}
