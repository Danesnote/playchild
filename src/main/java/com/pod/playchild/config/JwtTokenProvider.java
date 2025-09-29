package com.pod.playchild.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

public class JwtTokenProvider {
    private final Key key;
    private final long validityInMs = 1000L * 60 * 60; // 1시간

    public JwtTokenProvider(String secretKey) {
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    // 토큰 생성
    public String createToken(String username) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + validityInMs);

        return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(now)
            .setExpiration(expiry)
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();
    }

    // 토큰에서 username 추출
    public String getUsername(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build()
            .parseClaimsJws(token)
            .getBody()
            .getSubject();
    }

    // 토큰 유효성 검사
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}