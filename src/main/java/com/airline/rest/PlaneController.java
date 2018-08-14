package com.airline.rest;

import com.airline.model.dto.PlaneDTO;
import com.airline.service.impl.PlaneServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "api/planes")
@Secured(value = "ROLE_ADMIN")
public class PlaneController {

    private PlaneServiceImpl planeService;

    @Autowired
    public PlaneController(PlaneServiceImpl planeService) {
        this.planeService = planeService;
    }

    @GetMapping(value = "",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<PlaneDTO>> getPlanes() {
        return new ResponseEntity<>(planeService.getPlanes(), HttpStatus.OK);
    }
}
