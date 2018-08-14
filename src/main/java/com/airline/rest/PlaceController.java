package com.airline.rest;

import com.airline.model.dto.PlaceDTO;
import com.airline.service.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Класс используется для обратки входящих запросов по адресу "/api/places/" имеет методы для обработки запросов
 * GET  "/{orderId}"
 * POST ""
 */

@CrossOrigin
@RestController()
@RequestMapping(value = "/api/places/")
public class PlaceController {

    private PlaceService placeService;

    @Autowired
    public PlaceController(PlaceService placeService) {
        this.placeService = placeService;
    }

    /**
     * Метод обрабатывает GET запрос по адресу "/api/places/{orderId}". Принимает на вход параметр orderId и передает
     * его дальше в сервис для получения списка свободных мест для текущего заказа
     *
     * @param orderId id заказа
     * @return {@link ResponseEntity<List<String>>}
     */

    @GetMapping(value = "/{orderId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<String>> getOccupyPlaces(@PathVariable("orderId") Long orderId) {
        return new ResponseEntity<>(placeService.getOccupyPlaces(orderId), HttpStatus.OK);
    }

    /**
     * Метод обрабатывает POST запрос по адресу "/api/places/". Принимает на вход параметры {@link PlaceDTO}, получает
     * атрибут сессии login и передает в сервис для регистрации. Логин хранится в сессии в нижнем регистре.
     * Объект {@link PlaceDTO} проходит валидацию, настроенную с помощью аннотаций в {@link PlaceDTO}
     *
     * @param placeDTO объект для регистрации
     * @param request  объект для получения атрибутов сессии
     * @return {@link ResponseEntity<PlaceDTO>}
     */

    @Secured(value = "ROLE_USER")
    @PostMapping(value = "",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<PlaceDTO> registratedPlace(@RequestBody @Validated PlaceDTO placeDTO,
                                                     HttpServletRequest request) {
        String login = (String) request.getSession().getAttribute("login");
        return new ResponseEntity<>(placeService.registration(placeDTO, login), HttpStatus.OK);
    }
}
