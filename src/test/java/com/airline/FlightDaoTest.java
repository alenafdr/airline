package com.airline;

import com.airline.dao.*;
import com.airline.model.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
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

@Import({FlightDao.class, PriceDao.class, DepartureDao.class, PeriodDao.class, PlaneDao.class, ClassType.class})
@RunWith(SpringRunner.class)
@MybatisTest

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class FlightDaoTest {

    @Autowired
    private FlightDao flightDao;

    @Autowired
    private PlaneDao planeDao;

    @Autowired
    private PriceDao priceDao;

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
        assertTrue(newFlight.equals(selectFlight));
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
        prices.add(new Price(flight, classTypeDao.findClassTypeById(1L), new BigDecimal(3333)));
        prices.add(new Price(flight, classTypeDao.findClassTypeById(2L), new BigDecimal(3333)));
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
