package com.airline.service;

import com.airline.dao.AdminDao;
import com.airline.dao.ClientDao;
import com.airline.dto.mapper.UserAdminMapper;
import com.airline.dto.mapper.UserClientMapper;
import com.airline.exceptions.ChangePasswordException;
import com.airline.exceptions.LoginNotFoundException;
import com.airline.exceptions.UserNotFoundException;
import com.airline.model.UserAdmin;
import com.airline.model.UserClient;
import com.airline.model.dto.UserAdminDTO;
import com.airline.model.dto.UserClientDTO;
import com.airline.model.dto.UserEntityDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Override
    public UserAdminDTO saveAdmin(UserAdminDTO userAdminDTO) {
        userAdminDTO.setLogin(userAdminDTO.getLogin().toLowerCase());
        adminDao.save(userAdminMapper.converToEntity(userAdminDTO));
        return userAdminDTO;
    }

    @Override
    public UserAdminDTO updateAdmin(UserAdminDTO userAdminDTO) {
        String login = userAdminDTO.getLogin().toLowerCase();
        UserAdmin userAdmin = adminDao.findByLogin(login)
                .orElseThrow(() -> new UserNotFoundException("Not found user with login " + login));
        if (userAdmin.getPassword().equals(userAdminDTO.getOldPassword())) {
            userAdminDTO.setPassword(userAdminDTO.getNewPassword());
            adminDao.update(userAdminMapper.converToEntity(userAdminDTO));
            return userAdminDTO;
        } else {
            throw new ChangePasswordException("Wrong old password");
        }
    }

    @Override
    public UserClientDTO saveClient(UserClientDTO userClientDTO) {
        userClientDTO.setLogin(userClientDTO.getLogin().toLowerCase());
        clientDao.save(userClientMapper.converToEntity(userClientDTO));
        return userClientDTO;
    }

    @Override
    public UserClientDTO updateClient(UserClientDTO userClientDTO) {
        String login = userClientDTO.getLogin().toLowerCase();
        UserClient userClient = clientDao.findByLogin(login)
                .orElseThrow(() -> new UserNotFoundException("Not found user with login " + login));
        if (userClient.getPassword().equals(userClientDTO.getOldPassword())) {
            userClientDTO.setPassword(userClientDTO.getNewPassword());
            clientDao.update(userClientMapper.converToEntity(userClientDTO));
            return userClientDTO;
        } else {
            throw new ChangePasswordException("Wrong old password");
        }
    }

    @Override
    public List<UserClientDTO> getListClients() {
        return clientDao.getList()
                .stream()
                .map(client -> userClientMapper.convertToDTO(client))
                .collect(Collectors.toList());
    }
}
