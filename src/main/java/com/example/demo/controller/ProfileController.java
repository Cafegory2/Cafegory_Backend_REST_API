package com.example.demo.controller;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.auth.CafegoryTokenManager;
import com.example.demo.dto.profile.ProfileResponse;
import com.example.demo.service.profile.ProfileService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {
	private final ProfileService profileService;
	private final CafegoryTokenManager cafegoryTokenManager;

	@GetMapping("/{memberId:[0-9]+}")
	public ResponseEntity<ProfileResponse> get(@PathVariable("memberId") Long targetMemberID,
		@RequestHeader("Authorization") String authorization) {
		long requestMemberID = cafegoryTokenManager.getIdentityId(authorization);
		ProfileResponse profileResponse = profileService.get(requestMemberID, targetMemberID, LocalDateTime.now());
		return ResponseEntity.ok(profileResponse);
	}
}
