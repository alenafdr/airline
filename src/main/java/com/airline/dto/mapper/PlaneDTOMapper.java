package com.airline.dto.mapper;

import com.airline.model.Plane;
import com.airline.model.dto.PlaneDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PlaneDTOMapper {

    private ModelMapper modelMapper;

    @Autowired
    public PlaneDTOMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public PlaneDTO convertToDTO(Plane plane) {
        PlaneDTO planeDTO = modelMapper.map(plane, PlaneDTO.class);
        return planeDTO;
    }

    public Plane convertToEntity(PlaneDTO planeDTO) {
        Plane plane = modelMapper.map(planeDTO, Plane.class);
        return plane;
    }
}
