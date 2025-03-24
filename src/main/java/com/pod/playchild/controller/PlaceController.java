package com.pod.playchild.controller;

import com.pod.playchild.dto.request.PlaceRequestDto;
import com.pod.playchild.dto.response.PlaceResponseDto;
import com.pod.playchild.service.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/places")
@RequiredArgsConstructor
public class PlaceController {
    private final PlaceService placeService;

    @PostMapping
    public ResponseEntity<PlaceResponseDto> addPlace(@RequestBody PlaceRequestDto requestDto) {
        return ResponseEntity.ok(placeService.registerPlace(requestDto));
    }

}