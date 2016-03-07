package org.itevents.service.security.json_response;

import com.google.gson.JsonObject;
import org.springframework.security.core.AuthenticationException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public final class JsonAuthenticationFailureResponse {
    private final String ERROR_KEY_TITLE = "error";
    private final String DEFAULT_ERROR_VALUE = "unauthorised";
    private final String DESCRIPTION_KEY_TITLE = "description";
    private final String DEFAULT_DESCRIPTION_VALUE = "You have to be authorised to access this page";
    private final HttpServletResponse response;
    private final AuthenticationException exception;

    // Used to map exception's error message to message that is displayed to user
    private final Map<String, String> errorDescriptionMap = new HashMap<>();

    public JsonAuthenticationFailureResponse(final HttpServletResponse response, AuthenticationException exception) {
        this.response = response;
        this.exception = exception;
        errorDescriptionMap.put("Full authentication is required to access this resource",
                "You need to be authenticated to access this page");
        errorDescriptionMap.put("Bad credentials",
                "You have entered wrong username or password");
    }

    public void send() throws IOException {
        String errorDescription = DEFAULT_DESCRIPTION_VALUE;
        String exceptionMessage = exception.getMessage();
        JsonObject json = new JsonObject();
        json.addProperty(ERROR_KEY_TITLE, DEFAULT_ERROR_VALUE);

        if (errorDescriptionMap.containsKey(exceptionMessage)) {
            errorDescription = errorDescriptionMap.get(exceptionMessage);
        }

        json.addProperty(DESCRIPTION_KEY_TITLE, errorDescription);

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");

        Writer responseWriter = response.getWriter();
        responseWriter.write(json.toString());
        responseWriter.flush();
        responseWriter.close();
    }
}
