package com.pod.playchild.service;

import com.pod.playchild.domain.AgeGroup;
import com.pod.playchild.domain.Place;
import com.pod.playchild.domain.PlaceCategory;
import com.pod.playchild.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PlaceService {

    private final PlaceRepository placeRepository;

    // 기본 장소 등록
    @Transactional
    public Place registerPlace(String name, String address, Double latitude, Double longitude, PlaceCategory category) {
        Place place = Place.create(name, address, latitude, longitude, category);
        return placeRepository.save(place);
    }
    
    // 전체 장소 조회
    public List<Place> findAllPlaces() {
        return placeRepository.findAll();
    }
    
    // 상세 정보가 포함된 장소 등록
    @Transactional
    public Place registerPlaceWithDetails(String name, String address, Double latitude, Double longitude,
                                         PlaceCategory category, String description, Integer entranceFee,
                                         LocalTime openTime, LocalTime closeTime, Boolean hasParkingLot,
                                         Boolean hasRestroom, Boolean hasNursingRoom, List<AgeGroup> suitableAgeGroups) {
        Place place = Place.create(name, address, latitude, longitude, category);
        
        try {
            // 추가 필드 설정 (리플렉션 등의 기법으로 개선 가능)
            place.getClass().getDeclaredField("description").setAccessible(true);
            place.getClass().getDeclaredField("description").set(place, description);
            
            place.getClass().getDeclaredField("entranceFee").setAccessible(true);
            place.getClass().getDeclaredField("entranceFee").set(place, entranceFee);
            
            place.getClass().getDeclaredField("openTime").setAccessible(true);
            place.getClass().getDeclaredField("openTime").set(place, openTime);
            
            place.getClass().getDeclaredField("closeTime").setAccessible(true);
            place.getClass().getDeclaredField("closeTime").set(place, closeTime);
            
            place.getClass().getDeclaredField("hasParkingLot").setAccessible(true);
            place.getClass().getDeclaredField("hasParkingLot").set(place, hasParkingLot);
            
            place.getClass().getDeclaredField("hasRestroom").setAccessible(true);
            place.getClass().getDeclaredField("hasRestroom").set(place, hasRestroom);
            
            place.getClass().getDeclaredField("hasNursingRoom").setAccessible(true);
            place.getClass().getDeclaredField("hasNursingRoom").set(place, hasNursingRoom);
            
            place.getClass().getDeclaredField("suitableAgeGroups").setAccessible(true);
            place.getClass().getDeclaredField("suitableAgeGroups").set(place, suitableAgeGroups);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("장소 정보 설정 중 오류가 발생했습니다.", e);
        }
        
        return placeRepository.save(place);
    }

    // 카테고리별 장소 검색
    public List<Place> findByCategory(PlaceCategory category) {
        return placeRepository.findByCategory(category);
    }
    
    // 키워드로 장소 검색
    public List<Place> searchByKeyword(String keyword) {
        return placeRepository.findByNameContainingOrAddressContaining(keyword, keyword);
    }
    
    // 연령대별 추천 장소
    public List<Place> recommendByAgeGroup(AgeGroup ageGroup) {
        return placeRepository.findBySuitableAgeGroup(ageGroup);
    }
    
    // 반경 내 장소 검색
    public List<Place> findNearbyPlaces(double latitude, double longitude, double distanceInKm) {
        return placeRepository.findPlacesWithinDistance(latitude, longitude, distanceInKm);
    }
    
    // 무료 장소 검색
    public List<Place> findFreePlaces() {
        return placeRepository.findByEntranceFeeEquals(0);
    }
    
    // 특정 시설 보유 장소 검색
    public List<Place> findPlacesWithParkingLot() {
        return placeRepository.findByHasParkingLotTrue();
    }
    
    public List<Place> findPlacesWithNursingRoom() {
        return placeRepository.findByHasNursingRoomTrue();
    }
}
