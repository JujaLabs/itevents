package org.itevents.util.mail;

import org.apache.xalan.extensions.XSLProcessorContext;
import org.apache.xalan.templates.ElemExtensionCall;
import org.itevents.model.Event;
import org.itevents.model.User;
import org.itevents.test_utils.BuilderUtil;
import org.itevents.util.OneTimePassword.OtpGen;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
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
    @Inject
    OtpGen otpGen;
    @Inject
    BuilderUrl url;
    @Value("${serverName}")
    private String serverName;
    @Value("${httpPort}")
    private String httpPort;

    @Test
    public void testMailBuild() throws JAXBException, ParseException, IOException, TransformerException {
        List<Event> filteredEvents = BuilderUtil.buildEventsForMailUtilTest();
        String returnedDigestEmail = mailBuilderUtil.buildHtmlFromEventsList(filteredEvents);
        assertEquals(expectedDigestEmail, returnedDigestEmail);
    }

    @Test
    public void shouldReturnMailWithLinkToActivate()  throws Exception {
        XSLProcessorContext context = null;
        ElemExtensionCall elem = null;
        User user = BuilderUtil.buildUserAnakin();
        otpGen.generateOtp(1440);
        url.buildUrl(context, elem);
        String returnedUserOtpEmail = mailBuilderUtil.buildHtmlFromUserOtp(user, otpGen, url);
        assertEquals(expectedUserOtpEmail,returnedUserOtpEmail);
    }
}