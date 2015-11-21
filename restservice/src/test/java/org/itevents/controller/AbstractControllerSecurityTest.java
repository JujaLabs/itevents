package org.itevents.controller;

import org.itevents.model.User;
import org.itevents.test_utils.BuilderUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Arrays;

/**
 * Created by ramax on 11/11/15.
 */
public abstract class AbstractControllerSecurityTest extends AbstractControllerTest {

    protected void authenticationGuest() {
        User user = BuilderUtil.buildUserGuest();
        authenticationUser(user);
    }

    protected void authenticationUser(final User user) {
        Authentication auth = new UsernamePasswordAuthenticationToken(
                user.getLogin(),
                user.getPassword(),
                Arrays.asList(new GrantedAuthority() {
                    @Override
                    public String getAuthority() {
                        return user.getRole().getName();
                    }
                })
        );
        SecurityContextHolder.getContext().setAuthentication(auth);
    }
}
