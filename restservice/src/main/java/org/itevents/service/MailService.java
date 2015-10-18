package org.itevents.service;

import org.itevents.model.User;

public interface MailService {
    void sendMail(String htmlLetter, String mail);
}
