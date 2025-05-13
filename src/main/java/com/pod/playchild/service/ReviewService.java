package com.pod.playchild.service;

import com.pod.playchild.domain.AgeGroup;
import com.pod.playchild.domain.Place;
import com.pod.playchild.domain.Review;
import com.pod.playchild.domain.Users;
import com.pod.playchild.repository.PlaceRepository;
import com.pod.playchild.repository.ReviewRepository;
import com.pod.playchild.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final UsersRepository usersRepository;
    private final PlaceRepository placeRepository;
    
    // 리뷰 작성
    @Transactional
    public Review addReview(Long userId, Long placeId, String content, int rating) {
        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
                
        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 장소입니다."));
                
        Review review = Review.create(user, place, content, rating);
        return reviewRepository.save(review);
    }
    
    // 상세 평가 추가
    @Transactional
    public Review addDetailedRatings(Long reviewId, int cleanliness, int safety, int convenience, int costPerformance) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 리뷰입니다."));
                
        review.addDetailedRatings(cleanliness, safety, convenience, costPerformance);
        return review;
    }
    
    // 방문 연령대 추가
    @Transactional
    public Review addVisitedWithAgeGroup(Long reviewId, AgeGroup ageGroup) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 리뷰입니다."));
                
        review.addVisitedWithAgeGroup(ageGroup);
        return review;
    }
    
    // 사진 추가
    @Transactional
    public Review addPhoto(Long reviewId, String photoUrl) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 리뷰입니다."));
                
        review.addPhoto(photoUrl);
        return review;
    }
    
    // 좋아요 추가
    @Transactional
    public Review likeReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 리뷰입니다."));
                
        review.incrementLike();
        return review;
    }
    
    // 장소별 리뷰 조회
    public List<Review> findByPlaceId(Long placeId) {
        return reviewRepository.findByPlaceId(placeId);
    }
    
    // 사용자별 리뷰 조회
    public List<Review> findByUserId(Long userId) {
        return reviewRepository.findByUsersId(userId);
    }
    
    // 평점 높은 순으로 리뷰 조회
    public List<Review> findTopRatedReviews(int limit) {
        return reviewRepository.findTopByOrderByRatingDesc(limit);
    }
    
    // 최신 리뷰 조회
    public List<Review> findLatestReviews(int limit) {
        return reviewRepository.findTopByOrderByCreatedAtDesc(limit);
    }
}
