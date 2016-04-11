package org.itevents.service.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by ramax on 3/20/16.
 */
@Component
public final class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private static final String ERROR_MESSAGE = "UNAUTHORIZED";
    private JsonHelper jsonHelper = new JsonHelper();

    @Override
    public void commence(final HttpServletRequest request, final HttpServletResponse response,
                         final AuthenticationException authException) throws IOException {
        jsonHelper.sendJson(response, HttpServletResponse.SC_UNAUTHORIZED, ERROR_MESSAGE);
    }

}