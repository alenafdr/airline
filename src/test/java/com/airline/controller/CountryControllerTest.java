package com.airline.controller;

import com.airline.model.dto.CountryDTO;
import com.airline.rest.CountryController;
import com.airline.service.CountryService;
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

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(value = CountryController.class, secure = false)
@ContextConfiguration(classes = {CountryController.class})
public class CountryControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CountryService countryService;

    private MockHttpSession mockSession;

    @Before
    public void init() {
        mockSession = new MockHttpSession();
        mockSession.setAttribute("login", "test");
        mockSession.setAttribute("userId", "1");
        mockSession.setAttribute("userType", "client");
    }

    @Test
    public void getCountriesTest() throws Exception {
        when(countryService.getCountries()).thenReturn(buildListCountriesDTO());

        RequestBuilder requestBuilder = get("/api/countries/")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE);

        mockMvc.perform(requestBuilder).andExpect(status().isOk());
    }

    private List<CountryDTO> buildListCountriesDTO() {
        List<CountryDTO> countryDTOList = new ArrayList<>();
        countryDTOList.add(new CountryDTO("RU", "Russian Federation"));
        countryDTOList.add(new CountryDTO("IN", "India"));
        return countryDTOList;
    }

}
