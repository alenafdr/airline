package com.airline.security;

import com.airline.exceptions.SessionIsNotAuthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;

public class SessionAuthenticationFilter extends GenericFilterBean {

    private AuthenticationManager authenticationManager;
    private static final Logger LOGGER = LoggerFactory.getLogger(SessionAuthenticationFilter.class);

    @Autowired
    public SessionAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        String login = (String) httpServletRequest.getSession().getAttribute("login");
        if (login == null) {
            //for integration tests
            if (httpServletRequest.getHeader("integrationTesting") == null) {
                throw new SessionIsNotAuthorizedException("Session is not authorized");
            } else {
                login = System.getProperty("integrationTestLogin");
            }
        }

        try {
            Authentication authentication = authenticationManager.authenticate(new SessionAuthentication(new HashSet<GrantedAuthority>(),
                    login));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            filterChain.doFilter(servletRequest, servletResponse);
        } catch (AuthenticationException ex) {
            AuthenticationEntryPoint authenticationEntryPoint = new BasicAuthenticationEntryPoint();
            authenticationEntryPoint.commence(httpServletRequest, httpServletResponse, ex);
        }

    }
}
