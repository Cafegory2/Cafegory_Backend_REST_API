package com.example.demo.helper;

import com.example.demo.factory.TestReviewCafeTagFactory;
import com.example.demo.factory.TestReviewFactory;
import com.example.demo.implement.cafe.CafeEntity;
import com.example.demo.implement.cafe.CafeTagEntity;
import com.example.demo.implement.member.MemberEntity;
import com.example.demo.implement.review.ReviewCafeTagEntity;
import com.example.demo.implement.review.ReviewEntity;
import com.example.demo.repository.cafe.CafeRepository;
import com.example.demo.repository.cafe.CafeTagRepository;
import com.example.demo.repository.member.MemberRepository;
import com.example.demo.repository.review.ReviewCafeTagRepository;
import com.example.demo.repository.review.ReviewRepository;
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
