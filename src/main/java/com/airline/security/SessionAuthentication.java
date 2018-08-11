package com.airline.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class SessionAuthentication extends AbstractAuthenticationToken {

    private String login;

    public SessionAuthentication(Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
    }

    public SessionAuthentication(Collection<? extends GrantedAuthority> authorities, String login) {
        super(authorities);
        this.login = login;
    }


    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return login;
    }

    @Override
    public String toString() {
        return "SessionAuthentication{" +
                ", login='" + login + '\'' +
                '}';
    }
}
