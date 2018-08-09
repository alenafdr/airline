package com.airline.service.impl;

import com.airline.dao.PlaneDao;
import com.airline.dto.mapper.PlaneDTOMapper;
import com.airline.model.dto.PlaneDTO;
import com.airline.service.PlaneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlaneServiceImpl implements PlaneService {

    private PlaneDao planeDao;
    private PlaneDTOMapper planeDTOMapper;

    @Autowired
    public PlaneServiceImpl(PlaneDao planeDao, PlaneDTOMapper planeDTOMapper) {
        this.planeDao = planeDao;
        this.planeDTOMapper = planeDTOMapper;
    }

    @Override
    public List<PlaneDTO> getPlanes() {
        return planeDao.getPlanes()
                .stream()
                .map(plane -> planeDTOMapper.convertToDTO(plane))
                .collect(Collectors.toList());
    }
}
