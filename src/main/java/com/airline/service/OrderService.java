package com.airline.service;

import com.airline.model.UserClient;
import com.airline.model.dto.OrderDTO;
import com.airline.model.dto.PlaceDTO;

import java.util.List;
import java.util.Map;

public interface OrderService {
    OrderDTO saveOrder(OrderDTO orderDTO, String login);

    List<OrderDTO> getOrdersByParameters (Map<String,String> parameters) throws Exception ;

}
