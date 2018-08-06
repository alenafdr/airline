package com.airline.controller;

import com.airline.model.dto.FlightDTO;
import com.airline.model.dto.Schedule;
import com.airline.rest.FlightController;
import com.airline.service.FlightService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import java.math.BigDecimal;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(value = FlightController.class, secure = false)
@ContextConfiguration(classes = {FlightController.class})
public class FlightControllerTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(FlightControllerTest.class);

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FlightService flightService;

    @Test
    public void getFlightById() throws Exception {
        when(flightService.getById(1L)).thenReturn(buildFlight());
        RequestBuilder requestBuilder = get("/api/flights/1")
                .accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder).andExpect(status().isOk());
    }

    @Test
    public void validationTestIdNotNull() throws Exception {
        FlightDTO flightDTO = buildFlight();
        FlightDTO flightDTOSaved = buildFlight();
        when(flightService.save(any(FlightDTO.class))).thenReturn(flightDTOSaved);

        RequestBuilder requestBuilder = post("/api/flights/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(buildFlightWithIdAndPeriodsString())
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE);

        mockMvc.perform(requestBuilder).andExpect(status().is4xxClientError());
    }

    @Test
    public void validationTestDatesAndPeriodsBoth() throws Exception {

        when(flightService.save(any(FlightDTO.class))).thenReturn(buildFlight());

        RequestBuilder requestBuilder = post("/api/flights/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(buildFlightWithIdAndPeriodsString())
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE);

        mockMvc.perform(requestBuilder).andExpect(status().is4xxClientError());
    }

    @Test
    public void validationTestWithDates() throws Exception {

        when(flightService.save(any(FlightDTO.class))).thenReturn(buildFlight());

        RequestBuilder requestBuilder = post("/api/flights/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(buildFlightWithDatesString())
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE);

        mockMvc.perform(requestBuilder).andExpect(status().isOk());
    }

    @Test
    public void validationTestWithDatesUpdate() throws Exception {

        when(flightService.save(any(FlightDTO.class))).thenReturn(buildFlight());

        RequestBuilder requestBuilder = put("/api/flights/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(buildFlightWithDatesString())
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE);

        mockMvc.perform(requestBuilder).andExpect(status().isOk());
    }

    @Test
    public void validationTestWithPeriods() throws Exception {

        when(flightService.save(any(FlightDTO.class))).thenReturn(buildFlight());

        RequestBuilder requestBuilder = post("/api/flights/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(buildFlightWithPeriodsString())
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE);

        mockMvc.perform(requestBuilder).andExpect(status().isOk());
    }

    private FlightDTO buildFlight() throws Exception {
        FlightDTO flightDTO = new FlightDTO();

        flightDTO.setId(1L);
        flightDTO.setFlightName("test");
        flightDTO.setFromTown("test");
        flightDTO.setToTown("test");
        flightDTO.setStart(Time.valueOf("07:30:00"));
        flightDTO.setDuration(Time.valueOf("03:00:00"));
        Schedule schedule = new Schedule();

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        schedule.setFromDate(format.parse("2018-01-01"));
        schedule.setToDate(format.parse("2018-02-01"));
        schedule.setPeriods(Collections.singletonList("daily"));
        flightDTO.setSchedule(schedule);
        flightDTO.setPlaneName("testPlane");
        flightDTO.setPriceBusiness(new BigDecimal("6666.00"));
        flightDTO.setPriceEconomy(new BigDecimal("3333.00"));
        List<Date> dates = new ArrayList<>();
        dates.add(format.parse("2018-01-03"));
        flightDTO.setDates(dates);

        return flightDTO;
    }

    public String buildFlightWithIdAndPeriodsString() {
        return "{\n" +
                "    \"id\": 5,\n" +
                "    \"flightName\": \"158\",\n" +
                "    \"planeName\": \"Airbus A320\",\n" +
                "    \"fromTown\": \"Омск\",\n" +
                "    \"toTown\": \"Париж\",\n" +
                "    \"start\": \"12:00:00\",\n" +
                "    \"duration\": \"04:30:00\",\n" +
                "    \"priceBusiness\": 15000,\n" +
                "    \"priceEconomy\": 10000,\n" +
                "    \"schedule\": {\n" +
                "        \"fromDate\": \"2017-12-31\",\n" +
                "        \"toDate\": \"2018-12-31\",\n" +
                "        \"periods\": [\n" +
                "            \"15\",\n" +
                "            \"Fri\"\n" +
                "        ]\n" +
                "    },\n" +
                "    \"dates\": [\n" +
                "    ],\n" +
                "    \"approved\": false\n" +
                "}";
    }

    public String buildFlightWithDatesString() {
        return "{\n" +
                "    \"flightName\": \"158\",\n" +
                "    \"planeName\": \"Airbus A320\",\n" +
                "    \"fromTown\": \"Омск\",\n" +
                "    \"toTown\": \"Париж\",\n" +
                "    \"start\": \"12:00:00\",\n" +
                "    \"duration\": \"04:30:00\",\n" +
                "    \"priceBusiness\": 15000,\n" +
                "    \"priceEconomy\": 10000,\n" +
                "    \"schedule\": {\n" +
                "        \"fromDate\": \"2017-12-31\",\n" +
                "        \"toDate\": \"2018-12-31\",\n" +
                "        \"periods\": [ \n" +
                "        ]\n" +
                "    },\n" +
                "    \"dates\": [\n" +
                "        \"2018-07-12\"\n" +
                "    ],\n" +
                "    \"approved\": false\n" +
                "}";
    }

    private String buildFlightWithPeriodsString() {
        return "{\n" +
                "    \"flightName\": \"158\",\n" +
                "    \"planeName\": \"Airbus A320\",\n" +
                "    \"fromTown\": \"Омск\",\n" +
                "    \"toTown\": \"Париж\",\n" +
                "    \"start\": \"12:00:00\",\n" +
                "    \"duration\": \"04:30:00\",\n" +
                "    \"priceBusiness\": 15000,\n" +
                "    \"priceEconomy\": 10000,\n" +
                "    \"schedule\": {\n" +
                "        \"fromDate\": \"2017-12-31\",\n" +
                "        \"toDate\": \"2018-12-31\",\n" +
                "        \"periods\": [\n" +
                "            \"15\",\n" +
                "            \"Fri\"\n" +
                "        ]\n" +
                "    },\n" +
                "    \"dates\": [\n" +
                "    ],\n" +
                "    \"approved\": false\n" +
                "}";
    }

}
