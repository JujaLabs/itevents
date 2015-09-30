package org.itevents;

import org.junit.Test;
import org.itevents.service.MailService;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;

/**
 * Created by max on 30.07.15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:applicationContext.xml" })
public class MailServiceTest {

    String userMail ="example@it-events.com"; //Enter your e-mail, which will receive letter
    String htmlLetter ="<html><head><title>TEST MAIL</title></head><body><h1>It's test mail to you "+userMail+"</h1><br><img src=\"http://phd.chnebu.ch/images/Java.png\"></body></html>";

    @Inject
    MailService mailsService;

    @Test
    public void testSendMail(){
        mailsService.sendMail(htmlLetter,userMail);
    }
}