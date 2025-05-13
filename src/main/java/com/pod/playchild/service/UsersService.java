package com.pod.playchild.service;

import com.pod.playchild.domain.AgeGroup;
import com.pod.playchild.domain.Users;
import com.pod.playchild.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UsersService {
    private final UsersRepository usersRepository;
    
    // 회원가입
    @Transactional
    public Users registerUser(String email, String password, String nickname) {
        // 중복 이메일 체크
        usersRepository.findByEmail(email).ifPresent(user -> {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        });
        
        // 중복 닉네임 체크
        usersRepository.findByNickname(nickname).ifPresent(user -> {
            throw new IllegalArgumentException("이미 사용 중인 닉네임입니다.");
        });
        
        // 실제 구현에서는 비밀번호 암호화 필요
        Users user = Users.create(email, password, nickname);
        return usersRepository.save(user);
    }
    
    // 로그인
    public Users login(String email, String password) {
        Users user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이메일입니다."));
        
        // 실제 구현에서는 암호화된 비밀번호 비교 필요
        if (!user.getPassword().equals(password)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        
        return user;
    }
    
    // 사용자 조회
    public Users findById(Long userId) {
        return usersRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
    }
    
    // 자녀 추가
    @Transactional
    public Users addChild(Long userId, String name, AgeGroup ageGroup) {
        Users user = findById(userId);
        user.addChild(name, ageGroup);
        return user;
    }
    
    // 즐겨찾기 추가
    @Transactional
    public Users addFavoritePlace(Long userId, Long placeId) {
        Users user = findById(userId);
        user.addFavoritePlace(placeId);
        return user;
    }
    
    // 즐겨찾기 삭제
    @Transactional
    public Users removeFavoritePlace(Long userId, Long placeId) {
        Users user = findById(userId);
        user.removeFavoritePlace(placeId);
        return user;
    }
    
    // 프로필 이미지 업데이트
    @Transactional
    public Users updateProfileImage(Long userId, String imageUrl) {
        Users user = findById(userId);
        
        try {
            // 리플렉션으로 필드 접근 (실제로는 setter가 권장됨)
            user.getClass().getDeclaredField("profileImageUrl").setAccessible(true);
            user.getClass().getDeclaredField("profileImageUrl").set(user, imageUrl);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("프로필 이미지 업데이트 중 오류가 발생했습니다.", e);
        }
        
        return user;
    }
    
    // 닉네임으로 사용자 검색
    public List<Users> searchByNickname(String keyword) {
        return usersRepository.findByNicknameContaining(keyword);
    }
    
    // 특정 장소를 즐겨찾기한 사용자 목록
    public List<Users> findUsersByFavoritePlace(Long placeId) {
        return usersRepository.findByFavoritePlaceId(placeId);
    }
}
