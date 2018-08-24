package com.airline.service.impl;

import com.airline.dao.AdminDao;
import com.airline.dao.ClientDao;
import com.airline.dto.mapper.UserAdminDTOMapper;
import com.airline.dto.mapper.UserClientDTOMapper;
import com.airline.exceptions.AlreadyExistsException;
import com.airline.exceptions.ChangePasswordException;
import com.airline.exceptions.UserNotFoundException;
import com.airline.model.UserAdmin;
import com.airline.model.UserClient;
import com.airline.model.dto.UserAdminDTO;
import com.airline.model.dto.UserClientDTO;
import com.airline.model.dto.UserEntityDTO;
import com.airline.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Класс используется для связи между слоями контроллера и БД. Для маппинга объектов {@link UserAdminDTO} и
 * {@link UserClientDTO} в {@link UserAdmin} и {@link UserClient} соответственно.
 */

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

    /**
     * Метод служит для подготовки и отправки в БД запроса на поиск пользователя по логину.
     * Переводит login в нижний регистр (все логины хранятся в БД в нижнем регистре). Поочередно опрашивает
     * {@link ClientDao} и {@link AdminDao} на наличие пользователя с таким логином, возвращает найденного пользователя
     *
     * @param login для поиска в БД
     * @return {@link UserEntityDTO}
     * @throws {@link UserNotFoundException}
     */

    @Override
    public UserEntityDTO getUserByLogin(String login) {
        String loginLC = login.toLowerCase();
        UserEntityDTO entity;
        Optional<UserClient> userClient = clientDao.findByLogin(loginLC);
        if (userClient.isPresent()) {
            entity = userClientDTOMapper.convertToDTO(userClient.get());
        } else {
            entity = userAdminDTOMapper.convertToDTO(adminDao.findByLogin(loginLC)
                    .orElseThrow(() -> new UserNotFoundException("Not found user with login " + login)));
        }
        return entity;
    }

    /**
     * Метод служит для подготовки и отправки запроса в БД на сохранение пользователя администратора.
     * Переводит логин в нижний регистр, проверяет на наличие пользователя с таким именем, сохраняет в БД,
     * устанавливает id и возвращает {@link UserAdminDTO}
     *
     * @param userAdminDTO
     * @return {@link UserAdminDTO}
     * @throws {@link AlreadyExistsException}
     */

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

    /**
     * Метод служит для подготовки и отправки запроса в БД на обновление пользователя администратора.
     * Проверяет, существует ли такой пользователь, верно ли указан старый пароль, обновляет и возвращает {@link UserAdminDTO}
     *
     * @param userAdminDTO
     * @return {@link UserAdminDTO}
     * @throws {@link UserNotFoundException}
     * @throws {@link ChangePasswordException}
     */

    //TODO must be return without id
    @Override
    public UserAdminDTO updateAdmin(UserAdminDTO userAdminDTO) {
        String login = userAdminDTO.getLogin().toLowerCase();
        UserAdmin userAdmin = adminDao.findByLogin(login)
                .orElseThrow(() -> new UserNotFoundException("Not found user with login " + userAdminDTO.getLogin()));
        if (userAdmin.getPassword().equals(userAdminDTO.getOldPassword())) {
            userAdminDTO.setPassword(userAdminDTO.getNewPassword());
            userAdminDTO.setId(userAdmin.getId());
            adminDao.update(userAdminDTOMapper.converToEntity(userAdminDTO));
            return userAdminDTO;
        } else {
            throw new ChangePasswordException("Wrong old password");
        }
    }

    /**
     * Метод служит для подготовки и отправки запроса в БД на сохранение пользователя клиента.
     * Переводит логин в нижний регистр, убирает из параметра phone все знаки "-", проверяет на наличие
     * пользователя с таким именем, сохраняет в БД,
     * устанавливает id и возвращает {@link UserClientDTO}
     *
     * @param userClientDTO
     * @return {@link UserClientDTO}
     * @throws {@link AlreadyExistsException}
     */

    @Override
    public UserClientDTO saveClient(UserClientDTO userClientDTO) {
        String loginLC = userClientDTO.getLogin().toLowerCase();
        userClientDTO.setPhone(userClientDTO.getPhone().replace("-", ""));
        userClientDTO.setLogin(loginLC);
        if (adminDao.findByLogin(loginLC).isPresent() || clientDao.findByLogin(loginLC).isPresent()) {
            throw new AlreadyExistsException("User with login " + userClientDTO.getLogin() + " already exists");
        }
        Long id = clientDao.save(userClientDTOMapper.converToEntity(userClientDTO));
        userClientDTO.setId(id);
        return userClientDTO;
    }

    /**
     * Метод служит для подготовки и отправки запроса в БД на обновление пользователя клиента.
     * Проверяет, существует ли такой пользователь, убирает из параметра phone все знаки "-",
     * верно ли указан старый пароль и возвращает {@link UserClientDTO}
     *
     * @param userClientDTO
     * @return {@link UserClientDTO}
     * @throws {@link UserNotFoundException}
     * @throws {@link ChangePasswordException}
     */

    //TODO must be return without id
    @Override
    public UserClientDTO updateClient(UserClientDTO userClientDTO) {
        String login = userClientDTO.getLogin().toLowerCase();
        UserClient userClient = clientDao.findByLogin(login)
                .orElseThrow(() -> new UserNotFoundException("Not found user with login " + userClientDTO.getLogin()));
        if (userClient.getPassword().equals(userClientDTO.getOldPassword())) {
            userClientDTO.setPassword(userClientDTO.getNewPassword());
            userClientDTO.setId(userClient.getId());
            userClientDTO.setPhone(userClientDTO.getPhone().replace("-", ""));
            clientDao.update(userClientDTOMapper.converToEntity(userClientDTO));
            return userClientDTO;
        } else {
            throw new ChangePasswordException("Wrong old password");
        }
    }

    /**
     * Метод служит для получения из БД списка объектов {@link UserClient} и конвертации их в {@link UserClientDTO}
     *
     * @return
     */

    @Override
    public List<UserClientDTO> getListClients() {
        return clientDao.findList()
                .stream()
                .map(client -> userClientDTOMapper.convertToDTO(client))
                .collect(Collectors.toList());
    }
}
