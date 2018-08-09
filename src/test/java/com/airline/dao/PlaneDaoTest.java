package com.airline.dao;

import com.airline.model.Plane;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@Import({PlaneDao.class})
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PlaneDaoTest {

    @Autowired
    private PlaneDao planeDao;

    @Test
    public void getPlanesTest() {
        List<Plane> planes = planeDao.getPlanes();
        assertFalse(planes.isEmpty());
        Plane plane = planes.get(0);
        assertNotNull(plane.getId());
        assertNotNull(plane.getName());
        assertNotNull(plane.getBusinessRow());
        assertNotNull(plane.getEconomyRow());
        assertNotNull(plane.getPlacesInBusinessRow());
        assertNotNull(plane.getPlacesInEconomyRow());
    }
}
