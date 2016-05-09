package org.itevents.service.sendmail;
import com.sendgrid.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

/**
 * Created by max on 29.07.15.
 */
@Service("mailService")
public class SendGridMailService implements MailService {

    private static final String FROM_MAIL = "events@juja.com.ua";
    private static final String SUBJECT_MAIL = "IT Events";

    @Inject
    private SendGrid sendgrid;

    public void sendMail(String htmlLetter, String emailAddress){
        SendGrid.Email email = createMail(htmlLetter, emailAddress);
        send(email);
    }

    private void send(SendGrid.Email email) {
        try {
            sendgrid.send(email);
        } catch (SendGridException e) {
            throw new NotificationServiceException(e.getMessage(), e);
        }
    }

    private SendGrid.Email createMail(String htmlLetter, String emailAddress) {
        return new SendGrid
                .Email()
                .addTo(emailAddress)
                .setFrom(FROM_MAIL)
                .setSubject(SUBJECT_MAIL)
                .setHtml(htmlLetter);
    }
}
