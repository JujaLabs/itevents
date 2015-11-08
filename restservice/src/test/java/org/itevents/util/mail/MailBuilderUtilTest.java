package org.itevents.util.mail;

import org.itevents.model.Event;
import org.itevents.model.Otp;
import org.itevents.model.User;
import org.itevents.test_utils.BuilderUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import javax.xml.bind.JAXBException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:applicationContextTestAddon.xml"})
public class MailBuilderUtilTest {
    @Inject
    String expectedDigestEmail;
    @Inject
    String expectedUserOtpEmail;

    @Inject
    MailBuilderUtil mailBuilderUtil;

    @Test
    public void testMailBuild() throws JAXBException, ParseException, IOException, TransformerException {
        List<Event> filteredEvents = BuilderUtil.buildEventsForMailUtilTest();
        String returnedDigestEmail = mailBuilderUtil.buildHtmlFromEventsList(filteredEvents);
        assertEquals(expectedDigestEmail, returnedDigestEmail);
    }

    @Test
    public void shouldReturnMailWithUsernameAndOtp()  throws Exception {
        User user = BuilderUtil.buildUserAnakin();
        Otp otp = new Otp();
        otp.generateOtp(1440);
        String returnedUserOtpEmail = mailBuilderUtil.buildHtmlFromUserOtp(user,otp);
        assertEquals(expectedUserOtpEmail,returnedUserOtpEmail);
    }
}
