package org.itevents.controller;

import org.itevents.model.User;
import org.itevents.service.UserService;
import org.itevents.test_utils.BuilderUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.inject.Inject;
import java.util.Arrays;

/**
 * Created by ramax on 11/11/15.
 */
public abstract class AbstractControllerSecurityTest extends AbstractControllerTest {
    @Inject
    UserService userService;

    protected void authenticationGuest() {
        User user = BuilderUtil.buildUserGuest();
        authenticationUser(user, "guestPassword");
    }

    protected void authenticationUser(final User user, String password) {
        Authentication auth = new UsernamePasswordAuthenticationToken(
                user.getLogin(),
                password);
                Arrays.asList(new GrantedAuthority() {
                    @Override
                    public String getAuthority() {
                        return user.getRole().getName();
                    }
                });
        SecurityContextHolder.getContext().setAuthentication(auth);
    }
}
