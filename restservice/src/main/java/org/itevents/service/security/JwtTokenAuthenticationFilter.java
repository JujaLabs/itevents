package org.itevents.service.security;


import java.io.IOException;
import java.util.List;
import javax.inject.Inject;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.filter.OncePerRequestFilter;

@Component("jwtAuthenticationFilter")
public class JwtTokenAuthenticationFilter extends OncePerRequestFilter {
    private AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource = new WebAuthenticationDetailsSource();
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

    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain
    ) throws IOException, ServletException {
        String header = request.getHeader("Authorization");
        if(header != null && header.startsWith("Bearer ")) {
            try {
                Token token = cryptTokenService.decrypt(header.substring(7));

                List<GrantedAuthority> authorityList
                        = AuthorityUtils.commaSeparatedStringToAuthorityList(token.getRole());

                UsernamePasswordAuthenticationToken authRequest =
                    new UsernamePasswordAuthenticationToken(token.getUsername(), header, authorityList);

                authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
                Authentication authResult = this.authenticationManager.authenticate(authRequest);

                SecurityContextHolder.getContext().setAuthentication(authResult);
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

    public void setAuthenticationDetailsSource(AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource) {
        Assert.notNull(authenticationDetailsSource, "AuthenticationDetailsSource required");
        this.authenticationDetailsSource = authenticationDetailsSource;
    }

}