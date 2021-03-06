package com.airline.dto.mapper;

import com.airline.model.UserAdmin;
import com.airline.model.dto.UserAdminDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserAdminDTOMapper {

    private ModelMapper modelMapper;

    @Autowired
    public UserAdminDTOMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public UserAdminDTO convertToDTO(UserAdmin userAdmin) {
        return modelMapper.map(userAdmin, UserAdminDTO.class);
    }

    public UserAdmin converToEntity(UserAdminDTO userAdminDTO) {
        return modelMapper.map(userAdminDTO, UserAdmin.class);
    }
}
