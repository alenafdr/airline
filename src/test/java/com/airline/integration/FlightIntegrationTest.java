package com.airline.integration;

import com.airline.AirlineApplication;
import com.airline.controller.SessionControllerTest;
import com.airline.dao.AdminDao;
import com.airline.dao.ClientDao;
import com.airline.model.UserClient;
import com.airline.model.dto.FlightDTO;
import com.airline.model.dto.Schedule;
import com.airline.model.dto.UserClientDTO;
import com.airline.model.dto.UserEntityDTO;
import com.airline.rest.FlightController;
import com.airline.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.sql.Time;
import java.util.*;

import static org.mockito.Matchers.shortThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

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
        //добавить в базу пользователя админа

        //залогинится этим пользователем

        ResponseEntity<UserEntityDTO> userEntityDTOResponse = restTemplate.postForEntity("http://localhost:8080/api/session/", createUserJson(), UserEntityDTO.class);
        logger.info(userEntityDTOResponse.toString());

        //сохранить flight
        ResponseEntity<FlightDTO> flightDTOResponse = restTemplate.postForEntity()

        //запросить flight

        //изменить flight

        //удалить flight
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

    private Map<String,String> createUserJson(){
        Map<String,String> json = new HashMap<>();
        json.put("login",USER_LOGIN);
        json.put("password",USER_PASSWORD);
        return json;
    }
}
