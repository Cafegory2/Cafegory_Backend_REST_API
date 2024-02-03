package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.auth.CafegoryToken;
import com.example.demo.dto.oauth2.KakaoOAuth2TokenRequest;
import com.example.demo.dto.oauth2.NaverOAuth2TokenRequest;
import com.example.demo.service.OAuth2Service;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/oauth2")
@RequiredArgsConstructor
public class OAuthController {
	private final OAuth2Service kakaoOAuth2Service;
	private final OAuth2Service naverOAuth2Service;

	@GetMapping("/kakao")
	public CafegoryToken kakao(@RequestParam String code) {
		KakaoOAuth2TokenRequest kakaoOAuth2LoginRequest = new KakaoOAuth2TokenRequest(code);
		return kakaoOAuth2Service.joinOrLogin(kakaoOAuth2LoginRequest);
	}

	@GetMapping("/naver")
	public CafegoryToken naver(@RequestParam String code, @RequestParam String state) {
		NaverOAuth2TokenRequest naverOAuth2LoginRequest = new NaverOAuth2TokenRequest(code, state);
		return naverOAuth2Service.joinOrLogin(naverOAuth2LoginRequest);
	}
}
