package com.example.demo.controller;

import com.example.demo.dto.profile.WelcomeProfileResponse;
import com.example.demo.service.profile.ProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping("/welcome")
    public ResponseEntity<WelcomeProfileResponse> welcome(@AuthenticationPrincipal UserDetails userDetails) {
        Long memberId = Long.parseLong(userDetails.getUsername());
        WelcomeProfileResponse response = profileService.getWelcomeProfile(memberId);

        return ResponseEntity.ok(response);
    }

}
