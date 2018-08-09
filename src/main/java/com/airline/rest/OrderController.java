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

@RestController()
@RequestMapping(value = "/api/orders/")
public class OrderController {



    private OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;

    }

    @PostMapping(value = "",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<OrderDTO> saveOrder(@RequestBody @Validated OrderDTO orderDTO,
                                              HttpServletRequest request) {
        String login = (String) request.getSession().getAttribute("login");
        return new ResponseEntity<>(orderService.saveOrder(orderDTO, login), HttpStatus.OK);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
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
