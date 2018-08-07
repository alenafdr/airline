package com.airline.service;

import com.airline.model.dto.FlightDTO;

import java.util.List;

public interface FlightService {
    List<FlightDTO> listByParameters(FlightDTO flightDTO);

    FlightDTO save(FlightDTO flightDTO);

    FlightDTO update(FlightDTO flightDTO);

    void delete(Long id);

    FlightDTO getById(Long id);
}
