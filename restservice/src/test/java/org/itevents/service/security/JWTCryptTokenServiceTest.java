package org.itevents.service.security;

import org.itevents.service.CryptTokenService;
import org.itevents.service.exception.CryptTokenServiceException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by ramax on 2/1/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class JWTCryptTokenServiceTest {

    // Token generate from http://jwt.io/
    private final String KEY = "someSecretKey";
    private final String ENCODED_TOKEN =
            "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6InJhbWF4Iiwicm9s" +
            "ZSI6ImFkbWluIn0.4RdnMCRvtqUY_SDNmVJ_2VyPatbLtd-HPnf8hpk22AM";
    private final Token TOKEN = new Token("ramax","admin");
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    @InjectMocks
    private CryptTokenService cryptTokenService = new JWTCryptTokenService(KEY);

    @Test
    public void shouldEncryptToken() throws Exception {
        String encodedToken = cryptTokenService.encrypt(TOKEN);

        assertThat(encodedToken, is(ENCODED_TOKEN));
    }

    @Test
    public void shouldDecryptToken() throws Exception {
        Token token = cryptTokenService.decrypt(ENCODED_TOKEN);

        assertThat(token, is(TOKEN));
    }

    @Test
    public void shouldFailedDecryptToken() throws Exception {
        expectedException.expect(CryptTokenServiceException.class);
        String encodedToken = "someNotValidToken";

        cryptTokenService.decrypt(encodedToken);
    }
}