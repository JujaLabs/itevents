package org.itevents;

import org.itevents.model.Role;
import org.itevents.model.User;
import org.junit.Test;
import org.itevents.service.SendGridService;
import org.itevents.service.SendGridServiceImpl;

/**
 * Created by max on 30.07.15.
 */
public class SendGridTest {

    String userLogin ="example@it-events.org"; //Enter your e-mail, which will receive letter

    SendGridService sendGridService = new SendGridServiceImpl();
    @Test
    public void testSendMail(){
        User user = new User(userLogin, "testPassword", new Role("testRole"));
        sendGridService.sendMail("It's a test text to "+user.getLogin(),user);
    }
}
