package com.example.demo.member.presentation;

import com.example.demo.cafe.domain.Review;
import com.example.demo.cafe.service.ReviewService;
import com.example.demo.member.domain.Member;
import com.example.demo.member.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.profile.MyPageResponse;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {

	private final ReviewService reviewService;
	private final MemberService memberService;

	//TODO 해야됨!!!!
//	@GetMapping("/welcome")
//	public ResponseEntity<WelcomeProfileResponse> welcome(@AuthenticationPrincipal UserDetails userDetails) {
//		Long memberId = Long.parseLong(userDetails.getUsername());
//		WelcomeProfileResponse response = profileService.getWelcomeProfile(memberId);
//
//		return ResponseEntity.ok(response);
//	}

	@GetMapping("/mypage")
	public ResponseEntity<MyPageResponse> mypage(@AuthenticationPrincipal UserDetails userDetails) {
		Long memberId = Long.parseLong(userDetails.getUsername());

		Member member = memberService.getMember(memberId);
		List<Review> reviews = reviewService.getReviews(memberId);

		MyPageResponse response = MyPageResponse.of(member, reviews);
		return ResponseEntity.ok(response);
	}
}
