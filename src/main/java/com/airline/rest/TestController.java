package com.airline.rest;

import com.airline.dao.*;
import com.airline.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class TestController {

    @Autowired
    private ClassTypeDao classTypeDao;

    @Autowired
    private FlightDao flightDao;

    @Autowired
    private PlaneDao planeDao;

    @Autowired
    private PriceDao priceDao;

    @Autowired
    private PeriodDao periodDao;

    @Autowired
    private DepartureDao departureDao;

    @GetMapping(value = "/")
    public ResponseEntity<String> test(){

        ClassType classType = classTypeDao.findClassTypeById(1L);
        return new ResponseEntity<>(classType.toString(), HttpStatus.OK);
    }

    @GetMapping(value = "/flight")
    public ResponseEntity<String> flight(){

        Flight flight = flightDao.findOne(1L);
        return new ResponseEntity<>(flight.toString(), HttpStatus.OK);
    }

    @GetMapping(value = "/plane")
    public ResponseEntity<String> plane(){

        Plane plane = planeDao.findPlanetById(1L);
        return new ResponseEntity<>(plane.toString(), HttpStatus.OK);
    }

    @GetMapping(value = "/price")
    public ResponseEntity<String> price(){
        List<Price> prices = priceDao.findPricesByFlightId(5L);
        return new ResponseEntity<>(prices.toString(), HttpStatus.OK);
    }

    @GetMapping(value = "/period")
    public ResponseEntity<String> period(){
        List<Period> prices = periodDao.listPeriod();
        return new ResponseEntity<>(prices.toString(), HttpStatus.OK);
    }

    @GetMapping(value = "/flightinsert")
    public ResponseEntity<String> testInsert(){
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
        flightDao.save(flight);
        return new ResponseEntity<>(flight.toString(), HttpStatus.OK);
    }

    @GetMapping(value = "/insertprice")
    public ResponseEntity<String> insertPrice(){
        Price price = new Price(new BigDecimal(6666));
        price.setClassType(classTypeDao.findClassTypeById(1L));
        price.setFlight(flightDao.findOne(2L));
        priceDao.save(price);
        return new ResponseEntity<>(price.toString(), HttpStatus.OK);
    }

}
