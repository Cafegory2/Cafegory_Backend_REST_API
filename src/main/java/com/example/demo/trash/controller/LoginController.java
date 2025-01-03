package com.example.demo.trash.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.trash.dto.oauth2.KakaoOAuth2TokenRequest;
import com.example.demo.trash.implement.token.JwtToken;
import com.example.demo.trash.service.login.LoginService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {

	private final LoginService loginService;

	@GetMapping("/kakao")
	public ResponseEntity<JwtToken> kakao(@RequestParam String code) {
		KakaoOAuth2TokenRequest kakaoOAuth2LoginRequest = new KakaoOAuth2TokenRequest(code);
		JwtToken jwtToken = loginService.socialLogin(kakaoOAuth2LoginRequest);
		return ResponseEntity.ok(jwtToken);
	}
	// 인증인가
	// 컨트롤러
	// Member, study, qna, review, cafe,
	// DB모듈
}
