package com.airline.dto.mapper;

import com.airline.model.*;
import com.airline.model.dto.FlightDTO;
import com.airline.model.dto.Schedule;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FlightDTOMapper {

    private ModelMapper modelMapper;

    @Autowired
    public FlightDTOMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public FlightDTO convertToDTO(Flight flight) {
        FlightDTO flightDTO = modelMapper.map(flight, FlightDTO.class);
        flightDTO.setSchedule(new Schedule());
        flightDTO.getSchedule().setFromDate(flight.getFromDate());
        flightDTO.getSchedule().setToDate(flight.getToDate());
        flightDTO.getSchedule().setPeriods(flight.getPeriods()
                .stream()
                .map(Period::getValue)
                .collect(Collectors.toList()));

        flightDTO.setPriceBusiness(flight.getPriceByClassTypeName(ClassTypeEnum.BUSINESS.name()).getPrice());
        flightDTO.setPriceEconomy(flight.getPriceByClassTypeName(ClassTypeEnum.ECONOMY.name()).getPrice());
        flightDTO.setDates(flight.getDepartures()
                .stream()
                .map(Departure::getDate)
                .collect(Collectors.toList()));

        return flightDTO;
    }

    public Flight convertToEntity(FlightDTO flightDTO,
                                  Plane plane,
                                  List<Price> prices,
                                  List<Period> periods,
                                  List<Departure> departures) {
        Flight flight = modelMapper.map(flightDTO, Flight.class);

        flight.setPlane(plane);
        flight.setFromDate(flightDTO.getSchedule().getFromDate());
        flight.setToDate(flightDTO.getSchedule().getToDate());
        flight.setPeriods(periods);
        flight.setDepartures(departures);
        flight.setPrices(prices);

        return flight;
    }
}
