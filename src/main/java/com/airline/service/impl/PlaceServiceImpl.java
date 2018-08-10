package com.airline.service.impl;

import com.airline.dao.OrderDao;
import com.airline.dao.TicketDao;
import com.airline.exceptions.OrderNotFoundException;
import com.airline.exceptions.TicketNotFoundException;
import com.airline.exceptions.WrongPlaceException;
import com.airline.model.*;
import com.airline.model.dto.PlaceDTO;
import com.airline.service.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlaceServiceImpl implements PlaceService {

    private TicketDao ticketDao;
    private OrderDao orderDao;

    @Autowired
    public PlaceServiceImpl(TicketDao ticketDao, OrderDao orderDao) {
        this.ticketDao = ticketDao;
        this.orderDao = orderDao;
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
