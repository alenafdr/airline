package com.airline.dao;

import com.airline.dao.*;
import com.airline.model.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@Import({FlightDao.class, PriceDao.class, DepartureDao.class, PeriodDao.class, PlaneDao.class, ClassTypeDao.class})
@RunWith(SpringRunner.class)
@MybatisTest

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class FlightDaoTest {

    private final static Logger logger = LoggerFactory.getLogger(FlightDaoTest.class);

    @Autowired
    private FlightDao flightDao;

    @Autowired
    private PlaneDao planeDao;

    @Autowired
    private PeriodDao periodDao;

    @Autowired
    private ClassTypeDao classTypeDao;

    @Test
    public void selectFlightByIdTest(){
        Flight flight = flightDao.findOne(1L);
        assertNotNull("flight is null", flight);
    }

    @Test
    public void insertFlightTest(){
        Flight newFlight = buildFlight();
        Long id = flightDao.save(newFlight);
        Flight selectFlight = flightDao.findOne(id);
        newFlight.setId(selectFlight.getId());
        assertTrue(newFlight.getFlightName().equals(selectFlight.getFlightName()));
        assertTrue(newFlight.getStart().equals(selectFlight.getStart()));
        assertTrue(newFlight.getDuration().equals(selectFlight.getDuration()));
        assertTrue(newFlight.getPeriods().equals(selectFlight.getPeriods()));
        //assertTrue(newFlight.getPrices().equals(selectFlight.getPrices()));
        //assertTrue(newFlight.getDepartures().equals(selectFlight.getDepartures()));


    }

    @Test
    public void updateFlightTest(){
        Flight flight = flightDao.findOne(1L);
        flight.setFlightName("Test");
        flight.setStart(Time.valueOf("11:11:11"));
        flight.setDuration(Time.valueOf("11:11:11"));
        flight.getDepartures().get(0).setDate(new Date());
        flight.getPrices().get(0).setPrice(new BigDecimal(9999));
        flight.getPrices().remove(1);
        flight.getPeriods().get(0).setId(5L);

        flightDao.update(flight);
        Flight flightNew = flightDao.findOne(1L);

        assertTrue(flightNew.getStart().equals(Time.valueOf("11:11:11")));
        assertTrue(flightNew.getDuration().equals(Time.valueOf("11:11:11")));
        assertTrue(flightNew.getPrices().get(0).getPrice().compareTo(new BigDecimal(9999)) == 0);
        assertTrue(flightNew.getPrices().size() == 1);
        assertTrue(flightNew.getDepartures().get(0).getDate().equals(flightNew.getDepartures().get(0).getDate()));
        assertTrue(flightNew.getPeriods().get(0).getId().equals(5L));
    }

    public Flight buildFlight(){
        Flight flight = new Flight();
        flight.setFlightName("test");
        flight.setFromTown("test");
        flight.setToTown("test");
        flight.setStart(Time.valueOf("07:30:00"));
        flight.setDuration(Time.valueOf("03:00:00"));
        flight.setFromDate(new Date());
        flight.setToDate(new Date());

        flight.setPlane(planeDao.findPlanetById(1L));

        List<Price> prices = new ArrayList<>();
        prices.add(new Price(classTypeDao.findClassTypeById(1L), new BigDecimal(3333)));
        prices.add(new Price(classTypeDao.findClassTypeById(2L), new BigDecimal(4444)));
        flight.setPrices(prices);

        List<Departure> departures = new ArrayList<>();
        departures.add(new Departure(new Date()));
        flight.setDepartures(departures);

        List<Period> periods = new ArrayList<>();
        periods.add(periodDao.selectPeriodById(1L));
        flight.setPeriods(periods);

        return flight;
    }
}
