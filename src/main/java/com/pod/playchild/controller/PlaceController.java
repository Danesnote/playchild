package com.pod.playchild.controller;

import com.pod.playchild.dto.request.PlaceRequestDto;
import com.pod.playchild.dto.response.PlaceResponseDto;
import com.pod.playchild.domain.AgeGroup;
import com.pod.playchild.domain.Place;
import com.pod.playchild.domain.PlaceCategory;

import com.pod.playchild.service.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/places")

@RequiredArgsConstructor   
public class PlaceController {
    private final PlaceService placeService;

    // 장소 등록 API
    @PostMapping
    public ResponseEntity<Place> registerPlace(@RequestBody PlaceRegisterRequest request) {
        return ResponseEntity.ok(placeService.registerPlace(
                request.getName(),
                request.getAddress(),
                request.getLatitude(),
                request.getLongitude(),
                request.getCategory()
        ));
    }
    
    // 카테고리별 장소 조회 API
    @GetMapping("/category/{category}")
    public ResponseEntity<List<Place>> getPlacesByCategory(@PathVariable PlaceCategory category) {
        return ResponseEntity.ok(placeService.findByCategory(category));
    }
    
    // 장소 검색 API
    @GetMapping("/search")
    public ResponseEntity<List<Place>> searchPlaces(@RequestParam String keyword) {
        return ResponseEntity.ok(placeService.searchByKeyword(keyword));
    }
    
    // 연령별 추천 장소 API
    @GetMapping("/recommend/{ageGroup}")
    public ResponseEntity<List<Place>> getRecommendedPlaces(@PathVariable AgeGroup ageGroup) {
        return ResponseEntity.ok(placeService.recommendByAgeGroup(ageGroup));
    }
    
    // 위치 기반 주변 장소 API
    @GetMapping("/nearby")
    public ResponseEntity<List<Place>> getNearbyPlaces(
            @RequestParam Double latitude,
            @RequestParam Double longitude,
            @RequestParam(defaultValue = "3.0") Double distance) {
        return ResponseEntity.ok(placeService.findNearbyPlaces(latitude, longitude, distance));
    }
    
    // 무료 장소 조회 API
    @GetMapping("/free")
    public ResponseEntity<List<Place>> getFreePlaces() {
        return ResponseEntity.ok(placeService.findFreePlaces());
    }
    
    // 시설별 장소 조회 API
    @GetMapping("/filter")
    public ResponseEntity<List<Place>> getFilteredPlaces(@RequestParam(required = false) Boolean hasParkingLot,
                                                        @RequestParam(required = false) Boolean hasNursingRoom) {
        if (Boolean.TRUE.equals(hasParkingLot)) {
            return ResponseEntity.ok(placeService.findPlacesWithParkingLot());
        } else if (Boolean.TRUE.equals(hasNursingRoom)) {
            return ResponseEntity.ok(placeService.findPlacesWithNursingRoom());
        }
        
        // 기본은 전체 조회
        return ResponseEntity.ok(placeService.findAllPlaces());
    }
    
    // 요청 DTO
    public static class PlaceRegisterRequest {
        private String name;
        private String address;
        private Double latitude;
        private Double longitude;
        private PlaceCategory category;
        
        // Getter 메서드
        public String getName() { return name; }
        public String getAddress() { return address; }
        public Double getLatitude() { return latitude; }
        public Double getLongitude() { return longitude; }
        public PlaceCategory getCategory() { return category; }
    }
}