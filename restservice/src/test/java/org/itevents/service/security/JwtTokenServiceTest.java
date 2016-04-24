package org.itevents.service.security;

import org.itevents.service.wrapper.TokenWrapper;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
    public void shouldCreateTokenWrapper() throws Exception {

        User user = BuilderUtil.buildUserGuest();
        String password = "password";
        String token = "token";
        TokenWrapper expectedTokenWrapper = new TokenWrapper(token);
        Token jsonToken = new Token(user.getLogin(), user.getRole().getName());

        when(userService.getUserByName(user.getLogin())).thenReturn(user);
        when(cryptTokenService.encrypt(jsonToken)).thenReturn(token);

        TokenWrapper returnedTokenWrapper = tokenService.createTokenWrapper(user.getLogin(), password);

        assertEquals(returnedTokenWrapper, expectedTokenWrapper);
        verify(userService).checkPassword(user, password);
    }
}
