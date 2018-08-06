package com.airline.controller;

import com.airline.model.dto.UserAdminDTO;
import com.airline.model.dto.UserClientDTO;
import com.airline.rest.UserController;
import com.airline.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(value = UserController.class, secure = false)
@ContextConfiguration(classes = {UserController.class})
public class UserControllerTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserControllerTest.class);

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private MockHttpSession mockSession;

    @Before
    public void init(){
        mockSession = new MockHttpSession();
        mockSession.setAttribute("login", "test");
    }

    @Test
    public void createAdminTest() throws Exception{
        UserAdminDTO userAdminDTO = buildUserAdminDTO();
        when(userService.saveAdmin(any(UserAdminDTO.class))).thenReturn(userAdminDTO);

        RequestBuilder requestBuilder = post("/api/admin/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(buildAdminDTOString())
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE);

        mockMvc.perform(requestBuilder).andExpect(status().isOk());

    }

    @Test
    public void updateAdminTest() throws Exception{
        UserAdminDTO userAdminDTO = buildUserAdminDTO();
        when(userService.updateAdmin(any(UserAdminDTO.class))).thenReturn(userAdminDTO);

        RequestBuilder requestBuilder = put("/api/admin/")
                .session(mockSession)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(buildExistAdminDTOString())
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE);

        mockMvc.perform(requestBuilder).andExpect(status().isOk());

    }

    @Test
    public void createClientTest() throws Exception{
        UserClientDTO userClientDTO = buildUserClientDTO();
        when(userService.saveClient(any(UserClientDTO.class))).thenReturn(userClientDTO);

        RequestBuilder requestBuilder = post("/api/client/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(buildClientDTOString())
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE);

        mockMvc.perform(requestBuilder).andExpect(status().isOk());
    }

    @Test
    public void updateClientTest() throws Exception{
        UserClientDTO userClientDTO = buildUserClientDTO();
        when(userService.updateClient(any(UserClientDTO.class))).thenReturn(userClientDTO);

        RequestBuilder requestBuilder = put("/api/client/")
                .session(mockSession)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(buildExistClientDTOString())
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE);

        mockMvc.perform(requestBuilder).andExpect(status().isOk());

    }

    @Test
    public void readAccounClientTest() throws Exception{
        MockHttpSession mockSession = new MockHttpSession();
        mockSession.setAttribute("login" , "test");

        when(userService.getUserByLogin("test")).thenReturn(buildUserAdminDTO());

        RequestBuilder requestBuilder = get("/api/account/")
                .session(mockSession)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE);

        mockMvc.perform(requestBuilder).andExpect(status().isOk());
    }

    @Test
    public void listClientsTest() throws Exception{
        when(userService.getListClients()).thenReturn(buildListUserClientDTO());

        RequestBuilder requestBuilder = get("/api/clients/")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE);

        mockMvc.perform(requestBuilder).andExpect(status().isOk());
    }

    @Test
    public void validationPhoneTest() throws Exception {
        UserClientDTO userClientDTO = buildUserClientDTO();
        when(userService.saveClient(any(UserClientDTO.class))).thenReturn(userClientDTO);

        String jsonStr = buildClientDTOString();

        jsonStr = jsonStr.replace("+79999999999", "+78999999999");
        LOGGER.info(jsonStr);
        RequestBuilder requestBuilder = post("/api/client/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(jsonStr)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE);

        mockMvc.perform(requestBuilder).andExpect(status().is4xxClientError());

        jsonStr = jsonStr.replace("+78999999999", "79999999999");
        requestBuilder = post("/api/client/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(jsonStr)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE);
        mockMvc.perform(requestBuilder).andExpect(status().is4xxClientError());

        jsonStr = jsonStr.replace( "79999999999", "+88999999999");
        requestBuilder = post("/api/client/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(jsonStr)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE);
        mockMvc.perform(requestBuilder).andExpect(status().is4xxClientError());

        jsonStr = jsonStr.replace(  "+88999999999", "+889999999");
        requestBuilder = post("/api/client/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(jsonStr)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE);
        mockMvc.perform(requestBuilder).andExpect(status().is4xxClientError());

        jsonStr = jsonStr.replace(   "+889999999", "+79999999999");
        requestBuilder = post("/api/client/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(jsonStr)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE);
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

        mockMvc.perform(requestBuilder).andExpect(status().isOk());

        jsonStr = jsonStr.replace(   "+79999999999", "+7-999-999-99-99");
        requestBuilder = post("/api/client/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(jsonStr)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE);
        mockMvc.perform(requestBuilder).andExpect(status().isOk());
    }

    private UserAdminDTO buildUserAdminDTO(){
        UserAdminDTO userAdminDTO = new UserAdminDTO();
        userAdminDTO.setFirstName("test");
        userAdminDTO.setLastName("test");
        userAdminDTO.setPosition("test");
        userAdminDTO.setLogin("test");
        userAdminDTO.setPassword("test");
        return userAdminDTO;
    }

    private UserClientDTO buildUserClientDTO(){
        UserClientDTO userClientDTO = new UserClientDTO();
        userClientDTO.setId(1L);
        userClientDTO.setFirstName("test");
        userClientDTO.setLastName("test");
        userClientDTO.setLogin("test");
        userClientDTO.setPassword("test");
        userClientDTO.setUserType("client");
        userClientDTO.setPhone("+79999999999");
        userClientDTO.setEmail("test@test.com");
        return userClientDTO;
    }

    private List<UserClientDTO> buildListUserClientDTO(){
        List<UserClientDTO> userAdminDTOS = new ArrayList<>();
        userAdminDTOS.add(buildUserClientDTO());
        userAdminDTOS.add(buildUserClientDTO());
        return userAdminDTOS;
    }

    private String buildAdminDTOString(){
        return "{\n" +
                "    \"firstName\": \"Иван\",\n" +
                "    \"lastName\": \"Иванов\",\n" +
                "    \"patronymic\": null,\n" +
                "    \"password\": \"123456\",\n" +
                "    \"login\": \"test\",\n" +
                "    \"position\": \"инженер\"\n" +
                "}";
    }

    private String buildExistAdminDTOString(){
        return "{\n" +
                "    \"firstName\": \"Иван\",\n" +
                "    \"lastName\": \"Иванов\",\n" +
                "    \"patronymic\": null,\n" +
                "    \"oldPassword\": \"123456\",\n" +
                "    \"newPassword\": \"123456\",\n" +
                "    \"position\": \"инженер\"\n" +
                "}";
    }

    private String buildClientDTOString(){
        return "{\n" +
                "    \"firstName\": \"Иван\",\n" +
                "    \"lastName\": \"Иванов\",\n" +
                "    \"patronymic\": null,\n" +
                "    \"email\": \"test@test.com\",\n" +
                "    \"phone\": \"+79999999999\",\n" +
                "    \"login\": \"test\",\n" +
                "    \"password\": \"123456\"\n" +
                "}";
    }

    private String buildExistClientDTOString(){
        return "{\n" +
                "    \"firstName\": \"Иван\",\n" +
                "    \"lastName\": \"Иванов\",\n" +
                "    \"patronymic\": null,\n" +
                "    \"email\": \"test@test.com\",\n" +
                "    \"phone\": \"+79999999999\",\n" +
                "    \"oldPassword\": \"test\",\n" +
                "    \"newPassword\": \"123456\"\n" +
                "}";
    }
}
