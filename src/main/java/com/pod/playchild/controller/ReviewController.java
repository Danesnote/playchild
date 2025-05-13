package com.pod.playchild.controller;

import com.pod.playchild.domain.AgeGroup;
import com.pod.playchild.domain.Review;
import com.pod.playchild.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;
    
    // 리뷰 등록 API
    @PostMapping
    public ResponseEntity<Review> addReview(@RequestBody ReviewRequest request) {
        Review review = reviewService.addReview(
                request.getUserId(),
                request.getPlaceId(),
                request.getContent(),
                request.getRating()
        );
        
        if (request.getDetailedRatings() != null) {
            reviewService.addDetailedRatings(
                    review.getId(),
                    request.getDetailedRatings().getCleanliness(),
                    request.getDetailedRatings().getSafety(),
                    request.getDetailedRatings().getConvenience(),
                    request.getDetailedRatings().getCostPerformance()
            );
        }
        
        if (request.getVisitedWithAgeGroups() != null && !request.getVisitedWithAgeGroups().isEmpty()) {
            for (AgeGroup ageGroup : request.getVisitedWithAgeGroups()) {
                reviewService.addVisitedWithAgeGroup(review.getId(), ageGroup);
            }
        }
        
        return ResponseEntity.ok(review);
    }
    
    // 장소별 리뷰 조회 API
    @GetMapping("/place/{placeId}")
    public ResponseEntity<List<Review>> getReviewsByPlace(@PathVariable Long placeId) {
        return ResponseEntity.ok(reviewService.findByPlaceId(placeId));
    }
    
    // 사용자별 리뷰 조회 API
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Review>> getReviewsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(reviewService.findByUserId(userId));
    }
    
    // 리뷰 좋아요 API
    @PostMapping("/{reviewId}/like")
    public ResponseEntity<Review> likeReview(@PathVariable Long reviewId) {
        return ResponseEntity.ok(reviewService.likeReview(reviewId));
    }
    
    // 리뷰 사진 추가 API
    @PostMapping("/{reviewId}/photo")
    public ResponseEntity<Review> addPhotoToReview(
            @PathVariable Long reviewId,
            @RequestBody PhotoRequest request) {
        return ResponseEntity.ok(reviewService.addPhoto(reviewId, request.getPhotoUrl()));
    }
    
    // 요청 DTO
    public static class ReviewRequest {
        private Long userId;
        private Long placeId;
        private String content;
        private int rating;
        private DetailedRatings detailedRatings;
        private List<AgeGroup> visitedWithAgeGroups;
        
        // Getter 메서드들
        public Long getUserId() { return userId; }
        public Long getPlaceId() { return placeId; }
        public String getContent() { return content; }
        public int getRating() { return rating; }
        public DetailedRatings getDetailedRatings() { return detailedRatings; }
        public List<AgeGroup> getVisitedWithAgeGroups() { return visitedWithAgeGroups; }
    }
    
    public static class DetailedRatings {
        private int cleanliness;
        private int safety;
        private int convenience;
        private int costPerformance;
        
        // Getter 메서드들
        public int getCleanliness() { return cleanliness; }
        public int getSafety() { return safety; }
        public int getConvenience() { return convenience; }
        public int getCostPerformance() { return costPerformance; }
    }
    
    public static class PhotoRequest {
        private String photoUrl;
        
        // Getter 메서드
        public String getPhotoUrl() { return photoUrl; }
    }
}
