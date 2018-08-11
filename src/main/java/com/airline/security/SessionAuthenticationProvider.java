package com.airline.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public class SessionAuthenticationProvider implements AuthenticationProvider {

    private static final Logger logger = LoggerFactory.getLogger(SessionAuthenticationProvider.class);

    private final UserDetailsService userDetailsService;

    @Autowired
    public SessionAuthenticationProvider(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        SessionAuthentication sessionAuthentication = (SessionAuthentication) authentication;
        String login = (String) sessionAuthentication.getPrincipal();
        UserDetails userDetails = userDetailsService.loadUserByUsername(login);

        SessionAuthentication newSessionAuthentication = new SessionAuthentication(userDetails.getAuthorities());
        newSessionAuthentication.setAuthenticated(true);
        newSessionAuthentication.setDetails(userDetails);

        return newSessionAuthentication;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass == SessionAuthentication.class;
    }
}
