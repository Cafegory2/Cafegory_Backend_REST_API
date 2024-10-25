package com.example.demo.factory;

import com.example.demo.implement.cafe.CafeTagEntity;
import com.example.demo.implement.review.ReviewCafeTagEntity;
import com.example.demo.implement.review.ReviewEntity;

public class TestReviewCafeTagFactory {

	public static ReviewCafeTagEntity createReviewCafeTag(ReviewEntity review, CafeTagEntity cafeTag) {
		return ReviewCafeTagEntity.builder()
			.review(review)
			.cafeTag(cafeTag)
			.build();
	}
}
