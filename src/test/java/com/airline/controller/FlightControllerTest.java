package com.airline.controller;

import com.airline.model.dto.FlightDTO;
import com.airline.model.dto.Schedule;
import com.airline.rest.FlightController;
import com.airline.service.FlightService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.sql.Time;
import java.util.*;


@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(value = FlightController.class, secure = false)
@ContextConfiguration(classes = {FlightController.class})
public class FlightControllerTest {
    private static final Logger logger = LoggerFactory.getLogger(FlightControllerTest.class);

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FlightService flightService;

    @Test
    public void getFlightById() throws Exception {
        Mockito.when(flightService.getById(1L)).thenReturn(buildFlight());
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                "/api/flights/1").accept(
                MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        logger.info(result.getResponse().getContentAsString());
    }



    private FlightDTO buildFlight(){
        FlightDTO flightDTO = new FlightDTO();

        flightDTO.setId(1L);
        flightDTO.setFlightName("test");
        flightDTO.setFromTown("test");
        flightDTO.setToTown("test");
        flightDTO.setStart(Time.valueOf("07:30:00"));
        flightDTO.setDuration(Time.valueOf("03:00:00"));
        Schedule schedule = new Schedule();
        schedule.setFromDate(new Date());
        schedule.setToDate(new Date());
        schedule.setPeriods(Collections.singletonList("daily"));
        flightDTO.setSchedule(schedule);
        flightDTO.setPlaneName("testPlane");
        flightDTO.setPriceBusiness(new BigDecimal("6666.00"));
        flightDTO.setPriceEconomy(new BigDecimal("3333.00"));
        List<Date> dates = new ArrayList<>();
        dates.add(new Date());
        flightDTO.setDates(dates);

        return flightDTO;
    }


}
