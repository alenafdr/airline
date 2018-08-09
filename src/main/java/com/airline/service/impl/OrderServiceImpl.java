package com.airline.service.impl;


import com.airline.dao.*;
import com.airline.dto.mapper.OrderDTOMapper;
import com.airline.dto.mapper.TicketDTOMapper;
import com.airline.exceptions.*;
import com.airline.model.*;
import com.airline.model.dto.OrderDTO;
import com.airline.model.dto.PlaceDTO;
import com.airline.model.dto.TicketDTO;
import com.airline.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);

    private OrderDao orderDao;
    private TicketDao ticketDao;
    private OrderDTOMapper orderDTOMapper;
    private TicketDTOMapper ticketDTOMapper;
    private DepartureDao departureDao;
    private ClassTypeDao classTypeDao;
    private CountryDao countryDao;
    private PriceDao priceDao;
    private PlaneDao planeDao;
    private ClientDao clientDao;

    @Autowired
    public OrderServiceImpl(OrderDao orderDao,
                            TicketDao ticketDao,
                            OrderDTOMapper orderDTOMapper,
                            TicketDTOMapper ticketDTOMapper,
                            DepartureDao departureDao,
                            ClassTypeDao classTypeDao,
                            CountryDao countryDao,
                            PriceDao priceDao,
                            PlaneDao planeDao,
                            ClientDao clientDao) {
        this.orderDao = orderDao;
        this.ticketDao = ticketDao;
        this.orderDTOMapper = orderDTOMapper;
        this.ticketDTOMapper = ticketDTOMapper;
        this.departureDao = departureDao;
        this.classTypeDao = classTypeDao;
        this.countryDao = countryDao;
        this.priceDao = priceDao;
        this.planeDao = planeDao;
        this.clientDao = clientDao;
    }

    //classType can be null
    //nationality can be null

    @Override
    public OrderDTO saveOrder(OrderDTO orderDTO, String login) {
        String loginLC = login.toLowerCase();
        UserClient userClient = clientDao.findByLogin(loginLC)
                .orElseThrow(() -> new LoginNotFoundException("Not found login " + login));

        Order order;
        Long flightId = orderDTO.getFlightId();
        Departure departure = departureDao.findDepartureByFlightIdAndDate(new Departure(orderDTO.getDate(), new Flight(orderDTO.getFlightId())))
                .orElseThrow(() -> new DepartureNotFoundException("Not found departure for flight "
                        + flightId + " with date " + orderDTO.getDate()));

        List<Ticket> tickets = new ArrayList<>();
        for (TicketDTO ticketDTO : orderDTO.getPassengers()) {
            Country country = null;
            ClassType classType;
            Price price;
            String iso = ticketDTO.getNationality();
            if (iso != null) {
                country = countryDao.findCountryByIso(iso)
                        .orElseThrow(() -> new NationalityNotFoundException("Not found nationality with code " + iso));
            }

            if (ticketDTO.getClassType() != null) {
                classType = classTypeDao.findClassTypeByName(ticketDTO.getClassType())
                        .orElseThrow(()->new ClassTypeNotFoundException("Not found class with name " + ticketDTO.getClassType()));
            } else {
                classType = classTypeDao.findClassTypeByName(ClassTypeEnum.ECONOMY.name()).get();
            }

            price = priceDao.findPricesByFlightId(departure.getFlight().getId())
                    .stream()
                    .filter(price1 -> price1.getClassType().equals(classType))
                    .findAny()
                    .orElseThrow(() -> new PriceNotFoundException("Not found price for flight "
                            + orderDTO.getFlightName()
                            + " in class " + classType.getName()));

            tickets.add(ticketDTOMapper.convertToEntity(ticketDTO, country, classType, price));
        }

        order = orderDTOMapper.convertToEntity(orderDTO, userClient, departure, tickets);
        Long orderId = orderDao.saveOrder(order);
        return orderDTOMapper.convertToDTO(orderDao.findOrderById(orderId).get());
    }

    @Override
    public List<OrderDTO> getOrdersByParameters(Map<String, String> parameters) throws Exception {
        Plane plane = null;
        String planeName = parameters.get(ParametersEnum.PLANE_NAME);
        if (planeName != null) {
            plane = planeDao.findPlaneByName(planeName)
                    .orElseThrow(() -> new PlaneNotFoundException("Not found plane with name " + planeName));
        }

        Flight flight = new Flight();
        flight.setFromTown(parameters.get(ParametersEnum.FROM_TOWN.getValue()));
        flight.setToTown(parameters.get(ParametersEnum.TO_TOWN.getValue()));
        flight.setFlightName(parameters.get(ParametersEnum.FLIGHT_NAME.getValue()));
        flight.setPlane(plane);
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String fromDate = parameters.get(ParametersEnum.FROM_DATE.getValue());
        String toDate = parameters.get(ParametersEnum.TO_DATE.getValue());
        if (fromDate != null) flight.setFromDate(format.parse(fromDate));
        if (toDate != null) flight.setToDate(format.parse(toDate));

        Departure departure = new Departure(flight);

        UserClient userClient = null;

        if (parameters.get(ParametersEnum.CLIENT_ID.getValue()) != null) {
            Long id = Long.parseLong(parameters.get(ParametersEnum.CLIENT_ID.getValue()));
            userClient = clientDao.findById(id)
                    .orElseThrow(() -> new UserNotFoundException("Not found user with id " + id));
        }

        Order order = new Order();
        order.setDeparture(departure);
        order.setUserClient(userClient);

        return orderDao.findOrdersByParameters(order)
                .stream()
                .map(order1 -> orderDTOMapper.convertToDTO(order1))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getOccupyPlaces(Long orderId) {
        Order order = orderDao.findOrderById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Not found order with id " + orderId));
        List<String> busyPlaces = ticketDao.findOccupyPlaces(order.getDeparture().getId())
                .stream()
                .map(ticket -> ticket.getPlace())
                .collect(Collectors.toList());

        List<String> freePlaces = getPlacesByPlane(order.getDeparture().getFlight().getPlane());
        freePlaces.removeAll(busyPlaces);
        return freePlaces;
    }

    @Override
    public PlaceDTO registration(PlaceDTO placeDTO, String login) {
        Ticket ticket = ticketDao.findTicketById(placeDTO.getTicket())
                .orElseThrow(() -> new TicketNotFoundException("Not found ticket with id " + placeDTO.getTicket()));

        if (!login.equals(ticket.getOrder().getUserClient().getLogin())) {
            throw new TicketNotFoundException("Not found ticket " + placeDTO.getTicket()
                    + " for user with login " + login);
        }

        Plane plane = ticket.getOrder().getDeparture().getFlight().getPlane();
        String place = placeDTO.getPlace();
        if (!getPlacesByPlane(plane).contains(place)) {
            throw new WrongPlaceException("Wrong place, there is no place " + place + " in the plane");
        }

        if (isPlaceInRightClass(plane, ticket.getClassType(), place)) {
            ticket.setPlace(place);
            ticketDao.updatePlaceInTicket(ticket);
        } else {
            throw new WrongPlaceException("Wrong place, you must register place in "
                    + ticket.getClassType() + " class");
        }
        return placeDTO;
    }

    public List<String> getPlacesByPlane(Plane plane) {
        int rows = plane.getEconomyRow() + plane.getBusinessRow();
        int rowsInBusiness = plane.getBusinessRow();
        int placesInRowBusiness = plane.getPlacesInBusinessRow();
        int placesInRowEconomy = plane.getPlacesInEconomyRow();
        List<String> listPlaces = new ArrayList<>();
        int currRow = 0;
        for (; currRow < rowsInBusiness; currRow++) {
            for (int j = 0; j < placesInRowBusiness; j++) {
                char place = (char) (j + 65);
                listPlaces.add(String.valueOf(currRow + 1) + Character.toString(place));
            }
        }
        for (; currRow < rows; currRow++) {
            for (int j = 0; j < placesInRowEconomy; j++) {
                char place = (char) (j + 65);
                listPlaces.add(String.valueOf(currRow + 1) + Character.toString(place));
            }
        }
        return listPlaces;
    }

    public boolean isPlaceInRightClass(Plane plane, ClassType classType, String place) {
        int row = Integer.valueOf(place.replaceAll("[^-?0-9]+", ""));
        if (ClassTypeEnum.ECONOMY.name().equals(classType.getName()) && isPlaceInEconomy(plane, row)) {
            return true;
        } else if (ClassTypeEnum.BUSINESS.name().equals(classType.getName()) && isPlaceInBusiness(plane, row)) {
            return true;
        }

        return false;
    }

    private boolean isPlaceInBusiness(Plane plane, int row) {
        if (row <= plane.getBusinessRow()) return true;
        return false;
    }

    private boolean isPlaceInEconomy(Plane plane, int row) {
        if (row > plane.getBusinessRow()) return true;
        return false;
    }

}
