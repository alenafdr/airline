package com.airline.service;

import com.airline.model.dto.PlaceDTO;

import java.util.List;

public interface PlaceService {
    List<String> getOccupyPlaces(Long orderId);

    PlaceDTO registration(PlaceDTO placeDTO, String login);
}
