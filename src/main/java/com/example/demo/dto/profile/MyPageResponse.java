package com.example.demo.dto.profile;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MyPageResponse {

	private String nickname;
	private String email;
	private String profileUrl;
	private String bio;
	private List<Review> reviews;

	private static class Review {
		// 카페에 남긴 리뷰

	}
}
