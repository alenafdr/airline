package com.airline.controller;

import com.airline.rest.OrderController;
import com.airline.service.OrderService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(value = OrderController.class, secure = false)
@ContextConfiguration(classes = {OrderController.class})
public class OrderControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    private MockHttpSession mockSession;

    @Before
    public void init() {
        mockSession = new MockHttpSession();
        mockSession.setAttribute("login", "test");
        mockSession.setAttribute("userId", "1");
        mockSession.setAttribute("userType", "client");
    }

    @Test
    public void saveOrderTest() throws Exception {
        RequestBuilder requestBuilder = post("/api/orders/")
                .session(mockSession)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(buildOrderDTOString())
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE);

        mockMvc.perform(requestBuilder).andExpect(status().isOk());
    }


    @Test
    public void getOrdersByParametersTest() throws Exception {
        RequestBuilder requestBuilder = get("/api/orders/")
                .session(mockSession)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE);

        mockMvc.perform(requestBuilder).andExpect(status().isOk());
    }

    private String buildOrderDTOString() {
        return "{\n" +
                "        \"orderId\": 1,\n" +
                "        \"flightId\": 1,\n" +
                "        \"fromTown\": \"Омск\",\n" +
                "        \"toTown\": \"Москва\",\n" +
                "        \"flightName\": \"435\",\n" +
                "        \"planeName\": \"ТУ-134\",\n" +
                "        \"date\": 1531332000000,\n" +
                "        \"start\": \"07:30:00\",\n" +
                "        \"duration\": \"03:00:00\",\n" +
                "        \"passengers\": [\n" +
                "            {\n" +
                "                \"ticket\": 6,\n" +
                "                \"firstName\": \"Иван\",\n" +
                "                \"lastName\": \"Иванов\",\n" +
                "                \"nationality\": \"RU-Россия\",\n" +
                "                \"passport\": \"5555 55555\",\n" +
                "                \"price\": 10000,\n" +
                "                \"class\": \"ECONOMY\"\n" +
                "            }\n" +
                "        ],\n" +
                "        \"totalPrice\": 10000\n" +
                "    }";
    }
}
