package com.airline;

import com.airline.dao.*;
import com.airline.model.Departure;
import com.airline.model.Flight;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@Import({FlightDao.class, PriceDao.class, DepartureDao.class, PeriodDao.class, PlaneDao.class})
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
        flight.setPrices(priceDao.findPricesByFlightId(2L));
        List<Departure> departures = new ArrayList<>();
        departures.add(new Departure(new Date()));
        flight.setDepartures(departures);
        flight.setPeriods(periodDao.selectPeriodsByFlightId(2L));
        return flight;
    }
}
