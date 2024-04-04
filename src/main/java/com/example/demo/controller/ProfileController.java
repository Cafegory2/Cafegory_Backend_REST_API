package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.auth.CafegoryTokenManager;
import com.example.demo.dto.profile.ProfileResponse;
import com.example.demo.dto.profile.ProfileUpdateRequest;
import com.example.demo.service.profile.ProfileService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {
	private final ProfileService profileService;
	private final CafegoryTokenManager cafegoryTokenManager;

	@GetMapping("/{memberId:[0-9]+}")
	public ResponseEntity<ProfileResponse> get(@PathVariable("memberId") Long targetMemberId,
		@RequestHeader("Authorization") String authorization) {
		long requestMemberId = cafegoryTokenManager.getIdentityId(authorization);
		ProfileResponse profileResponse = profileService.get(requestMemberId, targetMemberId);
		return ResponseEntity.ok(profileResponse);
	}

	@PatchMapping("/{memberId:[0-9]+}")
	public ResponseEntity<ProfileResponse> update(@PathVariable("memberId") Long targetMemberId,
		@RequestBody ProfileUpdateRequest profileUpdateRequest,
		@RequestHeader("Authorization") String authorization) {
		long requestMemberId = cafegoryTokenManager.getIdentityId(authorization);
		ProfileResponse profileResponse = profileService.update(requestMemberId, targetMemberId, profileUpdateRequest);
		return ResponseEntity.ok(profileResponse);
	}
}
