package com.airline.service;

import com.airline.dao.CountryDao;
import com.airline.dto.mapper.CountryDTOMapper;
import com.airline.model.Country;
import com.airline.model.dto.CountryDTO;
import com.airline.service.impl.CountryServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ModelMapper.class)
public class CountryServiceTest {

    @Autowired
    private ModelMapper modelMapper;

    private CountryServiceImpl countryService;

    @MockBean
    private CountryDao countryDao;

    @Before
    public void init() {
        CountryDTOMapper countryDTOMapper = new CountryDTOMapper(modelMapper);
        countryService = new CountryServiceImpl(countryDao, countryDTOMapper);
    }

    @Test
    public void getCountriesTest() {
        when(countryDao.findCountries()).thenReturn(buildListCountry());
        List<CountryDTO> countries = countryService.getCountries();
        CountryDTO countryDTO = countries.get(0);
        assertFalse(countries.isEmpty());
        assertNotNull(countryDTO.getName());
        assertNotNull(countryDTO.getIso3166());
    }

    private List<Country> buildListCountry() {
        List<Country> countries = new ArrayList<>();
        countries.add(new Country("RU", "Russian Federation"));
        countries.add(new Country("IN", "India"));
        return countries;
    }
}
