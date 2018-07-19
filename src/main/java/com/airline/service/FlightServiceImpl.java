package com.airline.service;

import com.airline.dao.FlightDao;
import com.airline.model.Flight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class FlightServiceImpl implements FlightService {

    private FlightDao flightDao;

    @Autowired
    public FlightServiceImpl(FlightDao flightDao) {
        this.flightDao = flightDao;
    }

    @Override
    public List<Flight> listByParameters(Flight flight) {
        return flightDao.listByParameters(flight);
    }

    @Override
    public void save(Flight flight) {
        flightDao.save(flight);
    }

    @Override
    public void update(Flight flight) {
        flightDao.update(flight);
    }

    @Override
    public void delete(Long id) {
        flightDao.delete(id);
    }

    @Override
    public Flight getById(Long id) {
        return flightDao.findOne(id);
    }
}
