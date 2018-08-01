package com.airline.service;

import com.airline.dao.AdminDao;
import com.airline.dao.ClientDao;
import com.airline.dto.mapper.UserAdminMapper;
import com.airline.dto.mapper.UserClientMapper;
import com.airline.exceptions.LoginNotFoundException;
import com.airline.model.UserClient;
import com.airline.model.dto.UserEntityDTO;
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
    public UserEntityDTO getUserByLogin(String login) {
        String loginLC = login.toLowerCase();
        UserEntityDTO entity;
        Optional<UserClient> userClient = clientDao.findByLogin(loginLC);
        if (userClient.isPresent()) {
            entity = userClientMapper.convertToDTO(userClient.get());
        } else {
            entity = userAdminMapper.convertToDTO(adminDao.findByLogin(loginLC)
                    .orElseThrow(() -> new LoginNotFoundException("Not found user with login " + loginLC)));
        }
        return entity;
    }
}
