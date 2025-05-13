package com.pod.playchild.repository;

import com.pod.playchild.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Long> {
    
    // 이메일로 사용자 조회
    Optional<Users> findByEmail(String email);
    
    // 닉네임으로 사용자 조회
    Optional<Users> findByNickname(String nickname);
    
    // 닉네임에 특정 키워드가 포함된 사용자 검색
    List<Users> findByNicknameContaining(String keyword);
    
    // 특정 장소를 즐겨찾기한 사용자 조회
    @Query("SELECT u FROM Users u JOIN u.favoritePlaceIds f WHERE f = :placeId")
    List<Users> findByFavoritePlaceId(@Param("placeId") Long placeId);
    
    // 특정 연령대 자녀를 둔 사용자 조회
    @Query("SELECT u FROM Users u JOIN u.children c WHERE c.ageGroup = :ageGroup")
    List<Users> findByChildAgeGroup(@Param("ageGroup") String ageGroup);
} 