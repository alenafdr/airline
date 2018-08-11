package com.airline.service.impl;


import com.airline.dao.*;
import com.airline.dto.mapper.OrderDTOMapper;
import com.airline.dto.mapper.TicketDTOMapper;
import com.airline.exceptions.*;
import com.airline.model.*;
import com.airline.model.dto.OrderDTO;
import com.airline.model.dto.TicketDTO;
import com.airline.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Класс используется для обаботки запросов из контроллера, подготовки запросов к отправке в БД,
 * для маппинга результатов в DTO объекты
 */

@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);

    private OrderDao orderDao;
    private DepartureDao departureDao;
    private ClassTypeDao classTypeDao;
    private CountryDao countryDao;
    private PriceDao priceDao;
    private PlaneDao planeDao;
    private ClientDao clientDao;
    private OrderDTOMapper orderDTOMapper;
    private TicketDTOMapper ticketDTOMapper;

    @Autowired
    public OrderServiceImpl(OrderDao orderDao,
                            DepartureDao departureDao,
                            ClassTypeDao classTypeDao,
                            CountryDao countryDao,
                            PriceDao priceDao,
                            PlaneDao planeDao,
                            ClientDao clientDao,
                            OrderDTOMapper orderDTOMapper,
                            TicketDTOMapper ticketDTOMapper) {
        this.orderDao = orderDao;
        this.departureDao = departureDao;
        this.classTypeDao = classTypeDao;
        this.countryDao = countryDao;
        this.priceDao = priceDao;
        this.planeDao = planeDao;
        this.clientDao = clientDao;
        this.orderDTOMapper = orderDTOMapper;
        this.ticketDTOMapper = ticketDTOMapper;
    }

    /**
     * Метод получает объект {@link OrderDTO} и логин отправителя. Проверяет, есть ли такой отправитель в БД.
     * Проверяет, есть ли вылет с такой датой для текущего рейса. Для каждого объекта {@link TicketDTO}
     * создает {@link Ticket} -
     * назначает {@link Price}, {@link ClassType}, {@link Country}.
     * конструирует полноценный объект {@link Order},
     * сохраняет в БД,
     * по возвращаемому id забирает из БД сохраненный {@link Order},
     * конвертирует в {@link OrderDTO} и возвращает контроллеру
     *
     * @param orderDTO объект для сохранения в БД
     * @param login    логин отправителя, для связи заказа с определенным {@link UserClient}
     * @return {@link OrderDTO}
     * @throws {@link LoginNotFoundException} если не был найден пользователь по login (маловероятно, потому что login
     *                берется из Session и устанавливается туда при авторизации
     * @throws {@link DepartureNotFoundException} если не существует отправления c датой {@link OrderDTO#getDate()}
     *                для указанного рейса {@link OrderDTO#getFlightId()}
     * @throws {@link NationalityNotFoundException} если не верно указан код национальности (допустимо не указывать)
     * @throws {@link ClassTypeNotFoundException} если не верно указан тип класса (допустимо не указывать,
     *                по умолчанию установится ECONOMY)
     * @throws {@link PriceNotFoundException} если не найден прайс для этого класса для текущего рейса (допущены
     *                ошибки при сохранении рейса)
     */
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
                        .orElseThrow(() -> new ClassTypeNotFoundException("Not found class with name " + ticketDTO.getClassType()));
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
        order.setId(orderDao.saveOrder(order));

        return orderDTOMapper.convertToDTO(order);
    }

    /**
     * Метод принимает на вход список параметров в виде {@link Map<String,String}. Все параметры не обязятельные
     * Для поиска по заданным параметрам формируется объект {@link Order} и отправляется в БД
     * Проверка параметров на null осуществляется непосредственно при поиске в resources/mappers/OrderMapper.xml
     *
     * @param parameters Полный список доступных параметров {@link ParametersEnum}
     * @return {@link List<OrderDTO} список объектов
     * @throws {@link PlaneNotFoundException} если в параметрах поиска было указано неверное имя {@link Plane}
     * @throws {@link UserNotFoundException} если в параметрах был указан неверный clientId (при запросе
     *                от {@link UserClient}) id берется из сессии, при запросе от {@link UserAdmin} устанавливается в
     *                параметрах запроса
     * @throws ParseException может выкинуть при форматировании строки в {@link java.util.Date}
     *                Формат даты yyyy-MM-dd
     */

    @Override
    public List<OrderDTO> getOrdersByParameters(Map<String, String> parameters) throws ParseException {
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
}
