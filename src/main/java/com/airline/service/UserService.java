package com.airline.service;

import com.airline.model.dto.UserEntityDTO;

public interface UserService {
    public UserEntityDTO getUserByLogin(String login);
}
