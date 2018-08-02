package com.airline.integration;

import com.airline.AirlineApplication;
import com.airline.model.dto.FlightDTO;
import com.airline.model.dto.Schedule;
import com.airline.model.dto.UserEntityDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.sql.Time;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = AirlineApplication.class)
public class FlightIntegrationTest {

    private static final Logger logger = LoggerFactory.getLogger(FlightIntegrationTest.class);
    public final static String USER_LOGIN = "ivanovadmin";
    private final static String USER_PASSWORD = "123456";
    private final String URL_FLIGHT = "http://localhost:8080/api/flights/";

    private TestRestTemplate restTemplate = new TestRestTemplate();

    @Before
    public void init(){
        //настройки для прохождения аутентификации
        System.setProperty("integrationTestLogin", USER_LOGIN);

        restTemplate.getRestTemplate().setInterceptors(
                Collections.singletonList((request, body, execution) -> {
                    request.getHeaders()
                            .add("integrationTesting", "test");
                    return execution.execute(request, body);
                }));
    }

    @Test
    public void flightIntegrationTest() throws Exception{
        //залогинится админом

        ResponseEntity<UserEntityDTO> userEntityDTOResponse = restTemplate.exchange("http://localhost:8080/api/session/",
                HttpMethod.POST ,
                createUserJson(),
                UserEntityDTO.class);

        assertNotNull(userEntityDTOResponse.getBody().getId());

        //сохранить flight
        FlightDTO testFlightDTO =  buildFlight();
        ResponseEntity<FlightDTO> flightDTOResponsePost =
                restTemplate.postForEntity(URL_FLIGHT,
                testFlightDTO,
                FlightDTO.class);
        assertEquals(flightDTOResponsePost.getBody().getFlightName(),testFlightDTO.getFlightName());

        //запросить flight
        Long id = flightDTOResponsePost.getBody().getId();
        ResponseEntity<FlightDTO> flightDTOResponseGet =
                restTemplate.getForEntity(URL_FLIGHT + id,
                FlightDTO.class);

        assertEquals(flightDTOResponsePost.getBody().toString(), flightDTOResponseGet.getBody().toString());

        //изменить flight
        flightDTOResponseGet.getBody().setFromTown("test2");
        flightDTOResponseGet.getBody().setToTown("test2");

        ResponseEntity<FlightDTO> flightDTOResponsePut =
                restTemplate.exchange(URL_FLIGHT + "{id}",
                        HttpMethod.PUT,
                        new HttpEntity<FlightDTO>(flightDTOResponseGet.getBody()),
                        FlightDTO.class,
                        id);
        assertEquals(flightDTOResponsePut.getBody().getFromTown(), "test2");
        assertEquals(flightDTOResponsePut.getBody().getToTown(), "test2");


        //удалить flight

        ResponseEntity<String> responseEntityDel = restTemplate.exchange(URL_FLIGHT +"{id}",
                HttpMethod.DELETE,
                null,
                String.class,
                id);


        ResponseEntity<FlightDTO> flightDTOResponseGetDelete =
                restTemplate.getForEntity(URL_FLIGHT + id,
                        FlightDTO.class);
        assertEquals(flightDTOResponseGetDelete.getStatusCode(), HttpStatus.NOT_FOUND);

    }

    private FlightDTO buildFlight(){
        FlightDTO flightDTO = new FlightDTO();

        flightDTO.setFlightName("test");
        flightDTO.setPlaneName("ТУ-134");
        flightDTO.setFromTown("test");
        flightDTO.setToTown("test");
        flightDTO.setStart(Time.valueOf("03:30:00"));
        flightDTO.setDuration(Time.valueOf("02:40:00"));
        flightDTO.setPriceEconomy(new BigDecimal("3333.00"));
        flightDTO.setPriceBusiness(new BigDecimal("6666.00"));

        Schedule schedule = new Schedule();
        schedule.setFromDate(new Date());
        schedule.setToDate(new Date(schedule.getFromDate().getTime() + 10*24*60*60*1000));
        schedule.setPeriods(new ArrayList<>(Arrays.asList("1", "3", "10")));

        flightDTO.setSchedule(schedule);
        return flightDTO;
    }

    private HttpEntity<Map<String,String>> createUserJson(){
        Map<String,String> json = new HashMap<>();
        json.put("login",USER_LOGIN);
        json.put("password",USER_PASSWORD);
        return new HttpEntity<>(json);
    }
}
