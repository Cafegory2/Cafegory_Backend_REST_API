package com.example.demo.factory;

import com.example.demo.cafe.infrastructure.CafeEntity;
import com.example.demo.member.infrastructure.MemberEntity;
import com.example.demo.implement.review.ReviewEntity;

public class TestReviewFactory {

	public static ReviewEntity createReview(CafeEntity cafe, MemberEntity member) {
		return ReviewEntity.builder()
            .cafe(cafe)
            .member(member)
			.build();
	}
}
