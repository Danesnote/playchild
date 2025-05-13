package com.pod.playchild.repository;

import com.pod.playchild.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    
    // 장소별 리뷰 조회
    List<Review> findByPlaceId(Long placeId);
    
    // 사용자별 리뷰 조회
    List<Review> findByUsersId(Long userId);
    
    // 평점 높은 순으로 리뷰 조회
    @Query(value = "SELECT * FROM review ORDER BY rating DESC LIMIT :limit", nativeQuery = true)
    List<Review> findTopByOrderByRatingDesc(@Param("limit") int limit);
    
    // 최신 리뷰 조회
    @Query(value = "SELECT * FROM review ORDER BY created_at DESC LIMIT :limit", nativeQuery = true)
    List<Review> findTopByOrderByCreatedAtDesc(@Param("limit") int limit);
    
    // 특정 연령대 방문 리뷰 조회
    @Query("SELECT r FROM Review r JOIN r.visitedWithAgeGroups age WHERE age = :ageGroup")
    List<Review> findByVisitedWithAgeGroup(@Param("ageGroup") String ageGroup);
    
    // 좋아요 많은 순 리뷰 조회
    @Query(value = "SELECT * FROM review ORDER BY like_count DESC LIMIT :limit", nativeQuery = true)
    List<Review> findTopByOrderByLikeCountDesc(@Param("limit") int limit);
} 