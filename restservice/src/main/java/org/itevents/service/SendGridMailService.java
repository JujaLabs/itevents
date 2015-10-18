package org.itevents.service;
import com.sendgrid.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;


/**
 * Created by max on 29.07.15.
 */
@Service
public class SendGridMailService implements MailService {

    private static final Logger logger = LogManager.getLogger();
    private static final String FROM_MAIL = "events@juja.com.ua";
    private static final String SUBJECT_MAIL = "IT Events";

    //@alex-anakin: remove redundant variable
    @Value("${api_key}")
    private String apiKey;

    //@alex-anakin: declare bean in Spring context
    private SendGrid sendgrid;

    //@alex-anakin: remove redundant init-method
    @PostConstruct
    public void init() {
        sendgrid = new SendGrid(apiKey);
    }

    //@alex-anakin: rename variable 'mail' more suitable to sense
    public void sendMail(String htmlLetter, String mail){

        //@alex-anakin: Need refactoring
        // 1. Make separate method #1 for creating mail and test for it
        // 2. Make separate method # 2 to sending mail and test for it
        // 3. Call methods #1 and #2 in third method
        SendGrid.Email email = new SendGrid.Email();
        email.addTo(mail);
        email.setFrom(FROM_MAIL);
        email.setSubject(SUBJECT_MAIL);
        email.setHtml(htmlLetter);


        //@alex-anakin: Make sending in method #2 and return boolean (success or not) from it
        // Need to log unsuccessful message
        try {
            //@alex-anakin: variable 'response' is never used
            SendGrid.Response response = sendgrid.send(email);
        } catch (SendGridException e) {
            //@alex-anakin: need to log address of recipient
            logger.error("Email sending error",e);
        }
    }
}
