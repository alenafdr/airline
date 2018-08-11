package com.airline.rest;

import com.airline.model.ParametersEnum;
import com.airline.model.dto.OrderDTO;
import com.airline.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Класс используется для обработки входящих запросов по адресу "/api/orders/", имеет методы для обработки
 * POST и GET запросов
 */

@CrossOrigin
@RestController()
@RequestMapping(value = "/api/orders/")
public class OrderController {

    private OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;

    }

    /**
     * Метод обрабатывает входящие POST запросы по адресу "/api/orders/". Получает на вход объект {@link OrderDTO}
     * для дальнейшей обработки, а так же {@link HttpServletRequest} необходимый для получения атрибутов сессии,
     * заданных при авторизации
     *
     * @param orderDTO объект для сохранения, проходит валидацию (параметры валидации заданы аннотациями в классе
     *                 {@link OrderDTO}
     * @param request  для получения атрибутов сессии
     * @return {@link ResponseEntity<OrderDTO>}
     */

    @PostMapping(value = "",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<OrderDTO> saveOrder(@RequestBody @Validated OrderDTO orderDTO,
                                              HttpServletRequest request) {
        String login = (String) request.getSession().getAttribute("login");
        return new ResponseEntity<>(orderService.saveOrder(orderDTO, login), HttpStatus.OK);
    }

    /**
     * Метод принимает на вход список троковых параметров, все параметры не обязательные, собирает все параметры в
     * {@link} Map<String, String> и отправляет в {@link OrderService}. По установленным параметрам сессии определяет
     * тип пользователя, от которого был получен запрос - если это client, то устанавливает clientId из параметров сессии
     * (чтобы клиент видел только свои заказы), если это admin, то устанавливает clientId из параметров запроса
     *
     * @param fromTown
     * @param toTown
     * @param flightName
     * @param planeName
     * @param fromDate
     * @param toDate
     * @param clientId
     * @param request    параметр необходим для получения атрибутов сессии
     * @return {@link ResponseEntity<List<OrderDTO>>}
     * @throws Exception
     */

    @GetMapping(value = "",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<OrderDTO>> getOrdersByParameters(@RequestParam(name = "fromTown", required = false) String fromTown,
                                                                @RequestParam(name = "toTown", required = false) String toTown,
                                                                @RequestParam(name = "flightName", required = false) String flightName,
                                                                @RequestParam(name = "planeName", required = false) String planeName,
                                                                @RequestParam(name = "fromDate", required = false) String fromDate,
                                                                @RequestParam(name = "toDate", required = false) String toDate,
                                                                @RequestParam(name = "clientId", required = false) String clientId,
                                                                HttpServletRequest request) throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put(ParametersEnum.FROM_TOWN.getValue(), fromTown);
        map.put(ParametersEnum.TO_TOWN.getValue(), toTown);
        map.put(ParametersEnum.FLIGHT_NAME.getValue(), flightName);
        map.put(ParametersEnum.PLANE_NAME.getValue(), planeName);
        map.put(ParametersEnum.FROM_DATE.getValue(), fromDate);
        map.put(ParametersEnum.TO_DATE.getValue(), toDate);
        String userType = (String) request.getSession().getAttribute("userType");
        if ("client".equals(userType)) {
            String id = (String) request.getSession().getAttribute("userId");
            map.put(ParametersEnum.CLIENT_ID.getValue(), id);
        } else {
            map.put(ParametersEnum.CLIENT_ID.getValue(), clientId);
        }

        return new ResponseEntity<>(orderService.getOrdersByParameters(map), HttpStatus.OK);
    }
}
