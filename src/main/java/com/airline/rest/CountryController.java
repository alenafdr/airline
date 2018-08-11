package com.airline.rest;

import com.airline.model.dto.CountryDTO;
import com.airline.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Класс используется для обработки входящих запросов по адресу "/api/countries/", имеет метод для обработки
 * GET запроса
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/api/countries/")
public class CountryController {

    private CountryService countryService;

    @Autowired
    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    /**
     * Метод обрабатывает GET запрос по адресу "/api/countries/", отправляет запрос {@link CountryService} и
     * возвращает список объектов {@link CountryDTO}. Список может быть пустым, но не будет null.
     * @return {@link ResponseEntity<List<CountryDTO>>}
     */

    @GetMapping(value = "",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<CountryDTO>> getCountries() {
        return new ResponseEntity<>(countryService.getCountries(), HttpStatus.OK);
    }
}
