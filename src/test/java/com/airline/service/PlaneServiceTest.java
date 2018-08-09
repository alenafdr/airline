package com.airline.service;

import com.airline.dao.PlaneDao;
import com.airline.dto.mapper.PlaneDTOMapper;
import com.airline.model.Plane;
import com.airline.model.dto.PlaneDTO;
import com.airline.service.impl.PlaneServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ModelMapper.class)
public class PlaneServiceTest {

    @Autowired
    private ModelMapper modelMapper;

    private PlaneServiceImpl planeService;

    @MockBean
    private PlaneDao planeDao;

    @Before
    public void init() {
        PlaneDTOMapper planeDTOMapper = new PlaneDTOMapper(modelMapper);
        planeService = new PlaneServiceImpl(planeDao, planeDTOMapper);
    }

    @Test
    public void getPlanesTest() {
        when(planeDao.getPlanes()).thenReturn(buildListPlane());

        List<PlaneDTO> planeDTOList = planeService.getPlanes();
        PlaneDTO planeDTO = planeDTOList.get(0);
        assertFalse(planeDTOList.isEmpty());
        assertNotNull(planeDTO.getName());
        assertNotNull(planeDTO.getBusinessRow());
        assertNotNull(planeDTO.getEconomyRow());
        assertNotNull(planeDTO.getPlacesInBusinessRow());
        assertNotNull(planeDTO.getPlacesInEconomyRow());
    }

    private List<Plane> buildListPlane() {
        List<Plane> planes = new ArrayList<>();
        planes.add(new Plane(1L, "name", 2, 4, 2, 2));
        planes.add(new Plane(2L, "name2", 3, 7, 2, 3));
        planes.add(new Plane(3L, "name3", 4, 10, 4, 5));
        return planes;
    }

}
