package com.pod.playchild.domain;

public enum AgeGroup {
    INFANT("영아", "0-2세"),
    TODDLER("유아", "3-5세"),
    KINDERGARTEN("유치원", "5-7세"),
    ELEMENTARY_LOWER("초등 저학년", "8-10세"),
    ELEMENTARY_UPPER("초등 고학년", "11-13세");
    
    private final String displayName;
    private final String ageRange;
    
    AgeGroup(String displayName, String ageRange) {
        this.displayName = displayName;
        this.ageRange = ageRange;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    public String getAgeRange() {
        return ageRange;
    }
} 