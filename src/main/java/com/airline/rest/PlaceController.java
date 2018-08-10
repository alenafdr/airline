package com.airline.rest;

import com.airline.model.dto.PlaceDTO;
import com.airline.service.OrderService;
import com.airline.service.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController()
@RequestMapping(value = "/api/places/")
public class PlaceController {

    private PlaceService placeService;

    @Autowired
    public PlaceController(PlaceService placeService) {
        this.placeService = placeService;
    }

    @GetMapping(value = "/{orderId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<String>> getOccupyPlaces(@PathVariable("orderId") Long orderId){
        return new ResponseEntity<>(placeService.getOccupyPlaces(orderId), HttpStatus.OK);
    }

    @PostMapping(value = "",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<PlaceDTO> registratedPlace(@RequestBody @Validated PlaceDTO placeDTO,
                                                     HttpServletRequest request){
        String login = (String) request.getSession().getAttribute("login");
        return new ResponseEntity<>(placeService.registration(placeDTO, login), HttpStatus.OK);
    }
}
