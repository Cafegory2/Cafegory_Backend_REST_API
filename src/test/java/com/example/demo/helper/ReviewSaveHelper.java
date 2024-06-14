package com.example.demo.helper.save;

import com.example.demo.builder.TestReviewBuilder;
import com.example.demo.domain.cafe.Cafe;
import com.example.demo.domain.member.Member;
import com.example.demo.domain.review.Review;
import com.example.demo.repository.review.ReviewRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ReviewSaveHelper {

	private final ReviewRepository reviewRepository;

	public Review persistDefaultReview(Cafe cafe, Member member) {
		Review review = new TestReviewBuilder().cafe(cafe).member(member).build();
		return reviewRepository.save(review);
	}
}
