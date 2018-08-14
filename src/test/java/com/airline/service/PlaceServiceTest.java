package com.airline.service;

import com.airline.dao.OrderDao;
import com.airline.dao.TicketDao;
import com.airline.exceptions.TicketNotFoundException;
import com.airline.exceptions.WrongPlaceException;
import com.airline.model.*;
import com.airline.model.dto.PlaceDTO;
import com.airline.service.impl.PlaceServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ModelMapper.class)
public class PlaceServiceTest {

    @MockBean
    private TicketDao ticketDao;

    @MockBean
    private OrderDao orderDao;

    private PlaceServiceImpl placeService;

    @Before
    public void init(){
        placeService = new PlaceServiceImpl(ticketDao, orderDao);
    }

    @Test
    public void getFreePlacesTest() {
        when(orderDao.findOrderById(anyLong())).thenReturn(Optional.ofNullable(buildOrder()));
        when(ticketDao.findOccupyPlaces(anyLong())).thenReturn(buildBusyTickets());

        assertFalse(placeService.getFreePlaces(1L).contains("1A"));
    }

    @Test
    public void registartionRightTest() {
        doNothing().when(ticketDao).updatePlaceInTicket(any());
        when(ticketDao.findTicketById(anyLong())).thenReturn(Optional.ofNullable(buildFullTicket()));
        PlaceDTO placeDTO = placeService.registration(buildPlaceDTO(), "testLogin");
    }

    @Test(expected = TicketNotFoundException.class)
    public void registartionWrongTicketTest() {
        doNothing().when(ticketDao).updatePlaceInTicket(any());
        when(ticketDao.findTicketById(anyLong())).thenReturn(Optional.ofNullable(null));
        PlaceDTO placeDTO = placeService.registration(buildPlaceDTO(), "testLogin");
    }

    @Test(expected = WrongPlaceException.class)
    public void registartionWrongPlaceTest() {
        doNothing().when(ticketDao).updatePlaceInTicket(any());
        when(ticketDao.findTicketById(anyLong())).thenReturn(Optional.ofNullable(buildFullTicket()));
        PlaceDTO placeDTO = buildPlaceDTO();
        placeDTO.setPlace("3F");
        placeService.registration(placeDTO, "testLogin");
    }

    @Test(expected = WrongPlaceException.class)
    public void registartionWrongClassTest() {
        doNothing().when(ticketDao).updatePlaceInTicket(any());
        when(ticketDao.findTicketById(anyLong())).thenReturn(Optional.ofNullable(buildFullTicket()));
        PlaceDTO placeDTO = buildPlaceDTO();
        placeDTO.setPlace("3A"); //for this ticket place must be between 1-2 rows
        placeService.registration(placeDTO, "testLogin");
    }

    @Test
    public void isPlaceInRightClassTest() {
        Plane plane = buildPlane();
        ClassType classTypeEconomy = new ClassType(1L, ClassTypeEnum.ECONOMY.name());
        ClassType classTypeBusiness = new ClassType(2L, ClassTypeEnum.BUSINESS.name());

        assertTrue(placeService.isPlaceInRightClass(plane, classTypeEconomy, "3A"));
        assertFalse(placeService.isPlaceInRightClass(plane, classTypeBusiness, "4A"));
        assertTrue(placeService.isPlaceInRightClass(plane, classTypeBusiness, "2A"));
        assertFalse(placeService.isPlaceInRightClass(plane, classTypeEconomy, "1A"));
    }

    private Order buildOrder() {
        Order order = new Order();
        order.setId(1L);

        Departure departure = new Departure(1L);
        departure.setDate(new Date());
        departure.setFlight(buildFlight());
        order.setDeparture(departure);

        order.setUserClient(new UserClient(5L, "testLogin"));

        List<Ticket> tickets = new ArrayList<>();
        tickets.add(buildSimpleTicket());
        tickets.add(buildSimpleTicket());
        order.setTickets(tickets);

        return order;
    }

    private Ticket buildSimpleTicket() {
        Ticket ticket = new Ticket();
        ticket.setId(5L);
        ticket.setFirstName("test");
        ticket.setLastName("test");
        ticket.setNationality(new Country("RU", "RussianFederation"));
        ticket.setPassport("555 5555");
        ClassType classType = new ClassType(1L, ClassTypeEnum.BUSINESS.name());
        ticket.setClassType(classType);
        ticket.setPrice(new Price(classType, new BigDecimal("2000.00")));
        ticket.setOrder(new Order(1L));
        return ticket;
    }

    private Flight buildFlight() {
        Flight flight = new Flight();
        flight.setId(1L);
        flight.setFlightName("test");
        flight.setFromTown("test");
        flight.setToTown("test");
        flight.setStart(Time.valueOf("07:30:00"));
        flight.setDuration(Time.valueOf("03:00:00"));
        flight.setPlane(buildPlane());
        return flight;
    }

    private Ticket buildFullTicket() {
        Ticket ticket = buildSimpleTicket();
        ticket.setOrder(buildOrder());
        return ticket;
    }

    private Plane buildPlane() {
        Plane plane = new Plane();
        plane.setName("test plane");
        plane.setBusinessRow(2);
        plane.setEconomyRow(2);
        plane.setPlacesInBusinessRow(2);
        plane.setPlacesInEconomyRow(2);
        return plane;
    }

    private PlaceDTO buildPlaceDTO() {
        PlaceDTO placeDTO = new PlaceDTO();
        placeDTO.setOrderId(1L);
        placeDTO.setPlace("1A");
        placeDTO.setTicket(1L);
        return placeDTO;
    }

    private List<Ticket> buildBusyTickets() {
        List<Ticket> busyTicket = new ArrayList<>();
        Ticket ticket = buildSimpleTicket();
        ticket.setPlace("1A");
        busyTicket.add(ticket);
        return busyTicket;
    }
}
