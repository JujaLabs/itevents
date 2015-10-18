package org.itevents.service.sendmail;

import com.sendgrid.SendGrid;
import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;

/**
 * Created by max on 30.07.15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:applicationContext.xml" })
public class SendGridMailServiceTest {

    String userMail ="it-events@ex.ua";
    String htmlLetter ="<html><head><title>TEST MAIL</title></head><body><h1>It's test mail to you "
            +userMail+"</h1><br><img src=\"http://phd.chnebu.ch/images/Java.png\"></body></html>";

    @Inject
    SendGridMailService mailService;

    public SendGrid.Email createMail() {
        SendGrid.Email sendGridMail = mailService.createMail(htmlLetter, userMail);
        assertNotNull(sendGridMail);
        assertNotNull(sendGridMail.getFrom());

        return sendGridMail;
    }

    @Test
    public void testSend() {
        boolean status = mailService.send(createMail());
        assertTrue(status);
    }

}
