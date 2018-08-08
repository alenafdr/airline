package com.airline.rest;

import com.airline.model.dto.PlaceDTO;
import com.airline.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController()
@RequestMapping(value = "/api/places/")
public class PlaceController {

    private OrderService orderService;

    @Autowired
    public PlaceController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping(value = "/{orderId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<String>> getOccupyPlaces(@PathVariable("orderId") Long orderId){
        return new ResponseEntity<>(orderService.getOccupyPlaces(orderId), HttpStatus.OK);
    }

    @PostMapping(value = "",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<PlaceDTO> registratedPlace(@RequestBody PlaceDTO placeDTO,
                                                     HttpServletRequest request){
        String login = (String) request.getSession().getAttribute("login");
        return new ResponseEntity<>(orderService.registration(placeDTO, login), HttpStatus.OK);
    }
}
