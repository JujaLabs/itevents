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
public class MailServiceDelivery implements MailService {

    private static final Logger logger = LogManager.getLogger();
    private static final String FROM_MAIL = "events@juja.com.ua";
    private static final String SUBJECT_MAIL = "IT Events";

    @Value("${api_key}")
    private String apiKey;

    private SendGrid sendgrid;

    @PostConstruct
    public void init() {
        sendgrid = new SendGrid(apiKey);
    }

    public void sendMail(String htmlLetter, String mail){

        SendGrid.Email email = new SendGrid.Email();
        email.addTo(mail);
        email.setFrom(FROM_MAIL);
        email.setSubject(SUBJECT_MAIL);
        email.setHtml(htmlLetter);

        try {
            SendGrid.Response response = sendgrid.send(email);
        } catch (SendGridException e) {
            logger.error("Email sending error",e);
        }
    }
}
