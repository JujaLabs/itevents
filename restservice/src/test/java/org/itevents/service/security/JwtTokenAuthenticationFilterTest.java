package org.itevents.service.security;

import org.itevents.service.CryptTokenService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * Created by ramax on 3/3/16.
 */
@ContextConfiguration({
        "classpath*:spring-security.xml",
        "classpath:applicationContextTestAddon.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class JwtTokenAuthenticationFilterTest {

    @Inject
    @Spy
    private AuthenticationManager authenticationManager;

    @Mock
    private CryptTokenService cryptTokenService;

    @Inject
    @InjectMocks
    private JwtTokenAuthenticationFilter jwtTokenAuthenticationFilter;


    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenAuthenticationManagerIsNull() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("authenticationManager cannot be null");

        new JwtTokenAuthenticationFilter(null);
    }

    @Test
    public void shouldAuthoriseUser() throws Exception {
        String encodedToken = "someEncodedToken";
        Token token = new Token("ramax@mail.ua", "admin");

        HttpServletRequest request = mock(HttpServletRequest.class);
        FilterChain filterChain = mock(FilterChain.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getHeader("Authorization")).thenReturn("Bearer " + encodedToken);
        when(cryptTokenService.decrypt(encodedToken)).thenReturn(token);

        jwtTokenAuthenticationFilter.doFilterInternal(request, response, filterChain);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        assertTrue(auth.isAuthenticated());
        verify(filterChain).doFilter(request, response);
    }

    @Test
    public void shouldCallChainDoFilterWhenThrowAuthenticationException() throws Exception {
        String encoded_token = "someEncodedToken";

        HttpServletRequest request = mock(HttpServletRequest.class);
        FilterChain filterChain = mock(FilterChain.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getHeader("Authorization")).thenReturn("Bearer " + encoded_token);
        when(cryptTokenService.decrypt(encoded_token)).thenReturn(new Token("ramax@ex.ua","admin"));
        doThrow(new MyAuthenticationException()).when(authenticationManager).authenticate(any());

        jwtTokenAuthenticationFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
    }

    @Test
    public void shouldCallChainDoFilterWhenHeaderIsNull() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        FilterChain filterChain = mock(FilterChain.class);
        when(request.getHeader("Authorization")).thenReturn(null);

        jwtTokenAuthenticationFilter.doFilterInternal(request, null, filterChain);

        verify(filterChain).doFilter(request, null);
    }

    @Test
    public void shouldCallChainDoFilterWhenHeaderIsNotValid() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        FilterChain filterChain = mock(FilterChain.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getHeader("Authorization")).thenReturn("NotValidHeader");

        jwtTokenAuthenticationFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
    }

    private class MyAuthenticationException extends AuthenticationException {
        public MyAuthenticationException() {
            super("message");
        }
    }

}
