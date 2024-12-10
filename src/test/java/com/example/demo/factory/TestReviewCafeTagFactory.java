package com.example.demo.factory;

import com.example.demo.cafe.infrastructure.CafeTagEntity;
import com.example.demo.cafe.infrastructure.ReviewCafeTagEntity;
import com.example.demo.cafe.infrastructure.ReviewEntity;

public class TestReviewCafeTagFactory {

	public static ReviewCafeTagEntity createReviewCafeTag(ReviewEntity review, CafeTagEntity cafeTag) {
		return ReviewCafeTagEntity.builder()
			.review(review)
			.cafeTag(cafeTag)
			.build();
	}
}
