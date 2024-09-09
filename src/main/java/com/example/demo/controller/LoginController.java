package com.example.demo.controller;

import com.example.demo.dto.token.JwtTokenResponse;
import com.example.demo.service.login.LoginService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.implement.token.JwtToken;
import com.example.demo.dto.oauth2.KakaoOAuth2TokenRequest;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @GetMapping("/kakao")
    public ResponseEntity<JwtTokenResponse> kakao(@RequestParam String code) {
        KakaoOAuth2TokenRequest kakaoOAuth2LoginRequest = new KakaoOAuth2TokenRequest(code);
        JwtToken jwtToken = loginService.socialLogin(kakaoOAuth2LoginRequest);
        return ResponseEntity.ok(new JwtTokenResponse(jwtToken.getAccessToken(), jwtToken.getRefreshToken()));
    }
//
//    @GetMapping("/naver")
//    public CafegoryToken naver(@RequestParam String code, @RequestParam String state) {
//        NaverOAuth2TokenRequest naverOAuth2LoginRequest = new NaverOAuth2TokenRequest(code, state);
//        return naverOAuth2Service.joinOrLogin(naverOAuth2LoginRequest);
//    }

//    @PostMapping("/refresh")
//    public CafegoryToken refresh(@RequestBody RefreshRequest refreshRequest) {
//        return authService.refreshCafegoryToken(refreshRequest.getRefreshToken());
//    }
}
