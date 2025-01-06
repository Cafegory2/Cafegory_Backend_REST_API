package com.example.demo.api.member;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.cafe.domain.Review;
import com.example.demo.domain.cafe.service.ReviewService;
import com.example.demo.domain.member.domain.Member;
import com.example.demo.domain.member.domain.MemberContent;
import com.example.demo.domain.member.domain.MemberId;
import com.example.demo.domain.member.service.MemberService;
import com.example.demo.domain.member.service.ProfileService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {

	private final ReviewService reviewService;
	private final MemberService memberService;
	private final ProfileService profileService;

	@GetMapping("/welcome")
	public ResponseEntity<WelcomeProfileResponse> welcome(@AuthenticationPrincipal UserDetails userDetails) {
		MemberId memberId = new MemberId(Long.parseLong(userDetails.getUsername()));
		MemberContent memberContent = profileService.getWelcomeProfile(memberId);
		WelcomeProfileResponse response = WelcomeProfileResponse.of(memberContent);

		return ResponseEntity.ok(response);
	}

	@GetMapping("/mypage")
	public ResponseEntity<MyPageResponse> mypage(@AuthenticationPrincipal UserDetails userDetails) {
		MemberId memberId = new MemberId(Long.parseLong(userDetails.getUsername()));

		Member member = memberService.getMember(memberId);
		List<Review> reviews = reviewService.getReviews(memberId);

		MyPageResponse response = MyPageResponse.of(member, reviews);
		return ResponseEntity.ok(response);
	}
}
