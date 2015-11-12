package org.itevents.controller;

import org.itevents.model.User;
import org.itevents.test_utils.BuilderUtil;
import org.junit.Before;
import org.mockito.Mockito;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.inject.Inject;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * Created by ramax on 11/12/15.
 */
public class AbstractMockControllerTest<T> {

    protected MockMvc mockMvc;
    private T controller;

    public void init(Class<T> tClass) {
        try {
            controller = tClass.newInstance();
            mockingController();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        initMvcMock();
    }

    private void mockingController() throws IllegalAccessException {

        Field[] camps = controller.getClass().getDeclaredFields();

        for (Field f : camps) {
            Annotation an = f.getAnnotation(Inject.class);
            if (an != null) {
                Object mock = createMock( f.getType() );
                f.setAccessible(true);
                f.set(controller, mock);
                f.setAccessible(false);
            }
        }
    }

    private Object createMock(Class<?> clazz) {
        return Mockito.mock(clazz);
    }

    private void initMvcMock() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

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
