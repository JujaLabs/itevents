package org.itevents.service.security;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by ramax on 2/1/16.
 */
@RunWith(JUnit4.class)
public class AESCryptTokenServiceTest {

    private final String INIT_VECTOR_HEX = "000102030405060708090A0B0C0D0E0F";
    private final String KEY_HEX = "000102030405060708090A0B0C0D0E0F101112131415161718191A1B1C1D1E1F";
    private final String JSON_TOKEN = "{'username': 'someUsername', 'role': 'someUserRole'}";
    private final String TOKEN =
            "xgA1oJhZlGIhiNZpTGIfVqYl1yD/T/yt4llRK1TANSijxxNZsiAcWp2qqjuPg7NVohlGDWFGKwhubzKm5k2xuw==";

    private AESCryptTokenService cryptTokenService;
    private ObjectMapper objectMapper;

    @Before
    public void init() {
        cryptTokenService = new AESCryptTokenService();
        ReflectionTestUtils.setField(cryptTokenService,"initVectorHex", INIT_VECTOR_HEX);
        ReflectionTestUtils.setField(cryptTokenService,"keyHex", KEY_HEX);

        objectMapper = Mockito.mock(ObjectMapper.class);
        ReflectionTestUtils.setField(cryptTokenService,"objectMapper", objectMapper);
    }

    @Test
    public void shouldEncryptToken() throws Exception {
        when(objectMapper.writeValueAsString(any())).thenReturn(JSON_TOKEN);

        String token = cryptTokenService.encrypt(new Token("someUsername","someUserRole"));
        assertEquals(TOKEN, token);
    }

    @Test
    public void shouldDecryptToken() throws Exception {
        cryptTokenService.decrypt(TOKEN);
        verify(objectMapper).readValue(JSON_TOKEN, Token.class);
    }

}