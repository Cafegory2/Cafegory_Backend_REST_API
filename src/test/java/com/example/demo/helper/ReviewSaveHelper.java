package com.example.demo.helper;

import com.example.demo.domain.cafe.Cafe;
import com.example.demo.domain.member.Member;
import com.example.demo.domain.review.Review;
import com.example.demo.factory.TestReviewFactory;
import com.example.demo.repository.review.ReviewRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ReviewSaveHelper {

	private final ReviewRepository reviewRepository;

	public Review saveReview(Cafe cafe, Member member) {
		Review review = TestReviewFactory.createReview(cafe, member);
		return reviewRepository.save(review);
	}
}
