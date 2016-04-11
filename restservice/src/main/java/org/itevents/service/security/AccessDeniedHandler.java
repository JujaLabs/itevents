package org.itevents.service.security;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by ramax on 3/20/16.
 */
@Component
public class AccessDeniedHandler extends AccessDeniedHandlerImpl {

    private static final String ERROR_MESSAGE = "FORBIDDEN";
    private JsonHelper jsonHelper = new JsonHelper();

    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException reason) throws IOException {

        jsonHelper.sendJson(response, HttpServletResponse.SC_FORBIDDEN, ERROR_MESSAGE);
    }
}
