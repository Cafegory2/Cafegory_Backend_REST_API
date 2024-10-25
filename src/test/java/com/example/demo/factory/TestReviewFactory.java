package com.example.demo.factory;

import com.example.demo.implement.cafe.CafeEntity;
import com.example.demo.implement.member.MemberEntity;
import com.example.demo.implement.review.ReviewEntity;

public class TestReviewFactory {

	public static ReviewEntity createReview(CafeEntity cafe, MemberEntity member) {
		return ReviewEntity.builder()
            .cafe(cafe)
            .member(member)
			.build();
	}
}
