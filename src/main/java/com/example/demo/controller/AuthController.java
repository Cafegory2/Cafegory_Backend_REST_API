package com.example.demo.controller;

import com.example.demo.implement.token.JwtAccessToken;
import com.example.demo.implement.token.JwtToken;
import com.example.demo.service.auth.JwtTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtTokenService jwtTokenService;

    @PostMapping("/refresh")
    public JwtAccessToken refreshToken(@RequestBody JwtToken jwtToken) {
        return jwtTokenService.verifyAndRefreshAccessToken(jwtToken.getAccessToken(), jwtToken.getRefreshToken());
    }
}
