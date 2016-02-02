package org.itevents.service.security;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import javax.inject.Inject;

import static org.junit.Assert.*;

/**
 * Created by ramax on 2/1/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
        "classpath:spring-config-mocks.xml",
        "classpath*:spring-security.xml"
})
public class CryptTokenServiceTest {

    @Inject
    private CryptTokenService cryptTokenService;

    @Test
    public void shouldEncryptToken() {
        Token token = new Token("username", "role");

        String encryptToken = cryptTokenService.encrypt(token);
        assertNotNull(encryptToken);

        Token decryptToken = cryptTokenService.decrypt(encryptToken);
        assertEquals(decryptToken, token);
    }
}