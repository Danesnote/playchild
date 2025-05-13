package com.pod.playchild.controller;

import com.pod.playchild.domain.AgeGroup;
import com.pod.playchild.domain.Users;
import com.pod.playchild.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UsersController {
    private final UsersService usersService;
    
    // 회원가입 API
    @PostMapping("/register")
    public ResponseEntity<Users> registerUser(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(usersService.registerUser(
                request.getEmail(),
                request.getPassword(),
                request.getNickname()
        ));
    }
    
    // 로그인 API (실제로는 JWT 토큰 발급 등이 필요)
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginRequest request) {
        Users user = usersService.login(request.getEmail(), request.getPassword());
        // 실제 구현에서는 토큰 발급 등 인증 로직 필요
        return ResponseEntity.ok(Map.of(
                "userId", user.getId(),
                "nickname", user.getNickname()
        ));
    }
    
    // 사용자 프로필 조회 API
    @GetMapping("/{userId}")
    public ResponseEntity<Users> getUserProfile(@PathVariable Long userId) {
        return ResponseEntity.ok(usersService.findById(userId));
    }
    
    // 자녀 정보 추가 API
    @PostMapping("/{userId}/child")
    public ResponseEntity<Users> addChild(
            @PathVariable Long userId,
            @RequestBody ChildRequest request) {
        return ResponseEntity.ok(usersService.addChild(
                userId,
                request.getName(),
                request.getAgeGroup()
        ));
    }
    
    // 장소 즐겨찾기 추가 API
    @PostMapping("/{userId}/favorite/{placeId}")
    public ResponseEntity<Users> addFavoritePlace(
            @PathVariable Long userId,
            @PathVariable Long placeId) {
        return ResponseEntity.ok(usersService.addFavoritePlace(userId, placeId));
    }
    
    // 장소 즐겨찾기 삭제 API
    @DeleteMapping("/{userId}/favorite/{placeId}")
    public ResponseEntity<Users> removeFavoritePlace(
            @PathVariable Long userId,
            @PathVariable Long placeId) {
        return ResponseEntity.ok(usersService.removeFavoritePlace(userId, placeId));
    }
    
    // 프로필 이미지 업데이트 API
    @PutMapping("/{userId}/profile-image")
    public ResponseEntity<Users> updateProfileImage(
            @PathVariable Long userId,
            @RequestBody ProfileImageRequest request) {
        return ResponseEntity.ok(usersService.updateProfileImage(userId, request.getImageUrl()));
    }
    
    // 요청 DTO
    public static class RegisterRequest {
        private String email;
        private String password;
        private String nickname;
        
        // Getter 메서드
        public String getEmail() { return email; }
        public String getPassword() { return password; }
        public String getNickname() { return nickname; }
    }
    
    public static class LoginRequest {
        private String email;
        private String password;
        
        // Getter 메서드
        public String getEmail() { return email; }
        public String getPassword() { return password; }
    }
    
    public static class ChildRequest {
        private String name;
        private AgeGroup ageGroup;
        
        // Getter 메서드
        public String getName() { return name; }
        public AgeGroup getAgeGroup() { return ageGroup; }
    }
    
    public static class ProfileImageRequest {
        private String imageUrl;
        
        // Getter 메서드
        public String getImageUrl() { return imageUrl; }
    }
}
