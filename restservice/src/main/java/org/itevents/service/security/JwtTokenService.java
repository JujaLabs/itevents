package org.itevents.service.security;

import org.itevents.controller.wrapper.TokenWrapper;
import org.itevents.dao.model.User;
import org.itevents.service.CryptTokenService;
import org.itevents.service.TokenService;
import org.itevents.service.UserService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

/**
 * Created by ramax on 1/30/16.
 */
@Service
public class JwtTokenService implements TokenService {

    @Inject
    private UserService userService;

    @Inject
    private CryptTokenService cryptTokenService;

    @Override
    public TokenWrapper createTokenWrapper(String username, String password) {
        User user = userService.getUserByName(username);
        userService.checkPassword(user, password);

        Token token = new Token(username, user.getRole().getName());
        return new TokenWrapper(cryptTokenService.encrypt(token));
    }
}
