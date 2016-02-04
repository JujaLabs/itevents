package org.itevents.service.security;

import org.codehaus.jackson.map.ObjectMapper;
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

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by ramax on 2/1/16.
 */
public class AESCryptTokenServiceTest {

    private final String INIT_VECTOR_HEX = "000102030405060708090A0B0C0D0E0F";
    private final String KEY_HEX = "000102030405060708090A0B0C0D0E0F";

    private final String JSON = "{'username':'ramax','role':'guest'}";
    private final String TOKEN = "e9FEOe4dqxixdRRp1E9bh4lqFhbXc2Vo6UsIRV2gnPYCKo0WAuXl8Vj37Zl11wZS";

    @Mock
    private ObjectMapper objectMapper = new ObjectMapper();

    @InjectMocks
    private AESCryptTokenService cryptTokenService = new AESCryptTokenService(INIT_VECTOR_HEX, KEY_HEX);

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldEncryptToken() throws Exception {
        when(objectMapper.writeValueAsString(any())).thenReturn(JSON);
        String jsonToken = cryptTokenService.encrypt(new Token());
        assertEquals(TOKEN, jsonToken);
    }


    @Test
    public void shouldDecryptToken() throws Exception {
        cryptTokenService.decrypt(TOKEN);
        verify(objectMapper).readValue(JSON, Token.class);
    }
}