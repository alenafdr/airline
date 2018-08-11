package com.airline.controller;

import com.airline.config.SecurityConfig;
import com.airline.dao.AdminDao;
import com.airline.dao.ClientDao;
import com.airline.exceptions.SessionIsNotAuthorizedException;
import com.airline.model.UserClient;
import com.airline.model.dto.FlightDTO;
import com.airline.model.dto.UserClientDTO;
import com.airline.model.dto.UserEntityDTO;
import com.airline.rest.FlightController;
import com.airline.rest.SessionController;
import com.airline.security.SessionUserDetailService;
import com.airline.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(value = SessionController.class)
@ContextConfiguration(classes = {SecurityConfig.class, SessionUserDetailService.class, SessionController.class})
public class SessionControllerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(SessionControllerTest.class);
    private final static String USER_LOGIN = "testLogin";
    private final static String USER_PASSWORD = "testPassword";
    private final static String USER_FIRST_NAME = "testFirstName";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private ClientDao clientDao;

    @MockBean
    private AdminDao adminDao;

    @MockBean
    private FlightController flightController;

    @Before
    public void init() {

    }

    @Test(expected = SessionIsNotAuthorizedException.class)
    public void sessionIsNotAuthorized() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                "/api/flights/1").accept(
                MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
    }

    @Test
    public void sessionIsAuthorized() throws Exception {
        MockHttpSession mockSession = new MockHttpSession();

        when(clientDao.findByLogin(USER_LOGIN)).thenReturn(buildOptionalUserClient());
        when(userService.getUserByLogin(USER_LOGIN)).thenReturn(buildDTOUser());
        when(flightController.read(1L)).thenReturn(buildFlight());

        RequestBuilder requestBuilder = post(
                "/api/session/")
                .session(mockSession)
                .contentType(MediaType.APPLICATION_JSON)
                .content(createUserJson())
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder).andExpect(status().isOk());

        requestBuilder = MockMvcRequestBuilders.get("/api/flights/1")
                .session(mockSession)
                .accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder).andExpect(status().isOk());
    }

    private Optional<UserClient> buildOptionalUserClient() {
        UserClient userClient = new UserClient();
        userClient.setLogin(USER_LOGIN);
        userClient.setPassword(USER_PASSWORD);
        return Optional.of(userClient);
    }

    private UserEntityDTO buildDTOUser() {
        UserClientDTO userClientDTO = new UserClientDTO();
        userClientDTO.setLogin(USER_LOGIN);
        userClientDTO.setPassword(USER_PASSWORD);
        userClientDTO.setFirstName(USER_FIRST_NAME);
        return userClientDTO;
    }

    private ResponseEntity<FlightDTO> buildFlight() {
        FlightDTO flightDTO = new FlightDTO();

        flightDTO.setId(1L);
        flightDTO.setFlightName("test");
        flightDTO.setFromTown("test");
        return new ResponseEntity<>(flightDTO, HttpStatus.OK);
    }

    private String createUserJson() {
        return ("{\n" +
                "\"login\":\"" + USER_LOGIN + "\",\n" +
                "\"password\":\"" + USER_PASSWORD + "\"\n" +
                "}");
    }
}
