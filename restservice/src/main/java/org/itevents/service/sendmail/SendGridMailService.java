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

    private static final Logger LOGGER = LogManager.getLogger();
    private static final String FROM_MAIL = "events@juja.com.ua";
    private static final String SUBJECT_MAIL = "IT Events";

    @Inject
    private SendGrid sendgrid;

    public void sendMail(String htmlLetter, String emailAddress){

        SendGrid.Email email = createMail(htmlLetter, emailAddress);
        boolean isEmailSent = send(email);

        if (!isEmailSent) {
            LOGGER.error("Email sending error: ", emailAddress);
        }

    }

    public boolean send(SendGrid.Email email) {
        try {
            return sendgrid.send(email).getStatus();
        } catch (SendGridException e) {
            LOGGER.error("Email sending error: ", email.getTos());
            return false;
        }
    }

    public SendGrid.Email createMail(String htmlLetter, String emailAddress) {
        return new SendGrid
                .Email()
                .addTo(emailAddress)
                .setFrom(FROM_MAIL)
                .setSubject(SUBJECT_MAIL)
                .setHtml(htmlLetter);
    }
}
