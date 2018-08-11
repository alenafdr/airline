package com.airline.controller;

import com.airline.model.dto.OrderDTO;
import com.airline.rest.OrderController;
import com.airline.service.OrderService;
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
import org.springframework.test.web.servlet.RequestBuilder;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(value = OrderController.class, secure = false)
@ContextConfiguration(classes = {OrderController.class})
public class OrderControllerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderControllerTest.class);
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
        when(orderService.saveOrder(any(OrderDTO.class), eq("test"))).thenReturn(new OrderDTO());
        RequestBuilder requestBuilder = post("/api/orders/")
                .session(mockSession)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(buildOrderDTOString())
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE);

        mockMvc.perform(requestBuilder).andExpect(status().isOk());
    }

    @Test
    public void saveInvalidOrderTest() throws Exception {
        when(orderService.saveOrder(any(OrderDTO.class), eq("test"))).thenReturn(new OrderDTO());
        String order = buildOrderDTOString();
        order = order.replace("2018-07-12", "2018.07.12");
        RequestBuilder requestBuilder = post("/api/orders/")
                .session(mockSession)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(order)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE);

        mockMvc.perform(requestBuilder).andExpect(status().is4xxClientError());

        requestBuilder = post("/api/orders/")
                .session(mockSession)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(buildOrderDTOWithoutPassengersString())
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE);

        mockMvc.perform(requestBuilder).andExpect(status().is4xxClientError());
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
                "        \"flightId\": 1,\n" +
                "        \"date\": \"2018-07-12\",\n" +
                "        \"passengers\": [\n" +
                "            {\n" +
                "                \"firstName\": \"Иван\",\n" +
                "                \"lastName\": \"Иванов\",\n" +
                "                \"nationality\": \"RU\",\n" +
                "                \"passport\": \"5555 55555\",\n" +
                "                \"class\": \"ECONOMY\"\n" +
                "            }\n" +
                "        ]\n" +
                "    }";
    }

    private String buildOrderDTOWithoutPassengersString() {
        return "{\n" +
                "        \"flightId\": 1,\n" +
                "        \"date\": \"2018-07-12\"\n" +
                "    }";
    }
}
