package com.example.demo.controller;

import com.example.demo.dto.auth.CafegoryAccessToken;
import com.example.demo.dto.auth.CafegoryToken;
import com.example.demo.service.auth.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtService jwtService;

    @PostMapping("/refresh")
    public CafegoryAccessToken refreshToken(@RequestBody CafegoryToken cafegoryToken) {
        return jwtService.verifyAndRefreshAccessToken(cafegoryToken.getAccessToken(), cafegoryToken.getRefreshToken());
    }
}
