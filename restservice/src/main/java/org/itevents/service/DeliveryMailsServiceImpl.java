package org.itevents.service;
import com.sendgrid.*;
import org.itevents.model.User;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by max on 29.07.15.
 */
public class DeliveryMailsServiceImpl implements DeliveryMailsService {

    private String api_key=getApi_key();
    public static String fromMail = "events@juja.com.ua";
    private String subjectMail = "IT Events of the week";
    InputStream input = null;

    public String getApi_key(){
        try {
            Properties sendGridProperty = new Properties();
            input = DeliveryMailsServiceImpl.class.getClassLoader().getResourceAsStream("local.properties");
            sendGridProperty.load(input);
            return sendGridProperty.getProperty("api_key");
        } catch (IOException e) {
            System.err.println("ERROR: File local.properties not found.");
            return null;
        }finally {
            try {
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void sendMail(String htmlLetter, User user){

        SendGrid sendgrid = new SendGrid(api_key);
        SendGrid.Email email = new SendGrid.Email();
        email.addTo(user.getLogin());
        email.setFrom(fromMail);
        email.setSubject(subjectMail);
        email.setHtml(htmlLetter);

        try {
            SendGrid.Response response = sendgrid.send(email);
            System.out.println(response.getMessage());
        } catch (SendGridException e) {
            System.err.println(e);
        }
    }
}
