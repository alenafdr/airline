package com.airline.service;

import com.airline.model.dto.CountryDTO;

import java.util.List;

public interface CountryService {
    List<CountryDTO> getCountries();
}
