package org.itevents.service.security;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.Assert.*;

/**
 * Created by ramax on 2/1/16.
 */
@RunWith(JUnit4.class)
public class AESCryptTokenServiceTest {

    private final String INIT_VECTOR_HEX = "000102030405060708090A0B0C0D0E0F";
    private final String KEY_HEX = "000102030405060708090A0B0C0D0E0F101112131415161718191A1B1C1D1E1F";

    private AESCryptTokenService cryptTokenService;

    @Before
    public void init() {
        cryptTokenService = new AESCryptTokenService();
        ReflectionTestUtils.setField(cryptTokenService,"initVectorHex", INIT_VECTOR_HEX);
        ReflectionTestUtils.setField(cryptTokenService,"keyHex", KEY_HEX);
    }

    @Test
    public void shouldEncryptDecrypt() throws Exception {
        Token token = new Token("someUsername","someRole");

        String encryptToken = cryptTokenService.encrypt(token);
        Token decryptToken = cryptTokenService.decrypt(encryptToken);

        assertEquals(token, decryptToken);
    }
}