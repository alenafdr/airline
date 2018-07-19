package com.airline.service;

import com.airline.model.Flight;

import java.util.List;

public interface FlightService {
    List<Flight> listByParameters(Flight flight);
    void save(Flight flight);
    void update(Flight flight);
    void delete(Long id);
    Flight getById(Long id);
}
