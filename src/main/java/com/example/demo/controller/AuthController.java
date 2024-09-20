package com.example.demo.controller;

import com.example.demo.implement.token.JwtAccessToken;
import com.example.demo.service.token.JwtTokenManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private static final String BEARER = "Bearer ";

    private final JwtTokenManagementService jwtTokenManagementService;

    @PostMapping("/refresh")
    public ResponseEntity<JwtAccessToken> refreshToken(
        @RequestHeader("Authorization") String accessToken,
        @RequestHeader("Refresh-Token") String refreshToken) {
        JwtAccessToken jwtAccessToken = jwtTokenManagementService.verifyAndRefreshAccessToken(
            extractJwtAccessToken(accessToken), refreshToken);
        return ResponseEntity.ok(jwtAccessToken);
    }

    private String extractJwtAccessToken(String authorization) {
        return authorization.substring(BEARER.length());
    }
}
