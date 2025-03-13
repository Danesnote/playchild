package com.pod.playchild.service;

import com.pod.playchild.domain.Place;
import com.pod.playchild.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlaceService {

    private final PlaceRepository placeRepository;

    public Place registerPlace(String name, String address, Double latitude, Double longitude) {
        Place place = Place.create(name, address, latitude, longitude);
        return placeRepository.save(place);
    }
}
