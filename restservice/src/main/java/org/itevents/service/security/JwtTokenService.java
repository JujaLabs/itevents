package org.itevents.service.security;

import org.itevents.model.User;
import org.itevents.service.CryptTokenService;
import org.itevents.service.TokenService;
import org.itevents.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

/**
 * Created by ramax on 1/30/16.
 */
@Component
public class JwtTokenService implements TokenService {

    @Inject
    private UserService userService;

    @Inject
    private CryptTokenService cryptTokenService;

    @Inject
    private PasswordEncoder passwordEncoder;

    @Override
    public String createToken(String username, String password) {
        User user = userService.getUserByName(username);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            Token token = new Token(username, user.getRole().getName());
            return cryptTokenService.encrypt(token);
        } else {
            return null;
        }
    }
}
