package com.pod.playchild.service;

import com.pod.playchild.domain.Place;
import com.pod.playchild.dto.request.PlaceRequestDto;
import com.pod.playchild.dto.response.PlaceResponseDto;
import com.pod.playchild.repository.PlaceRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlaceService {

    @Autowired
    private final PlaceRepository placeRepository;

    @Transactional
    public PlaceResponseDto registerPlace(PlaceRequestDto requestDto) {
        Place place = Place.create(requestDto.getName(), requestDto.getAddress(), requestDto.getLatitude(), requestDto.getLongitude());
        Place savedPlace = placeRepository.save(place);

        return new PlaceResponseDto(savedPlace);
    }
}
