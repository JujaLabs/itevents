package org.itevents.service.security;

import org.itevents.service.CryptTokenService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.inject.Inject;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Component("authenticationFilter")
public class JwtTokenAuthenticationFilter extends OncePerRequestFilter {

    private static final String AUTH_MARK = "Bearer ";

    @Inject
    private AuthenticationManager authenticationManager;
    @Inject
    private CryptTokenService cryptTokenService;

    public JwtTokenAuthenticationFilter(AuthenticationManager authenticationManager) {
        Assert.notNull(authenticationManager, "authenticationManager cannot be null");
        this.authenticationManager = authenticationManager;
    }

    public JwtTokenAuthenticationFilter() {
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain
    ) throws IOException, ServletException {
        String header = request.getHeader("Authorization");
        if(header != null && header.startsWith(AUTH_MARK)) {
            try {
                String encryptToken = header.substring(AUTH_MARK.length());
                Authentication auth = createAuthentication(encryptToken);
                authorizeUser(auth);
            } catch (AuthenticationException var10) {
                SecurityContextHolder.clearContext();
                chain.doFilter(request, response);

                return;
            }
            chain.doFilter(request, response);
        } else {
            chain.doFilter(request, response);
        }
    }

    private void authorizeUser(Authentication auth) {
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    private Authentication createAuthentication(String encryptToken) {
        Token token = cryptTokenService.decrypt(encryptToken);

        List<GrantedAuthority> authorityList
                = AuthorityUtils.commaSeparatedStringToAuthorityList(token.getRole());

        UsernamePasswordAuthenticationToken authRequest =
                new UsernamePasswordAuthenticationToken(token.getUsername(), encryptToken, authorityList);

        return this.authenticationManager.authenticate(authRequest);
    }

}