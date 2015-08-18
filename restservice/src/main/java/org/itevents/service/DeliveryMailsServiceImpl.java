package org.itevents.service;
import com.sendgrid.*;
import org.itevents.model.User;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by max on 29.07.15.
 */
public class DeliveryMailsServiceImpl implements DeliveryMailsService {

    private String api_key=getApi_key();

    public String getApi_key(){
        try {
            FileInputStream fis;
            Properties sendGridProperty = new Properties();
            fis = new FileInputStream("./src/main/resources/local.properties");
            sendGridProperty.load(fis);
            return sendGridProperty.getProperty("api_key");
        } catch (IOException e) {
            System.err.println("ERROR: File local.properties not found.");
            return null;
        }
    }
    public void sendMail(String htmlLetter, User user){

        SendGrid sendgrid = new SendGrid(api_key);
        SendGrid.Email email = new SendGrid.Email();
        email.addTo(user.getLogin());
        email.setFrom("events@juja.com.ua");
        email.setSubject("IT Events of the week");
        email.setHtml(htmlLetter);

        try {
            SendGrid.Response response = sendgrid.send(email);
            System.out.println(response.getMessage());
        } catch (SendGridException e) {
            System.err.println(e);
        }
    }
}
