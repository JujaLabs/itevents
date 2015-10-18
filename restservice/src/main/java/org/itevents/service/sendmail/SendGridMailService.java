package org.itevents.service.sendmail;
import com.sendgrid.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by max on 29.07.15.
 */
public class SendGridMailService implements MailService {

    private static final Logger logger = LogManager.getLogger();
    private static final String FROM_MAIL = "events@juja.com.ua";
    private static final String SUBJECT_MAIL = "IT Events";

    private SendGrid sendgrid;

    public SendGridMailService(SendGrid sendgrid) {
        this.sendgrid = sendgrid;
    }

    public void sendMail(String htmlLetter, String emailAddress){

        SendGrid.Email email = createMail(htmlLetter, emailAddress);
        boolean status = send(email);

        if (!status) {
            logger.error("Email sending error: ", emailAddress);
        }

    }

    public boolean send(SendGrid.Email email) {
        try {
            return sendgrid.send(email).getStatus();
        } catch (SendGridException e) {
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
