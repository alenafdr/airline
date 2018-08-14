package com.airline.dao;


import com.airline.model.Departure;
import com.airline.model.Flight;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@Import({DepartureDao.class})
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class DepartureDaoTest {

    private final static Logger LOGGER = LoggerFactory.getLogger(DepartureDaoTest.class);

    @Autowired
    DepartureDao departureDao;

    @Test
    public void findDepartureByFlightIdAndDateTest() throws Exception {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = format.parse("2018-07-12");
        Departure departure = departureDao.findDepartureByFlightIdAndDate(new Departure(date, new Flight(1L))).get();


        LOGGER.info(departure.toString());
    }
}
