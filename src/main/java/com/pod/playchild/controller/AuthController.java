package com.pod.playchild.controller;

import com.pod.playchild.config.JwtTokenProvider;
import java.util.Map;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final JwtTokenProvider jwtTokenProvider =
        new JwtTokenProvider("mySecretKeymySecretKeymySecretKey!!"); // 32바이트 이상

    @PostMapping("/login")
    public Map<String, String> login(@RequestParam String username,
        @RequestParam String password) {
        // (실제로는 DB 조회 필요)
        if ("user".equals(username) && "1234".equals(password)) {
            String token = jwtTokenProvider.createToken(username);
            return Map.of("token", token);
        } else {
            throw new RuntimeException("Invalid credentials");
        }
    }
}