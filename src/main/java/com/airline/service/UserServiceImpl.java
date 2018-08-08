package com.airline.service;

import com.airline.dao.AdminDao;
import com.airline.dao.ClientDao;
import com.airline.dto.mapper.UserAdminDTOMapper;
import com.airline.dto.mapper.UserClientDTOMapper;
import com.airline.exceptions.AlreadyExistsException;
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
    private UserClientDTOMapper userClientDTOMapper;
    private UserAdminDTOMapper userAdminDTOMapper;

    @Autowired
    public UserServiceImpl(AdminDao adminDao,
                           ClientDao clientDao,
                           UserClientDTOMapper userClientDTOMapper,
                           UserAdminDTOMapper userAdminDTOMapper) {
        this.adminDao = adminDao;
        this.clientDao = clientDao;
        this.userClientDTOMapper = userClientDTOMapper;
        this.userAdminDTOMapper = userAdminDTOMapper;
    }

    @Override
    public UserEntityDTO getUserByLogin(String login) {
        String loginLC = login.toLowerCase();
        UserEntityDTO entity;
        Optional<UserClient> userClient = clientDao.findByLogin(loginLC);
        if (userClient.isPresent()) {
            entity = userClientDTOMapper.convertToDTO(userClient.get());
        } else {
            entity = userAdminDTOMapper.convertToDTO(adminDao.findByLogin(loginLC)
                    .orElseThrow(() -> new LoginNotFoundException("Not found user with login " + loginLC)));
        }
        return entity;
    }

    @Override
    public UserAdminDTO saveAdmin(UserAdminDTO userAdminDTO) {
        String loginLC = userAdminDTO.getLogin().toLowerCase();
        userAdminDTO.setLogin(loginLC);
        if (adminDao.findByLogin(loginLC).isPresent() || clientDao.findByLogin(loginLC).isPresent()) {
            throw new AlreadyExistsException("User with login " + userAdminDTO.getLogin() + " already exists");
        }
        Long id = adminDao.save(userAdminDTOMapper.converToEntity(userAdminDTO));
        userAdminDTO.setId(id);
        return userAdminDTO;
    }

    //TODO must be return without id
    @Override
    public UserAdminDTO updateAdmin(UserAdminDTO userAdminDTO) {
        String login = userAdminDTO.getLogin().toLowerCase();
        UserAdmin userAdmin = adminDao.findByLogin(login)
                .orElseThrow(() -> new UserNotFoundException("Not found user with login " + login));
        if (userAdmin.getPassword().equals(userAdminDTO.getOldPassword())) {
            userAdminDTO.setPassword(userAdminDTO.getNewPassword());

            adminDao.update(userAdminDTOMapper.converToEntity(userAdminDTO));
            return userAdminDTO;
        } else {
            throw new ChangePasswordException("Wrong old password");
        }
    }

    @Override
    public UserClientDTO saveClient(UserClientDTO userClientDTO) {
        String loginLC = userClientDTO.getLogin().toLowerCase();
        userClientDTO.setLogin(userClientDTO.getLogin().toLowerCase());
        if (adminDao.findByLogin(loginLC).isPresent() || clientDao.findByLogin(loginLC).isPresent()) {
            throw new AlreadyExistsException("User with login " + userClientDTO.getLogin() + " already exists");
        }
        Long id = clientDao.save(userClientDTOMapper.converToEntity(userClientDTO));
        userClientDTO.setId(id);
        return userClientDTO;
    }

    //TODO must be return without id
    @Override
    public UserClientDTO updateClient(UserClientDTO userClientDTO) {
        String login = userClientDTO.getLogin().toLowerCase();
        UserClient userClient = clientDao.findByLogin(login)
                .orElseThrow(() -> new UserNotFoundException("Not found user with login " + login));
        if (userClient.getPassword().equals(userClientDTO.getOldPassword())) {
            userClientDTO.setPassword(userClientDTO.getNewPassword());

            clientDao.update(userClientDTOMapper.converToEntity(userClientDTO));
            return userClientDTO;
        } else {
            throw new ChangePasswordException("Wrong old password");
        }
    }

    @Override
    public List<UserClientDTO> getListClients() {
        return clientDao.findList()
                .stream()
                .map(client -> userClientDTOMapper.convertToDTO(client))
                .collect(Collectors.toList());
    }
}
