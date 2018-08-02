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
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;


@RestController()
@RequestMapping(value = "/api/flights/")
public class FlightController {

    private static final Logger logger = LoggerFactory.getLogger(FlightController.class);

    private FlightService flightService;

    @Autowired
    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }


    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 400, message = "Error in the request"),
            @ApiResponse(code = 404, message = "Resource is not found")
    })
    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<FlightDTO>> list(@RequestParam(name = "fromTown", required = false) String fromTown,
                                                @RequestParam(name = "toTown", required = false) String toTown,
                                                @RequestParam(name = "flightName", required = false) String flightName,
                                                @RequestParam(name = "planeName", required = false) String planeName,
                                                @RequestParam(name = "fromDate", required = false) String fromDate,
                                                @RequestParam(name = "toDate", required = false) String toDate) {
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
            logger.error(pe.toString());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(flightService.listByParameters(flightDTO), HttpStatus.OK);
    }

    @PostMapping(value = "",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<FlightDTO> create(@RequestBody FlightDTO flightDTO) {

        if (flightDTO == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(flightService.save(flightDTO), HttpStatus.OK);
    }


    @GetMapping(value = "{id}")
    public ResponseEntity<FlightDTO> read(@PathVariable("id") Long id) {
        FlightDTO flightDTO = flightService.getById(id);
        if (flightDTO == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(flightDTO, HttpStatus.OK);
    }

    @PutMapping(value = "{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<FlightDTO> update(@PathVariable("id") Long id,
                                            @RequestBody FlightDTO flightDTO) {
        if (flightDTO == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        flightDTO.setId(id);
        flightService.update(flightDTO);

        return new ResponseEntity<>(flightDTO, HttpStatus.OK);
    }

    @DeleteMapping(value = "{id}",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        FlightDTO flightDTO = flightService.getById(id);
        if (flightDTO == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        flightService.delete(id);
        return new ResponseEntity<>("{}", HttpStatus.OK);
    }


}
