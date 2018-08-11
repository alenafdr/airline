package com.airline.service;

import com.airline.model.dto.UserAdminDTO;
import com.airline.model.dto.UserClientDTO;
import com.airline.model.dto.UserEntityDTO;

import java.util.List;

public interface UserService {
    UserEntityDTO getUserByLogin(String login);

    UserAdminDTO saveAdmin(UserAdminDTO userAdminDTO);

    UserAdminDTO updateAdmin(UserAdminDTO userAdminDTO);

    UserClientDTO saveClient(UserClientDTO userClientDTO);

    UserClientDTO updateClient(UserClientDTO userClientDTO);

    List<UserClientDTO> getListClients();
}
