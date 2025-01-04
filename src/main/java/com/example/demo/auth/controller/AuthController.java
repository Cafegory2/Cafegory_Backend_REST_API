package com.example.demo.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.auth.implement.token.JwtAccessToken;
import com.example.demo.auth.service.token.JwtTokenManagementService;

import lombok.RequiredArgsConstructor;

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
