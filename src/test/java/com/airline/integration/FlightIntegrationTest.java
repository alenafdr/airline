package com.airline.integration;

import com.airline.AirlineApplication;
import com.airline.model.dto.FlightDTO;
import com.airline.model.dto.Schedule;
import com.airline.model.dto.UserEntityDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.sql.Time;
import java.util.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = AirlineApplication.class)
public class FlightIntegrationTest {

    private static final Logger logger = LoggerFactory.getLogger(FlightIntegrationTest.class);
    private final static String USER_LOGIN = "ivanovadmin";
    private final static String USER_PASSWORD = "123456";
    private final static String USER_FIRST_NAME = "testFirstName";

    private TestRestTemplate restTemplate = new TestRestTemplate();

    @Test
    public void flightIntegrationTest() throws Exception{

        restTemplate.getRestTemplate().setInterceptors(
                Collections.singletonList((request, body, execution) -> {
                    request.getHeaders()
                            .add("test", "test");
                    return execution.execute(request, body);
                }));
        //добавить в базу пользователя админа

        //залогинится этим пользователем

        ResponseEntity<UserEntityDTO> userEntityDTOResponse = restTemplate.exchange("http://localhost:8080/api/session/",
                HttpMethod.POST ,
                createUserJson(),
                UserEntityDTO.class);

        logger.info(userEntityDTOResponse.toString());

        //сохранить flight
        ResponseEntity<FlightDTO> flightDTOResponse = restTemplate.exchange("http://localhost:8080/api/flights/",
                HttpMethod.POST,
                buildFlight(),
                FlightDTO.class);
        logger.info(flightDTOResponse.toString());

        //запросить flight

        //изменить flight

        //удалить flight
    }

    private HttpEntity<FlightDTO> buildFlight(){
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
        return new HttpEntity<>(flightDTO);
    }

    private HttpEntity<Map<String,String>> createUserJson(){
        Map<String,String> json = new HashMap<>();
        json.put("login",USER_LOGIN);
        json.put("password",USER_PASSWORD);
        return new HttpEntity<>(json);
    }
}
