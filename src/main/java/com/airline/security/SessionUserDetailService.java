package com.airline.security;

import com.airline.dao.AdminDao;
import com.airline.dao.ClientDao;
import com.airline.model.UserAdmin;
import com.airline.model.UserClient;
import com.airline.model.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class SessionUserDetailService implements UserDetailsService {

    private ClientDao clientDao;
    private AdminDao adminDao;

    @Autowired
    public SessionUserDetailService(ClientDao clientDao, AdminDao adminDao) {
        this.clientDao = clientDao;
        this.adminDao = adminDao;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        UserEntity user;
        Optional<UserClient> userClient = clientDao.findByLogin(login);
        Optional<UserAdmin> userAdmin = adminDao.findByLogin(login);
        Set<GrantedAuthority> authorities = new HashSet<>();
        if (userClient.isPresent()) {
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
            user = userClient.get();
        } else if (userAdmin.isPresent()) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            user = userAdmin.get();
        } else {
            throw new UsernameNotFoundException("Unknown " + login);
        }


        return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(), authorities);
    }
}
