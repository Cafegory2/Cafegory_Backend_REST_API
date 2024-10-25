package com.example.demo.helper;

import com.example.demo.factory.TestReviewCafeTagFactory;
import com.example.demo.implement.cafe.CafeTagEntity;
import com.example.demo.cafe.infrastructure.ReviewCafeTagEntity;
import com.example.demo.cafe.infrastructure.ReviewEntity;
import com.example.demo.repository.cafe.CafeTagRepository;
import com.example.demo.cafe.infrastructure.ReviewCafeTagRepository;
import com.example.demo.cafe.infrastructure.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
public class ReviewCafeTagSaveHelper {

	private final ReviewRepository reviewRepository;
	private final CafeTagRepository cafeTagRepository;
	private final ReviewCafeTagRepository reviewCafeTagRepository;

	public ReviewCafeTagEntity saveReview(ReviewEntity review, CafeTagEntity cafeTag) {
		ReviewEntity mergedReview = reviewRepository.save(review);
		CafeTagEntity mergedCafeTag = cafeTagRepository.save(cafeTag);

		ReviewCafeTagEntity reviewCafeTag = TestReviewCafeTagFactory.createReviewCafeTag(mergedReview, mergedCafeTag);
		return reviewCafeTagRepository.save(reviewCafeTag);
	}
}
