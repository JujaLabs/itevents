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

    private final String USER_EMAIL_ADDRESS ="it-events@ex.ua";
    private final String HTML_LATTER ="<html><head><title>TEST MAIL</title></head><body><h1>It's test mail to you "
            + USER_EMAIL_ADDRESS +"</h1><br><img src=\"http://phd.chnebu.ch/images/Java.png\"></body></html>";

    @Inject
    private SendGridMailService mailService;

    @Test
    public void createMail() {
        SendGrid.Email sendGridMail = mailService.createMail(HTML_LATTER, USER_EMAIL_ADDRESS);
        assertNotNull(sendGridMail);
        assertNotNull(sendGridMail.getFrom());
    }

    @Test
    public void testSend() {
        SendGrid.Email sendGridMail = mailService.createMail(HTML_LATTER, USER_EMAIL_ADDRESS);
        boolean isMailSent = mailService.send(sendGridMail);
        assertTrue(isMailSent);
    }

}
