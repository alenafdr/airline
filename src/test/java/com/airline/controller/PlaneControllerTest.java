package com.airline.controller;

import com.airline.model.dto.PlaneDTO;
import com.airline.rest.PlaneController;
import com.airline.service.impl.PlaneServiceImpl;
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
@WebMvcTest(value = PlaneController.class, secure = false)
@ContextConfiguration(classes = {PlaneController.class})
public class PlaneControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PlaneServiceImpl planeService;

    private MockHttpSession mockSession;

    @Before
    public void init() {
        mockSession = new MockHttpSession();
        mockSession.setAttribute("login", "test");
        mockSession.setAttribute("userId", "1");
        mockSession.setAttribute("userType", "client");
    }

    @Test
    public void getPlanesTest() throws Exception {
        when(planeService.getPlanes()).thenReturn(buildListPlane());

        RequestBuilder requestBuilder = get("/api/planes/")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE);

        mockMvc.perform(requestBuilder).andExpect(status().isOk());
    }

    private List<PlaneDTO> buildListPlane() {
        List<PlaneDTO> planes = new ArrayList<>();
        planes.add(new PlaneDTO("name", 2, 4, 2, 2));
        planes.add(new PlaneDTO("name2", 3, 7, 2, 3));
        planes.add(new PlaneDTO("name3", 4, 10, 4, 5));
        return planes;
    }
}
