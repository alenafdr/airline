package com.airline.rest;

import com.airline.model.dto.FlightDTO;
import com.airline.model.dto.Schedule;
import com.airline.service.FlightService;
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

    public FlightController() {
    }

    @Autowired
    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }


    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<FlightDTO>> list(@RequestParam("fromTown") String fromTown,
                                                @RequestParam("toTown") String toTown,
                                                @RequestParam("flightName") String flightName,
                                                @RequestParam("planeName") String planeName,
                                                @RequestParam("fromDate") String fromDate,
                                                @RequestParam("toDate") String toDate) {
        FlightDTO flightDTO = new FlightDTO();
        flightDTO.setFromTown(fromTown);
        flightDTO.setToTown(toTown);
        flightDTO.setFlightName(flightName);
        flightDTO.setPlaneName(planeName);
        try {
            Schedule schedule = new Schedule();
            flightDTO.setSchedule(schedule);
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            flightDTO.getSchedule().setFromDate(format.parse(fromDate));
            flightDTO.getSchedule().setFromDate(format.parse(toDate));
        } catch (ParseException pe){
            logger.error(pe.toString());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(flightService.listByParameters(flightDTO), HttpStatus.OK);
    }

    @PostMapping(value = "",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<FlightDTO> create(@RequestBody FlightDTO flightDTO) {

        if(flightDTO == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(flightService.save(flightDTO), HttpStatus.OK);
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<FlightDTO> read(@PathVariable("id") Long id) {
        FlightDTO flightDTO = flightService.getById(id);
        if(flightDTO == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(flightDTO, HttpStatus.OK);
    }

    @PutMapping(value = "{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<FlightDTO> update(@PathVariable("id") Long id,
                                         @RequestBody FlightDTO flightDTO) {
        if(flightDTO == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        flightDTO.setId(id);
        flightService.update(flightDTO);

        return new ResponseEntity<>(flightDTO, HttpStatus.OK);
    }

    @DeleteMapping(value = "{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<FlightDTO> delete(@PathVariable("id") Long id) {
        FlightDTO flightDTO = flightService.getById(id);
        if(flightDTO == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        flightService.delete(id);
        return new ResponseEntity<>(flightDTO, HttpStatus.OK);
    }


}