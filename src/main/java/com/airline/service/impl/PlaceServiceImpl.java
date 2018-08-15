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

/**
 * Класс служит для связи между слоями контроллера и БД. Имеет методы для получения списка свободных мест  для
 * определенного заказа, а так же метод для регистрации пассажира
 */
@Service
public class PlaceServiceImpl implements PlaceService {

    private TicketDao ticketDao;
    private OrderDao orderDao;

    @Autowired
    public PlaceServiceImpl(TicketDao ticketDao, OrderDao orderDao) {
        this.ticketDao = ticketDao;
        this.orderDao = orderDao;
    }

    /**
     * По orderId метод пытается найти оформленный заказ в БД. Если такой заказ был оформлен, то ищет занятые места
     * для объекта {@link Departure}, связанного с этим заказом.
     * По типу самолета, привязанного к текущему {@link Departure}, формирует список всех возможных мест для этого
     * самолета и возвращает разницу между списком доступных мест для самолета и списком занятых мест для текущего
     * {@link Departure}
     *
     * @param orderId id заказа
     * @return {@link List<String>} список свободных мест
     * @throws {@link OrderNotFoundException}
     */

    @Override
    public List<String> getFreePlaces(Long orderId) {
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

    /**
     * Метод принимает объект для регистрации, котором указана вся необходимая информация, а так же логин пользоватлея,
     * который пытается зарегистрировать объект.
     * Метод получает из БД ранее сохраненный тикет по параметру {@link PlaceDTO#getTicket()}, который содержит id.
     * Проверяет, принадлежит ли этот билет с пользователем, который пытается его зарегистрировать.
     * По связанному с заказом самолету определяет, есть ли в текущем типе самолета место, которое пытаются
     * зарегистрировать и находится ли это место в классе, для которого был куплен билет. Если все было указано верно,
     * сохраняет билет в БД и возвращает текущее значение
     *
     * @param placeDTO объект с информацией о регистрации
     * @param login    логин пользоватлея, который пытается зарегистрироваться
     * @return {@link PlaceDTO}
     * @throws {@link TicketNotFoundException} не найден билет по id или переданный логин не связян с найденным билетом
     * @throws {@link WrongPlaceException} неверно указано место (такого места нет в самолете или место не находится
     *                в классе, для которого куплен билет)
     */

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
            if (getFreePlaces(ticket.getOrder().getId()).contains(placeDTO.getPlace())) {
                ticket.setPlace(place);
                ticketDao.updatePlaceInTicket(ticket);
            } else {
                throw new WrongPlaceException("Place " + placeDTO.getPlace() + " is occupy");
            }

        } else {
            throw new WrongPlaceException("Wrong place, you must register place in "
                    + ticket.getClassType().getName() + " class");
        }
        placeDTO.setFirstName(ticket.getFirstName());
        placeDTO.setLastName(ticket.getLastName());
        return placeDTO;
    }

    /**
     * Метод создает список доступных для самолета мест
     *
     * @param plane тип самолета, для которого нужно получить все доступные места
     * @return {@link List<String>}
     */

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

    /**
     * Метод определяет, принадлежит ли переданное место к нужному классу. Для этого получает место, которое нужно
     * проверить, тип класса, к которому должно принадлежать место и тип самолета.
     *
     * @param plane     тип самолета
     * @param classType тип класса
     * @param place     место
     * @return
     */

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
