package com.pod.playchild.repository;

import com.pod.playchild.domain.AgeGroup;
import com.pod.playchild.domain.Place;
import com.pod.playchild.domain.PlaceCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PlaceRepository extends JpaRepository<Place, Long> {
    
    // 카테고리별 장소 검색
    List<Place> findByCategory(PlaceCategory category);
    
    // 장소명 또는 주소에 키워드가 포함된 장소 검색
    List<Place> findByNameContainingOrAddressContaining(String nameKeyword, String addressKeyword);
    
    // 특정 연령대에 적합한 장소 검색
    @Query("SELECT p FROM Place p JOIN p.suitableAgeGroups age WHERE age = :ageGroup")
    List<Place> findBySuitableAgeGroup(@Param("ageGroup") AgeGroup ageGroup);
    
    // 특정 위치 반경 내 장소 검색 (Haversine 공식 사용)
    @Query(value = "SELECT * FROM place p WHERE " +
            "(6371 * acos(cos(radians(:latitude)) * cos(radians(p.latitude)) * " +
            "cos(radians(p.longitude) - radians(:longitude)) + " +
            "sin(radians(:latitude)) * sin(radians(p.latitude)))) < :distance " +
            "ORDER BY (6371 * acos(cos(radians(:latitude)) * cos(radians(p.latitude)) * " +
            "cos(radians(p.longitude) - radians(:longitude)) + " +
            "sin(radians(:latitude)) * sin(radians(p.latitude))))",
            nativeQuery = true)
    List<Place> findPlacesWithinDistance(
            @Param("latitude") double latitude,
            @Param("longitude") double longitude,
            @Param("distance") double distanceInKm);
    
    // 무료 장소 검색
    List<Place> findByEntranceFeeEquals(int entranceFee);
    
    // 시설별 검색
    List<Place> findByHasParkingLotTrue();
    List<Place> findByHasNursingRoomTrue();
    List<Place> findByHasRestroomTrue();
}
