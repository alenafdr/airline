package com.airline.dto.mapper;

import com.airline.model.ClassType;
import com.airline.model.Country;
import com.airline.model.Price;
import com.airline.model.Ticket;
import com.airline.model.dto.TicketDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TicketDTOMapper {
    private ModelMapper modelMapper;

    @Autowired
    public TicketDTOMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public TicketDTO convertToDTO(Ticket ticket) {
        TicketDTO ticketDTO = modelMapper.map(ticket, TicketDTO.class);
        ticketDTO.setTicket(ticket.getId());
        ticketDTO.setClassType(ticket.getClassType().getName());
        return ticketDTO;
    }

    public Ticket convertToEntity(TicketDTO ticketDTO,
                                  Country country,
                                  ClassType classType,
                                  Price price) {
        Ticket ticket = modelMapper.map(ticketDTO, Ticket.class);
        ticket.setId(ticketDTO.getTicket());
        ticket.setNationality(country);
        ticket.setClassType(classType);
        price.setClassType(classType);
        ticket.setPrice(price);
        return ticket;
    }
}
