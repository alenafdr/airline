package com.airline.service;

import com.airline.dao.*;
import com.airline.dto.mapper.OrderDTOMapper;
import com.airline.dto.mapper.TicketDTOMapper;
import com.airline.exceptions.TicketNotFoundException;
import com.airline.exceptions.WrongPlaceException;
import com.airline.model.*;
import com.airline.model.dto.OrderDTO;
import com.airline.model.dto.PlaceDTO;
import com.airline.model.dto.TicketDTO;
import com.airline.service.impl.OrderServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.sql.Time;
import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ModelMapper.class)
public class OrderServiceTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceTest.class);

    @Autowired
    private ModelMapper modelMapper;

    @MockBean
    private OrderDao orderDao;

    @MockBean
    private TicketDao ticketDao;

    @MockBean
    private DepartureDao departureDao;

    @MockBean
    private ClassTypeDao classTypeDao;

    @MockBean
    private CountryDao countryDao;

    @MockBean
    private PriceDao priceDao;

    @MockBean
    private PlaneDao planeDao;

    @MockBean
    private ClientDao clientDao;

    private OrderDTOMapper orderDTOMapper;

    private TicketDTOMapper ticketDTOMapper;

    private OrderServiceImpl orderService;

    @Before
    public void init() {
        ticketDTOMapper = new TicketDTOMapper(modelMapper);
        orderDTOMapper = new OrderDTOMapper(modelMapper, ticketDTOMapper);
        orderService = new OrderServiceImpl(orderDao, departureDao,
                classTypeDao, countryDao,
                priceDao, planeDao,
                clientDao, orderDTOMapper,
                ticketDTOMapper);
    }

    @Test
    public void saveOrderTest() {
        when(departureDao.findDepartureById(anyLong())).thenReturn(buildDeparture());
        when(countryDao.findCountryByIso("RU")).thenReturn(Optional.ofNullable(new Country("RU", "Russian federation")));
        when(classTypeDao.findClassTypeByName(any())).thenReturn(Optional.ofNullable(new ClassType(1L, ClassTypeEnum.ECONOMY.name())));
        when(priceDao.findPricesByFlightId(anyLong())).thenReturn(buildListPrices());
        when(orderDao.saveOrder(any(Order.class))).thenReturn(1L);
        when(orderDao.findOrderById(anyLong())).thenReturn(Optional.ofNullable(buildOrder()));

        OrderDTO orderDTO = orderService.saveOrder(buildOrderDTO(), "testLogin");
        assertNotNull(orderDTO.getOrderId());
        assertNotNull(orderDTO.getFlightId());
        assertNotNull(orderDTO.getFromTown());
        assertNotNull(orderDTO.getToTown());
        assertNotNull(orderDTO.getFlightName());
        assertNotNull(orderDTO.getPlaneName());
        assertNotNull(orderDTO.getDate());
        assertNotNull(orderDTO.getStart());
        assertNotNull(orderDTO.getDuration());
        assertTrue(orderDTO.getPassengers().size() > 0);
        assertNotNull(orderDTO.getTotalPrice());
    }

    @Test
    public void getOrdersByParametersTest() throws Exception {
        when(planeDao.findPlaneByName(any())).thenReturn(Optional.ofNullable(buildPlane()));
        when(clientDao.findById(anyLong())).thenReturn(Optional.ofNullable(new UserClient(1L)));
        when(orderDao.findOrdersByParameters(any(Order.class))).thenReturn(buildListOrders());

        assertTrue(orderService.getOrdersByParameters(buildParameters()).size() > 0);
    }

    //tests DTO mappers

    @Test
    public void dtoToOrderTest() {
        List<Ticket> tickets = new ArrayList<>();
        tickets.add(buildSimpleTicket());
        tickets.add(buildSimpleTicket());
        Order order = orderDTOMapper.convertToEntity(buildOrderDTO(),
                new UserClient(15L),
                new Departure(new Date()),
                tickets);
        assertNotNull(order.getId());
        assertNotNull(order.getTickets());
        assertNotNull(order.getDeparture());
        assertNotNull(order.getUserClient());
    }

    @Test
    public void orderToDTOTest() {
        OrderDTO orderDTO = orderDTOMapper.convertToDTO(buildOrder());
        assertNotNull(orderDTO.getOrderId());
        assertNotNull(orderDTO.getFlightId());
        assertNotNull(orderDTO.getFromTown());
        assertNotNull(orderDTO.getToTown());
        assertNotNull(orderDTO.getFlightName());
        assertNotNull(orderDTO.getDate());
        assertNotNull(orderDTO.getStart());
        assertNotNull(orderDTO.getDuration());
        assertTrue(orderDTO.getPassengers().size() > 0);
    }

    @Test
    public void dtoToTicketTest() {
        ClassType classType = new ClassType(1L, ClassTypeEnum.BUSINESS.name());
        Ticket ticket = ticketDTOMapper.convertToEntity(buildTicketDTO(),
                new Country("RU", "RussianFederation"),
                new ClassType(1L, ClassTypeEnum.BUSINESS.name()),
                new Price(new BigDecimal("5000.00")));
        assertNotNull(ticket.getId());
        assertNotNull(ticket.getFirstName());
        assertNotNull(ticket.getLastName());
        assertNotNull(ticket.getPassport());
        assertNotNull(ticket.getClassType());
        assertNotNull(ticket.getPrice());
    }

    @Test
    public void ticketToDTOTest() {
        TicketDTO ticketDTO = ticketDTOMapper.convertToDTO(buildSimpleTicket());
        assertNotNull(ticketDTO.getTicket());
        assertNotNull(ticketDTO.getFirstName());
        assertNotNull(ticketDTO.getLastName());
        assertNotNull(ticketDTO.getPassport());
        assertNotNull(ticketDTO.getClassType());
        assertNotNull(ticketDTO.getPrice());
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

    private OrderDTO buildOrderDTO() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderId(1L);
        orderDTO.setFlightId(1L);
        orderDTO.setFromTown("test");
        orderDTO.setToTown("test");
        orderDTO.setFlightName("test");
        orderDTO.setDate(new Date());
        orderDTO.setDuration(Time.valueOf("03:00:00"));

        List<TicketDTO> passengers = new ArrayList<>();
        passengers.add(buildTicketDTO());
        passengers.add(buildTicketDTO());
        orderDTO.setPassengers(passengers);

        orderDTO.setTotalPrice(new BigDecimal("10000.00"));
        return orderDTO;
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

    private TicketDTO buildTicketDTO() {
        TicketDTO ticketDTO = new TicketDTO();
        ticketDTO.setTicket(5L);
        ticketDTO.setFirstName("test");
        ticketDTO.setLastName("test");
        ticketDTO.setClassType(ClassTypeEnum.BUSINESS.name());
        ticketDTO.setPassport("5555 5555555");
        ticketDTO.setNationality("RU");
        ticketDTO.setPrice(new BigDecimal("5000.00"));
        return ticketDTO;
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

    private Plane buildPlane() {
        Plane plane = new Plane();
        plane.setName("test plane");
        plane.setBusinessRow(2);
        plane.setEconomyRow(2);
        plane.setPlacesInBusinessRow(2);
        plane.setPlacesInEconomyRow(2);
        return plane;
    }

    private List<Price> buildListPrices() {
        List<Price> prices = new ArrayList<>();
        prices.add(new Price(new ClassType(1L, ClassTypeEnum.ECONOMY.name()), new BigDecimal("2000")));
        prices.add(new Price(new ClassType(1L, ClassTypeEnum.BUSINESS.name()), new BigDecimal("4000")));
        return prices;
    }

    private Optional<Departure> buildDeparture() {
        Departure departure = new Departure();
        departure.setId(1L);
        departure.setFlight(new Flight(1L));
        return Optional.ofNullable(departure);
    }

    private Map<String, String> buildParameters() {
        Map<String, String> map = new HashMap<>();
        map.put(ParametersEnum.PLANE_NAME.getValue(), "test plane");
        map.put(ParametersEnum.FROM_TOWN.getValue(), "test");
        map.put(ParametersEnum.TO_TOWN.getValue(), "test");
        map.put(ParametersEnum.FLIGHT_NAME.getValue(), "test");
        map.put(ParametersEnum.FROM_DATE.getValue(), "2018-01-01");
        map.put(ParametersEnum.TO_DATE.getValue(), "2018-08-01");
        map.put(ParametersEnum.CLIENT_ID.getValue(), "1");
        return map;
    }

    public List<Order> buildListOrders() {
        List<Order> orders = new ArrayList<>();
        orders.add(buildOrder());
        orders.add(buildOrder());
        return orders;
    }

}
