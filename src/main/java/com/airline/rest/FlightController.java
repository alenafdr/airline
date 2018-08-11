package com.airline.rest;

import com.airline.model.dto.FlightDTO;
import com.airline.model.dto.Schedule;
import com.airline.service.FlightService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Класс используется для обработки входящих запросов по адресу "/api/flights/", имеет методы для обработки
 * GET ""
 * POST ""
 * GET "/{id}"
 * PUT "/{id}"
 * DELETE "/{id}"
 * PUT "/{id}/approve"
 */

@CrossOrigin
@RestController()
@RequestMapping(value = "/api/flights/")
public class FlightController {

    private static final Logger logger = LoggerFactory.getLogger(FlightController.class);

    private FlightService flightService;

    @Autowired
    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    /**
     * Метод обрабатывает GET запрос по адресу "/api/flights/", принимает на вход список параметров, все из которых
     * необязательные, формирует из них объект {@link FlightDTO}. Если в атрибутах сессии указан userType client, то
     * дополнительным параметром устаналивается значение approved(true), чтобы клиент видел только утвержденные рейсы.
     *
     * @param fromTown   город
     * @param toTown     город
     * @param flightName название рейса
     * @param planeName  название самолета
     * @param fromDate   дата начала выполнения полетов на рейсе
     * @param toDate     дата окончания выполнения полетов на рейсе
     * @param request    используется для получения атрибутов сессии
     * @return {@link ResponseEntity<List<FlightDTO>>}
     */

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 400, message = "Error in the request"),
            @ApiResponse(code = 404, message = "Resource is not found")
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<FlightDTO>> list(@RequestParam(name = "fromTown", required = false) String fromTown,
                                                @RequestParam(name = "toTown", required = false) String toTown,
                                                @RequestParam(name = "flightName", required = false) String flightName,
                                                @RequestParam(name = "planeName", required = false) String planeName,
                                                @RequestParam(name = "fromDate", required = false) String fromDate,
                                                @RequestParam(name = "toDate", required = false) String toDate,
                                                HttpServletRequest request) {
        FlightDTO flightDTO = new FlightDTO();
        flightDTO.setFromTown(fromTown);
        flightDTO.setToTown(toTown);
        flightDTO.setFlightName(flightName);
        flightDTO.setPlaneName(planeName);

        try {
            Schedule schedule = new Schedule();
            flightDTO.setSchedule(schedule);
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            if (fromDate != null) flightDTO.getSchedule().setFromDate(format.parse(fromDate));
            if (toDate != null) flightDTO.getSchedule().setFromDate(format.parse(toDate));
        } catch (ParseException pe) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if ("client".equals(request.getSession().getAttribute("userType"))) {
            flightDTO.setApproved(true);
        }

        return new ResponseEntity<>(flightService.listByParameters(flightDTO), HttpStatus.OK);
    }

    /**
     * Метод обрабатывает POST запрос по адресу "/api/flights/", принимает на вход {@link FlightDTO} и отправляет
     * на дальнейшую обработку. Объект проходит валидацию, которая настроена аннотациями в объекте {@link FlightDTO}
     *
     * @param flightDTO
     * @return {@link ResponseEntity<List<FlightDTO>>}
     */

    @PostMapping(value = "",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<FlightDTO> create(@RequestBody @Validated FlightDTO flightDTO) {
        return new ResponseEntity<>(flightService.save(flightDTO), HttpStatus.OK);
    }


    /**
     * Метод обрабатывает GET запрос по адресу "/api/flights/{id}", принимает на вход параметр id, делает запрос в
     * сервис и возвращает необходимый объект
     *
     * @param id
     * @return {@link ResponseEntity<List<FlightDTO>>}
     */

    @GetMapping(value = "{id}")
    public ResponseEntity<FlightDTO> read(@PathVariable("id") Long id) {
        return new ResponseEntity<>(flightService.getById(id), HttpStatus.OK);
    }

    /**
     * Метод обрабатывает PUT запрос по адресу "/api/flights/{id}", принимает на вход параметр id и объект
     * {@link FlightDTO}. Объект проходит валидацию, которая настроена аннотациями в объекте {@link FlightDTO}.
     * Далле объекту {@link FlightDTO} устанавливается необходимый id и объект передается на дальнейшую обработку.
     *
     * @param id
     * @param flightDTO
     * @return {@link ResponseEntity<List<FlightDTO>>}
     */

    @PutMapping(value = "{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<FlightDTO> update(@PathVariable("id") Long id,
                                            @RequestBody @Validated FlightDTO flightDTO) {
        flightDTO.setId(id);
        flightService.update(flightDTO);
        return new ResponseEntity<>(flightDTO, HttpStatus.OK);
    }

    /**
     * Метод обрабатывает DELETE запрос по адресу "/api/flights/{id}", принимает на вход параметр id, который передает
     * в сервис на обработку
     *
     * @param id
     * @return {@link ResponseEntity<String>} пустой JSON
     */

    @DeleteMapping(value = "{id}",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        flightService.delete(id);
        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

    /**
     * Метод обрабатывает PUT запрос по адресу "/api/flights/{id}/approve", принимает на вход параметр id,
     * который передает в сервис на обработку и возвращает необходимый объект
     *
     * @param id
     * @return {@link ResponseEntity<List<FlightDTO>>}
     */

    @PutMapping(value = "/{id}/approve",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<FlightDTO> approve(@PathVariable("id") Long id) {
        return new ResponseEntity<>(flightService.approved(id), HttpStatus.OK);
    }

}
