package com.example.demo.controller;

import com.example.demo.implement.token.JwtAccessToken;
import com.example.demo.implement.token.JwtToken;
import com.example.demo.service.token.JwtTokenManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtTokenManagementService jwtTokenManagementService;

    //TODO 요청 바디로 토큰값을 받으면 안되고, 헤더로 받아야한다.
    @PostMapping("/refresh")
    public ResponseEntity<JwtAccessToken> refreshToken(@RequestBody JwtToken jwtToken) {
        JwtAccessToken jwtAccessToken = jwtTokenManagementService.verifyAndRefreshAccessToken(
            jwtToken.getAccessToken(), jwtToken.getRefreshToken());
        return ResponseEntity.ok(jwtAccessToken);
    }
}
