package org.itevents.service.security.json_response;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.core.AuthenticationException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class JsonAuthenticationFailureResponseTest {

    @Mock
    public HttpServletResponse response;

    @Mock
    public PrintWriter responseWriter;

    @Mock
    public AuthenticationException exception;

    @Test
    public void shouldFillResponseWithExceptionInformation() throws IOException {
        when(exception.getMessage()).thenReturn("Full authentication is required to access this resource");
        when(response.getWriter()).thenReturn(responseWriter);

        new JsonAuthenticationFailureResponse(response, exception).send();

        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        verify(response).setContentType("application/json;charset=UTF-8");
        verify(response).getWriter();
        verify(responseWriter).write("{\"error\":\"unauthorised\",\"description\":\"You need to be authenticated to access this page\"}");
        verify(responseWriter).flush();
        verify(responseWriter).close();
    }

    @Test
    public void shouldFillResponseWithDefaultExcepptionInformation() throws IOException {
        when(exception.getMessage()).thenReturn("Some message");
        when(response.getWriter()).thenReturn(responseWriter);

        new JsonAuthenticationFailureResponse(response, exception).send();

        verify(responseWriter).write("{\"error\":\"unauthorised\",\"description\":\"You have to be authorised to access this page\"}");

    }
}
