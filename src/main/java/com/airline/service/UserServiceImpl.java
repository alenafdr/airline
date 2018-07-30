package com.airline.service;

import com.airline.dao.AdminDao;
import com.airline.dao.ClientDao;
import com.airline.dtomapper.UserAdminMapper;
import com.airline.dtomapper.UserClientMapper;
import com.airline.exceptions.LoginNotFoundException;
import com.airline.model.UserAdmin;
import com.airline.model.UserClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private AdminDao adminDao;
    private ClientDao clientDao;
    private UserClientMapper userClientMapper;
    private UserAdminMapper userAdminMapper;

    @Autowired
    public UserServiceImpl(AdminDao adminDao,
                           ClientDao clientDao,
                           UserClientMapper userClientMapper,
                           UserAdminMapper userAdminMapper) {
        this.adminDao = adminDao;
        this.clientDao = clientDao;
        this.userClientMapper = userClientMapper;
        this.userAdminMapper = userAdminMapper;
    }

    @Override
    public Object getUserByLogin(String login) {
        Object entity;
        Optional<UserClient> userClient = clientDao.findByLogin(login);
        if (userClient.isPresent()) {
            entity = userClientMapper.convertToDTO(userClient.get());
        } else {
            Optional<UserAdmin> userAdmin = adminDao.findByLogin(login);
            entity = userAdminMapper.convertToDTO(adminDao.findByLogin(login)
                    .orElseThrow(() -> new LoginNotFoundException("Not found user with login " + login)));
        }
        return entity;
    }
}
