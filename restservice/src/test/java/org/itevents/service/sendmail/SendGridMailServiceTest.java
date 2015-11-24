package org.itevents.service.sendmail;

import com.sendgrid.SendGrid;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import com.sendgrid.SendGridException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.BeforeTransaction;

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

    @InjectMocks
    private SendGridMailService mailService;

    @Inject
    @Mock
    private SendGrid sendGrid;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldSendMail() throws SendGridException {
        when(sendGrid.send( any(SendGrid.Email.class) )).thenReturn(
                new SendGrid.Response(HttpStatus.OK.value(),"ok")
        );
        mailService.sendMail(HTML_LATTER, USER_EMAIL_ADDRESS);
        verify(sendGrid).send( any(SendGrid.Email.class) );
    }


}
