package com.pod.playchild.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Users {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;
    
    @Column(nullable = false)
    private String nickname;
    
    @Column
    private String profileImageUrl;
    
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_id")
    private List<Child> children = new ArrayList<>();
    
    @OneToMany(mappedBy = "users")
    private List<Review> reviews = new ArrayList<>();
    
    @ElementCollection
    private List<Long> favoritePlaceIds = new ArrayList<>();
    
    // 회원가입용 메서드
    public static Users create(String email, String password, String nickname) {
        Users user = new Users();
        user.email = email;
        user.password = password;  // 실제로는 암호화 필요
        user.nickname = nickname;
        return user;
    }
    
    // 자녀 추가 메서드
    public void addChild(String name, AgeGroup ageGroup) {
        Child child = new Child(null, name, ageGroup);
        this.children.add(child);
    }
    
    // 즐겨찾기 추가 메서드
    public void addFavoritePlace(Long placeId) {
        if (!this.favoritePlaceIds.contains(placeId)) {
            this.favoritePlaceIds.add(placeId);
        }
    }
    
    // 즐겨찾기 제거 메서드
    public void removeFavoritePlace(Long placeId) {
        this.favoritePlaceIds.remove(placeId);
    }
}

