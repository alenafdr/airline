package com.airline.dto.mapper;

import com.airline.model.*;
import com.airline.model.dto.OrderDTO;
import com.airline.utils.Utils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

public class OrderDTOMapper {

    private ModelMapper modelMapper;
    private TicketDTOMapper ticketDTOMapper;

    @Autowired
    public OrderDTOMapper(ModelMapper modelMapper, TicketDTOMapper ticketDTOMapper) {
        this.modelMapper = modelMapper;
        this.ticketDTOMapper = ticketDTOMapper;
    }

    public OrderDTO convertToDTO(Order order) {
        OrderDTO orderDTO = modelMapper.map(order, OrderDTO.class);
        Flight flight = order.getDeparture().getFlight();
        orderDTO.setFlightId(flight.getId());
        orderDTO.setFromTown(flight.getFromTown());
        orderDTO.setToTown(flight.getToTown());
        orderDTO.setFlightName(flight.getFlightName());
        orderDTO.setPlaneName(order.getDeparture().getFlight().getPlane().getName());
        orderDTO.setDate(order.getDeparture().getDate());
        orderDTO.setStart(flight.getStart());
        orderDTO.setDuration(flight.getDuration());
        orderDTO.setPassengers(order.getTickets()
                .stream()
                .map(ticket -> ticketDTOMapper.convertToDTO(ticket))
                .collect(Collectors.toList()));
        orderDTO.setTotalPrice(Utils.calculateTotalPrice(order.getTickets()));
        return orderDTO;
    }

    public Order convertToEntity(OrderDTO orderDTO,
                                 UserClient userClient,
                                 Departure departure,
                                 List<Ticket> tickets) {
        Order order = new Order();
        order.setId(orderDTO.getOrderId());
        order.setDeparture(departure);
        order.setTickets(tickets);
        order.setUserClient(userClient);
        return order;
    }


}
