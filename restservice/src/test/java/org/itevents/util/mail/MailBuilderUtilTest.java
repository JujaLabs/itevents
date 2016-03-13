package org.itevents.util.mail;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import javax.inject.Inject;
import javax.xml.bind.JAXBException;
import javax.xml.transform.TransformerException;
import org.itevents.dao.model.Event;
import org.itevents.dao.model.User;
import org.itevents.test_utils.BuilderUtil;
import org.itevents.util.OneTimePassword.OneTimePassword;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:applicationContext.xml",
        "classpath:applicationContextTestAddon.xml"})
public class MailBuilderUtilTest {
    @Inject
    private String expectedDigestEmail;
    @Inject
    private String expectedUserOtpEmail;
    @Inject
    private MailBuilderUtil mailBuilderUtil;
    @Inject
    private OneTimePassword oneTimePassword;

    @Test
    public void testMailBuild() throws JAXBException, ParseException, IOException, TransformerException {
        List<Event> filteredEvents = BuilderUtil.buildEventsForMailUtilTest();
        String returnedDigestEmail = mailBuilderUtil.buildHtmlFromEventsList(filteredEvents);
        assertEquals(expectedDigestEmail, returnedDigestEmail);
    }

    @Test
    public void shouldReturnMailWithActivationLink()  throws Exception {
        int twoMonthInHours = 1440;
        User user = BuilderUtil.buildUserAnakin();
        String confirmationUrl = "http://localhost:8080/users/activate/68eeea0d-f89d-4634-aa52-67da452eadb0";
        oneTimePassword.generateOtp(twoMonthInHours);
        String returnedUserOtpEmail = mailBuilderUtil.buildHtmlFromUserOtp(user, oneTimePassword, confirmationUrl);
        assertEquals(expectedUserOtpEmail,returnedUserOtpEmail);
    }
}