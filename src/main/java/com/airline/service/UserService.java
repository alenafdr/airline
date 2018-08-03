package com.airline.service;

import com.airline.model.dto.UserAdminDTO;
import com.airline.model.dto.UserClientDTO;
import com.airline.model.dto.UserEntityDTO;

import java.util.List;

public interface UserService {
    public UserEntityDTO getUserByLogin(String login);
    public UserAdminDTO saveAdmin(UserAdminDTO userAdminDTO);
    public UserAdminDTO updateAdmin(UserAdminDTO userAdminDTO);
    public UserClientDTO saveClient(UserClientDTO userClientDTO);
    public UserClientDTO updateClient(UserClientDTO userClientDTO);
    public List<UserClientDTO> getListClients();
}
