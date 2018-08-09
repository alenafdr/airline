package com.airline.dto.mapper;

import com.airline.model.Country;
import com.airline.model.dto.CountryDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CountryDTOMapper {

    private ModelMapper modelMapper;

    @Autowired
    public CountryDTOMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public CountryDTO convertToDTO(Country country) {
        CountryDTO countryDTO = modelMapper.map(country, CountryDTO.class);
        countryDTO.setIso3166(country.getIso());
        return countryDTO;
    }

    public Country convertToEntity(CountryDTO countryDTO) {
        Country country = modelMapper.map(countryDTO, Country.class);
        country.setIso(countryDTO.getIso3166());
        return country;
    }
}
