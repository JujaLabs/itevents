package org.itevents.util.mail;

import org.itevents.dao.model.Event;
import org.itevents.dao.model.User;
import org.itevents.test_utils.BuilderUtil;
import org.itevents.util.OneTimePassword.OtpGenerator;
import org.junit.Ignore;
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
    private OtpGenerator otpGenerator;

    @Test
    public void testMailBuild() throws JAXBException, ParseException, IOException, TransformerException {
        List<Event> filteredEvents = BuilderUtil.buildEventsForMailUtilTest();
        String returnedDigestEmail = mailBuilderUtil.buildHtmlFromEventsList(filteredEvents);
        assertEquals(expectedDigestEmail, returnedDigestEmail);
    }

    /*
    * @TODO: FIX THIS TEST
    * issue 156
    * https://github.com/JuniorsJava/itevents/issues/156
    */
    @Test
    @Ignore
    public void shouldReturnMailWithActivationLink()  throws Exception {
        User user = BuilderUtil.buildUserAnakin();
        otpGenerator.generateOtp(1440);
        String returnedUserOtpEmail = mailBuilderUtil.buildHtmlFromUserOtp(user, otpGenerator);
        assertEquals(expectedUserOtpEmail,returnedUserOtpEmail);
    }
}
