package org.itevents.service.sendmail;

public interface MailService {
    void sendMail(String htmlLetter, String mailAddress);
}
