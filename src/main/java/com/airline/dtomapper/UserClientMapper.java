package com.airline.dtomapper;

import com.airline.model.UserClient;
import com.airline.model.dto.UserClientDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserClientMapper {
    private ModelMapper modelMapper;

    @Autowired
    public UserClientMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public UserClientDTO convertToDTO(UserClient userClient) {
        return modelMapper.map(userClient, UserClientDTO.class);
    }

    public UserClient converToEntity(UserClientDTO userClientDTO) {
        return modelMapper.map(userClientDTO, UserClient.class);
    }
}
