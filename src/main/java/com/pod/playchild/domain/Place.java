package com.pod.playchild.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Place {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;   // 장소 이름

    @Column(nullable = false)
    private String address; // 주소

    @Column(nullable = false)
    private Double latitude; // 위도

    @Column(nullable = false)
    private Double longitude; // 경도
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PlaceCategory category; // 장소 카테고리
    
    @Column
    private String description; // 장소 설명
    
    @Column
    private Integer entranceFee; // 입장료 (0은 무료)
    
    @Column
    private LocalTime openTime; // 오픈 시간
    
    @Column
    private LocalTime closeTime; // 마감 시간
    
    @Column
    private Boolean hasParkingLot; // 주차장 유무
    
    @Column
    private Boolean hasRestroom; // 화장실 유무
    
    @Column
    private Boolean hasNursingRoom; // 수유실 유무
    
    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<AgeGroup> suitableAgeGroups = new ArrayList<>(); // 적합한 연령대

    public static Place create(String name, String address, Double latitude, Double longitude, PlaceCategory category) {
        validate(name, address, latitude, longitude);
        Place place = new Place();
        place.name = name;
        place.address = address;
        place.latitude = latitude;
        place.longitude = longitude;
        place.category = category;
        place.hasParkingLot = false;
        place.hasRestroom = false;
        place.hasNursingRoom = false;
        place.entranceFee = 0;
        return place;
    }

    private static void validate(String name, String address, Double latitude, Double longitude) {
        if (name == null || name.isBlank() || address == null || address.isBlank()
                || latitude == null || longitude == null) {
            throw new IllegalArgumentException("모든 필드가 필수입니다.");
        }

        if (!isInSouthKorea(latitude, longitude)) {
            throw new IllegalArgumentException("대한민국 내 위치만 등록 가능합니다.");
        }

        if(!nameLimit(name)) {
            throw new IllegalArgumentException("장소명은 10자 이하로 작성해야합니다.");
        }
    }

    private static boolean isInSouthKorea(Double latitude, Double longitude) {
        return latitude >= 33.0 && latitude <= 38.5 && longitude >= 124.0 && longitude <= 132.0;
    }

    private static boolean nameLimit(String name) {
        return name.length() <= 10;
    }
}