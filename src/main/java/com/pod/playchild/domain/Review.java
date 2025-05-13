package com.pod.playchild.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Users users;

    @ManyToOne
    private Place place;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private int rating; // 1~5점
    
    @Column(nullable = false)
    private LocalDateTime createdAt;
    
    @Column
    private int cleanliness; // 청결도 1~5점
    
    @Column
    private int safety; // 안전성 1~5점
    
    @Column
    private int convenience; // 편의성 1~5점
    
    @Column
    private int costPerformance; // 가성비 1~5점
    
    @ElementCollection
    private List<String> photoUrls = new ArrayList<>();
    
    @Column
    private int likeCount = 0; // 리뷰 좋아요 수
    
    @ElementCollection
    private List<AgeGroup> visitedWithAgeGroups = new ArrayList<>(); // 방문 시 동반한 자녀 연령대
    
    // 리뷰 생성 메서드
    public static Review create(Users user, Place place, String content, int rating) {
        Review review = new Review();
        review.users = user;
        review.place = place;
        review.content = content;
        review.rating = validateRating(rating);
        review.createdAt = LocalDateTime.now();
        return review;
    }
    
    // 세부 평가 추가 메서드
    public void addDetailedRatings(int cleanliness, int safety, int convenience, int costPerformance) {
        this.cleanliness = validateRating(cleanliness);
        this.safety = validateRating(safety);
        this.convenience = validateRating(convenience);
        this.costPerformance = validateRating(costPerformance);
    }
    
    // 사진 추가 메서드
    public void addPhoto(String photoUrl) {
        this.photoUrls.add(photoUrl);
    }
    
    // 동반 자녀 연령대 추가
    public void addVisitedWithAgeGroup(AgeGroup ageGroup) {
        if (!this.visitedWithAgeGroups.contains(ageGroup)) {
            this.visitedWithAgeGroups.add(ageGroup);
        }
    }
    
    // 좋아요 추가
    public void incrementLike() {
        this.likeCount++;
    }
    
    // 평점 검증 (1~5점)
    private static int validateRating(int rating) {
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("평점은 1~5점 사이여야 합니다.");
        }
        return rating;
    }
}
