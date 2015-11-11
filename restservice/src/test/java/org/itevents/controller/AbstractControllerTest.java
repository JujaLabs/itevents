package org.itevents.controller;

import org.itevents.model.User;
import org.itevents.test_utils.BuilderUtil;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

/**
 * Created by ramax on 11/10/15.
 */
public abstract class AbstractControllerTest {

    protected MockMvc mockMvc;

    protected AbstractControllerTest initMock(Object testObject) {
        MockitoAnnotations.initMocks(testObject);
        return this;
    }

    protected void initMvc(Object controller) {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    protected void authenticationGuest() {
        User user = BuilderUtil.buildUserGuest();
        authenticationUser(user);
    }

    protected void authenticationUser(User user) {
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
