package org.itevents.service.security;

import org.itevents.model.User;
import org.itevents.service.UserService;
import org.itevents.test_utils.BuilderUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
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
    public void shouldCreateToken() throws Exception {

        User user = BuilderUtil.buildUserGuest();

        String username = "SomeLogin";
        String password = "SomePassword";
        when(userService.getUserByName(username)).thenReturn(user);
        when(userService.matchPasswordByLogin(user, password)).thenReturn(true);

        String generateToken = "someGenerateToken";
        when(cryptTokenService.encrypt(any())).thenReturn(generateToken);

        String token = tokenService.createToken(username, password);
        assertEquals(token, generateToken);
    }

    @Test
    public void shouldFailCreateToken() throws Exception {

        String username = "SomeLogin";
        String password = "SomePassword";
        when(userService.getUserByName(username)).thenReturn(null);

        String token = tokenService.createToken(username, password);
        assertNull(token);
    }
}
