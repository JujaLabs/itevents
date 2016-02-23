package org.itevents.service.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;
import org.codehaus.jackson.map.ObjectMapper;
import org.itevents.service.CryptTokenService;
import org.itevents.service.exception.CryptTokenServiceException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import javax.inject.Inject;

import java.io.IOException;
import java.security.Key;
import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by ramax on 2/1/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class JWTCryptTokenServiceTest {

    /*
         Token generate from http://jwt.io/
     */
    private final String KEY = "someSecretKey";
    private final String ENCODED_TOKEN =
            "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6InJhbWF4Iiwicm9s" +
            "ZSI6ImFkbWluIn0.4RdnMCRvtqUY_SDNmVJ_2VyPatbLtd-HPnf8hpk22AM";
    private final Token TOKEN = new Token("ramax","admin");

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

    @Test(expected = CryptTokenServiceException.class)
    public void shouldFailedDecryptToken() throws Exception {
        String encodedToken = "someNotValidToken";

        cryptTokenService.decrypt(encodedToken);
    }
}