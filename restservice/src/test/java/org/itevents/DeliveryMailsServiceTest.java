package org.itevents;

import org.itevents.model.Role;
import org.itevents.model.User;
import org.junit.Test;
import org.itevents.service.DeliveryMailsService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by max on 30.07.15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContext.xml" })
public class DeliveryMailsServiceTest {

    String userMail ="ramax@ex.ua"; //Enter your e-mail, which will receive letter
    String htmlLetter ="<html><head><title>TEST MAIL</title></head><body><h1>It's test mail to you "+userMail+"</h1><br><img src=\"http://phd.chnebu.ch/images/Java.png\"></body></html>";

    @Autowired
    DeliveryMailsService deliveryMailsService;

    @Test
    public void testSendMail(){
        deliveryMailsService.sendMail(htmlLetter,userMail);
    }
}
