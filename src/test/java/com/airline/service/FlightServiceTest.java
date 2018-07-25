package com.airline.service;

import com.airline.dao.ClassTypeDao;
import com.airline.dao.FlightDao;
import com.airline.dao.PeriodDao;
import com.airline.dao.PlaneDao;
import com.airline.dtomapper.FlightDTOMapper;
import com.airline.model.ClassType;
import com.airline.model.ClassTypeEnum;
import com.airline.model.Period;
import com.airline.model.Plane;
import com.airline.model.dto.FlightDTO;
import com.airline.model.dto.Schedule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ModelMapper.class)
public class FlightServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(FlightServiceTest.class);

    @Autowired
    private ModelMapper modelMapper;

    @MockBean
    private FlightDao flightDao;

    @MockBean
    private PlaneDao planeDao;

    @MockBean
    private PeriodDao periodDao;

    @MockBean
    private ClassTypeDao classTypeDao;

    @Test
    public void saveFlightTest(){
        FlightDTOMapper flightDTOMapper = new FlightDTOMapper(modelMapper);
        FlightServiceImpl flightService = new FlightServiceImpl(flightDao, flightDTOMapper, planeDao, periodDao, classTypeDao);
        FlightDTO flightDTO = buildFlight();

        when(planeDao.findPlaneByName("testPlane")).thenReturn(new Plane(1L, "testPlane", 2,2,2,4));
        when(periodDao.selectPeriodByValue("Thu")).thenReturn(new Period(30L, "Thu"));
        when(periodDao.selectPeriodByValue("Fri")).thenReturn(new Period(32L, "Fri"));
        when(classTypeDao.findClassTypeByName(ClassTypeEnum.BUSINESS.name())).thenReturn(new ClassType(1L, ClassTypeEnum.BUSINESS.name()));
        when(classTypeDao.findClassTypeByName(ClassTypeEnum.ECONOMY.name())).thenReturn(new ClassType(2L, ClassTypeEnum.ECONOMY.name()));

        logger.info(flightService.save(flightDTO).toString());

    }

    private FlightDTO buildFlight(){
        FlightDTO flightDTO = new FlightDTO();

        flightDTO.setId(1L);
        flightDTO.setFlightName("test");
        flightDTO.setFromTown("test");
        flightDTO.setPlaneName("testPlane");
        flightDTO.setToTown("test");
        flightDTO.setStart(Time.valueOf("07:30:00"));
        flightDTO.setDuration(Time.valueOf("03:00:00"));
        Schedule schedule = new Schedule();
        schedule.setFromDate(new Date());
        schedule.setToDate(new Date(schedule.getFromDate().getTime() + 10*24*60*60*1000));

        schedule.setPeriods(Arrays.asList("Thu","Fri"));
        flightDTO.setSchedule(schedule);
        flightDTO.setPlaneName("testPlane");
        flightDTO.setPriceBusiness(new BigDecimal("6666.00"));
        flightDTO.setPriceEconomy(new BigDecimal("3333.00"));
        flightDTO.setDates(new ArrayList<>());

        return flightDTO;
    }
}
