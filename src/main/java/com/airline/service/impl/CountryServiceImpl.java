package com.airline.service.impl;

import com.airline.dao.CountryDao;
import com.airline.dto.mapper.CountryDTOMapper;
import com.airline.model.dto.CountryDTO;
import com.airline.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Сервис используется для связи между слоями контроллера и DAO,
 * а так же для маппинга объекта {@link com.airline.model.Country} в объект {@link CountryDTO}
 */
@Service
public class CountryServiceImpl implements CountryService {
    private CountryDao countryDao;
    private CountryDTOMapper countryDTOMapper;

    @Autowired
    public CountryServiceImpl(CountryDao countryDao, CountryDTOMapper countryDTOMapper) {
        this.countryDao = countryDao;
        this.countryDTOMapper = countryDTOMapper;
    }

    /**
     * Метод получает из БД список объектов {@link com.airline.model.Country}, маппит их в список объектов
     * {@link CountryDTO} и возвращает контроллеру
     *
     * @return список {@link CountryDTO}
     */

    @Override
    public List<CountryDTO> getCountries() {
        return countryDao.findCountries()
                .stream()
                .map(country -> countryDTOMapper.convertToDTO(country))
                .collect(Collectors.toList());
    }
}
