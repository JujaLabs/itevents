package org.itevents;

import org.itevents.model.Role;
import org.itevents.model.User;
import org.junit.Test;
import org.itevents.service.SendGridService;
import org.itevents.service.SendGridServiceImpl;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Created by max on 30.07.15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContext.xml" })
public class SendGridTest {

    String userLogin ="example@it-events.org"; //Enter your e-mail, which will receive letter

    @Autowired
    SendGridService sendGridService;

    @Test
    public void testSendMail(){
        User user = new User(userLogin, "testPassword", new Role("testRole"));
        sendGridService.sendMail("It's a test text to "+user.getLogin(),user);
    }
}
