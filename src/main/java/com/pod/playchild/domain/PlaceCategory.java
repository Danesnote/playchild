package com.pod.playchild.domain;

public enum PlaceCategory {
    KIDS_CAFE("키즈카페"),
    INDOOR_PLAYGROUND("실내 놀이터"),
    OUTDOOR_PLAYGROUND("실외 놀이터"),
    PARK("공원"),
    MUSEUM("박물관"),
    AQUARIUM("수족관"),
    ZOO("동물원"),
    THEME_PARK("테마파크"),
    EXPERIENCE_CENTER("체험관"),
    CULTURAL_CENTER("문화센터"),
    LIBRARY("도서관"),
    OTHER("기타");
    
    private final String displayName;
    
    PlaceCategory(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
} 