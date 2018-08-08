package com.airline.service;

import com.airline.model.UserClient;
import com.airline.model.dto.OrderDTO;
import com.airline.model.dto.PlaceDTO;

import java.util.List;
import java.util.Map;

public interface OrderService {
    OrderDTO saveOrder(OrderDTO orderDTO, UserClient userClient);

    List<OrderDTO> getOrdersByParameters (Map<String,String> parameters) throws Exception ;

    List<String> getFreePlaces(Long orderId);

    PlaceDTO registration(PlaceDTO placeDTO);
}
