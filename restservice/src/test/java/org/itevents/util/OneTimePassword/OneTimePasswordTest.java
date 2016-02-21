package org.itevents.util.OneTimePassword;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;

import static org.junit.Assert.assertNotEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class OneTimePasswordTest {

    public static final int LIFETIME_IN_MINUTES = 1440;

    @Inject
    private OneTimePassword oneTimePassword;

    @Test
    public void shouldGenerateDifferentPasswords() throws Exception {
        String password1 = oneTimePassword.generateOtp(LIFETIME_IN_MINUTES).getPassword();
        String password2 = oneTimePassword.generateOtp(LIFETIME_IN_MINUTES).getPassword();
        assertNotEquals(password1, password2);
    }
}