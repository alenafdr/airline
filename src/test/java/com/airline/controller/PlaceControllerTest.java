package com.airline.controller;

import com.airline.model.dto.PlaceDTO;
import com.airline.rest.PlaceController;
import com.airline.service.PlaceService;
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

import java.util.Arrays;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(value = PlaceController.class, secure = false)
@ContextConfiguration(classes = {PlaceController.class})
public class PlaceControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PlaceService placeService;

    private MockHttpSession mockSession;

    @Before
    public void init() {
        mockSession = new MockHttpSession();
        mockSession.setAttribute("login", "test");
        mockSession.setAttribute("userId", "1");
        mockSession.setAttribute("userType", "client");
    }

    @Test
    public void getOccupyPlacesTest() throws Exception {
        when(placeService.getOccupyPlaces(123L)).thenReturn(Arrays.asList("1A", "1B", "2C"));

        RequestBuilder requestBuilder = get("/api/places/" + 123)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE);

        mockMvc.perform(requestBuilder).andExpect(status().isOk());
    }

    @Test
    public void registratedPlaceTest() throws Exception {
        when(placeService.registration(any(PlaceDTO.class), eq("test"))).thenReturn(buildPlaceDTO());

        RequestBuilder requestBuilder = post("/api/places/")
                .session(mockSession)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(buildPlaceDTOString())
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE);

        mockMvc.perform(requestBuilder).andExpect(status().isOk());
    }

    private String buildPlaceDTOString() {
        return "{\n" +
                "    \"orderId\": \"123\",\n" +
                "    \"ticket\": \"154\",\n" +
                "    \"lastName\": \"Иванов\",\n" +
                "    \"firstName\": \"Иван\",\n" +
                "    \"place\": \"1A\"\n" +
                "}";
    }


    private PlaceDTO buildPlaceDTO() {
        PlaceDTO placeDTO = new PlaceDTO();
        placeDTO.setTicket(1L);
        placeDTO.setOrderId(1L);
        placeDTO.setFirstName("test");
        placeDTO.setLastName("test");
        placeDTO.setPlace("1A");
        return placeDTO;
    }
}
