package org.itevents.service;
import com.sendgrid.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by max on 29.07.15.
 */
public class MailsServiceImpl implements MailsService {

    private static final String FROM_MAIL = "events@juja.com.ua";
    private static final String SUBJECT_MAIL = "IT Events";

    private SendGrid sendgrid;

    public MailsServiceImpl() {
        String apiKey = getApiKeyFromProperties();
        sendgrid = new SendGrid(apiKey);
    }

    public String getApiKeyFromProperties(){
        try(
                InputStream input = getClass().getClassLoader().getResourceAsStream("local.properties");
        ){
            Properties sendGridProperty = new Properties();
            sendGridProperty.load(input);
            return sendGridProperty.getProperty("api_key");
        } catch (IOException e) {
            System.err.println("ERROR: File local.properties not found.");
            return null;
        }
    }

    public void sendMail(String htmlLetter, String mail){

        SendGrid.Email email = new SendGrid.Email();
        email.addTo(mail);
        email.setFrom(FROM_MAIL);
        email.setSubject(SUBJECT_MAIL);
        email.setHtml(htmlLetter);

        try {
            SendGrid.Response response = sendgrid.send(email);
            System.out.println(response.getMessage());
        } catch (SendGridException e) {
            System.err.println(e);
        }
    }
}
