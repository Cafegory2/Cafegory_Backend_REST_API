package com.example.demo.factory;

import com.example.demo.domain.cafe.Cafe;
import com.example.demo.domain.member.Member;
import com.example.demo.domain.review.Review;

public class TestReviewFactory {

	public static Review createReview(Cafe cafe, Member member) {
		return Review.builder()
			.content("커피가 맛있고 공부하기 좋아요!!")
			.rate(4.9)
			.cafe(cafe)
			.member(member)
			.build();
	}

	public static Review createReviewWithContentAndRate(String content, double rate) {
		return Review.builder()
			.content(content)
			.rate(rate)
			.build();
	}

	public static Review createReviewWithContent(String content) {
		return Review.builder()
			.content(content)
			.build();
	}
}
