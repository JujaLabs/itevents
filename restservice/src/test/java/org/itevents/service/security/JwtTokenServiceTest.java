package org.itevents.service.security;

import org.itevents.dao.model.User;
import org.itevents.service.CryptTokenService;
import org.itevents.service.UserService;
import org.itevents.test_utils.BuilderUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * Created by ramax on 2/1/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class JwtTokenServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private CryptTokenService cryptTokenService;

    @InjectMocks
    private JwtTokenService tokenService;

    @Test
    public void shouldCreateToken() throws Exception {

        User user = BuilderUtil.buildUserGuest();
        String password = "SomePassword";
        String generateToken = "someGenerateToken";
        Token jsonToken = new Token(user.getLogin(), user.getRole().getName());
        when(userService.getUserByName(user.getLogin())).thenReturn(user);
        when(cryptTokenService.encrypt(jsonToken)).thenReturn(generateToken);

        String token = tokenService.createToken(user.getLogin(), password);

        assertEquals(token, generateToken);
        verify(userService).checkPassword(user, password);
    }
}
