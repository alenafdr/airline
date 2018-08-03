package com.airline.service;

import com.airline.dao.ClassTypeDao;
import com.airline.dao.FlightDao;
import com.airline.dao.PeriodDao;
import com.airline.dao.PlaneDao;
import com.airline.dto.mapper.FlightDTOMapper;
import com.airline.exceptions.AlreadyExistsException;
import com.airline.exceptions.PlaneNotFoundException;
import com.airline.model.*;
import com.airline.model.dto.FlightDTO;
import com.airline.model.dto.Schedule;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.*;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ModelMapper.class)
public class FlightServiceTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(FlightServiceTest.class);

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

    private FlightServiceImpl flightService;

    @Before
    public void init() {
        FlightDTOMapper flightDTOMapper = new FlightDTOMapper(modelMapper);
        flightService = new FlightServiceImpl(flightDao, flightDTOMapper, planeDao, periodDao, classTypeDao);
    }

    @Test
    public void saveFlightTest() {

        FlightDTO flightDTO = buildFlight();

        when(planeDao.findPlaneByName("testPlane")).thenReturn(Optional.ofNullable(new Plane(1L, "testPlane", 2, 2, 2, 4)));
        when(periodDao.selectPeriodByValue("Thu")).thenReturn(new Period(30L, "Thu"));
        when(periodDao.selectPeriodByValue("Fri")).thenReturn(new Period(32L, "Fri"));
        when(classTypeDao.findClassTypeByName(ClassTypeEnum.BUSINESS.name())).thenReturn(new ClassType(1L, ClassTypeEnum.BUSINESS.name()));
        when(classTypeDao.findClassTypeByName(ClassTypeEnum.ECONOMY.name())).thenReturn(new ClassType(2L, ClassTypeEnum.ECONOMY.name()));

        LOGGER.info(flightService.save(flightDTO).toString());

    }

    @Test
    public void createDepartureByDayOfWeekTest() {
        List<Period> periodList = new ArrayList<>();
        periodList.add(new Period(30L, "Thu"));
        periodList.add(new Period(32L, "Fri"));

        Date dateStart = new Date();
        Date dateEnd = new Date(dateStart.getTime() + 14 * 24 * 60 * 60 * 1000);

        List<Departure> departures = flightService.createDepartureByPeriods(periodList, dateStart, dateEnd);
        for (Departure departure : departures) {
            LocalDate localDate = departure.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            String dayOfWeek = localDate.getDayOfWeek().name();
            assertTrue(dayOfWeek.equalsIgnoreCase("THURSDAY") || dayOfWeek.equalsIgnoreCase("FRIDAY"));
        }

    }

    @Test
    public void createDepartureByNumbersDayTest() {
        List<Period> periodList = new ArrayList<>();
        periodList.add(new Period(1L, "1"));
        periodList.add(new Period(15L, "15"));

        Date dateStart = new Date();
        Date dateEnd = new Date(dateStart.getTime() + 30 * 24 * 60 * 60 * 1000L);

        List<Departure> departures = flightService.createDepartureByPeriods(periodList, dateStart, dateEnd);
        for (Departure departure : departures) {
            LocalDate localDate = departure.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            int dayOfMount = localDate.getDayOfMonth();
            assertTrue(dayOfMount == 1 || dayOfMount == 15);
        }
    }

    @Test(expected = PlaneNotFoundException.class)
    public void createFlightWithUnknownPlane(){
        FlightDTO flightDTO = buildFlight();
        when(planeDao.findPlaneByName("testPlane")).thenReturn(Optional.ofNullable(null));
        flightService.save(flightDTO);

    }

    @Test(expected = AlreadyExistsException.class)
    public void createFlightWithDuplicateName(){
        FlightDTO flightDTO = buildFlight();

        when(planeDao.findPlaneByName("testPlane")).thenReturn(Optional.ofNullable(new Plane(1L, "testPlane", 2, 2, 2, 4)));
        when(periodDao.selectPeriodByValue("Thu")).thenReturn(new Period(30L, "Thu"));
        when(periodDao.selectPeriodByValue("Fri")).thenReturn(new Period(32L, "Fri"));
        when(classTypeDao.findClassTypeByName(ClassTypeEnum.BUSINESS.name())).thenReturn(new ClassType(1L, ClassTypeEnum.BUSINESS.name()));
        when(classTypeDao.findClassTypeByName(ClassTypeEnum.ECONOMY.name())).thenReturn(new ClassType(2L, ClassTypeEnum.ECONOMY.name()));

        flightService.save(flightDTO);
        when(flightDao.selectCountByName("test")).thenReturn(1);
        flightService.save(flightDTO);
    }


    private FlightDTO buildFlight() {
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
        schedule.setToDate(new Date(schedule.getFromDate().getTime() + 10 * 24 * 60 * 60 * 1000));
        schedule.setPeriods(Arrays.asList("Thu,Fri"));
        schedule.setPeriods(Arrays.asList("Thu", "Fri"));
        flightDTO.setSchedule(schedule);
        flightDTO.setPlaneName("testPlane");
        flightDTO.setPriceBusiness(new BigDecimal("6666.00"));
        flightDTO.setPriceEconomy(new BigDecimal("3333.00"));
        flightDTO.setDates(new ArrayList<>());

        return flightDTO;
    }
}
