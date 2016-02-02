package org.itevents.service.security;

import org.itevents.model.User;
import org.itevents.service.TokenService;
import org.itevents.service.UserService;
import org.itevents.service.security.CryptTokenService;
import org.itevents.service.security.Token;
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
    public String createToken(String username, String password) {
        User user = userService.getUserByName(username);
        if (user != null && userService.matchPasswordByLogin(user, password)) {
            Token token = new Token(username, user.getRole().getName());
            return cryptTokenService.encrypt(token);
        } else {
            return null;
        }
    }
}
