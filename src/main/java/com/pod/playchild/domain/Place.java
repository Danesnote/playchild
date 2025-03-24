package com.pod.playchild.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "place")
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

    public static Place create(String name, String address, Double latitude, Double longitude) {
        validate(name, address, latitude, longitude);
        return new Place(null, name, address, latitude, longitude);
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