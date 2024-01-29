package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.auth.CafegoryToken;
import com.example.demo.dto.oauth2.KakaoOAuth2LoginRequest;
import com.example.demo.dto.oauth2.NaverOAuth2LoginRequest;
import com.example.demo.service.KakaoOAuth2Service;
import com.example.demo.service.NaverOAuth2Service;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/oauth2")
@RequiredArgsConstructor
public class OAuthController {
	private final KakaoOAuth2Service kakaoOAuth2Service;
	private final NaverOAuth2Service naverOAuth2Service;

	@GetMapping("/kakao")
	public CafegoryToken kakao(@RequestParam String code) {
		KakaoOAuth2LoginRequest kakaoOAuth2LoginRequest = new KakaoOAuth2LoginRequest(code);
		return kakaoOAuth2Service.joinOrLogin(kakaoOAuth2LoginRequest);
	}

	@GetMapping("/naver")
	public CafegoryToken naver(@RequestParam String code, @RequestParam String state) {
		NaverOAuth2LoginRequest naverOAuth2LoginRequest = new NaverOAuth2LoginRequest(code, state);
		return naverOAuth2Service.joinOrLogin(naverOAuth2LoginRequest);
	}
}
