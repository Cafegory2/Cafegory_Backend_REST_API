package com.example.demo.mapper.review;

import java.util.List;

import org.springframework.data.domain.Page;

import com.example.demo.domain.ReviewImpl;
import com.example.demo.dto.ReviewResponse;
import com.example.demo.dto.ReviewSearchResponse;

public class ReviewAdaptor implements ReviewMapper {

	private final ReviewMapStruct reviewMapper = ReviewMapStruct.INSTANCE;

	@Override
	public ReviewResponse reviewToReviewResponse(ReviewImpl review) {
		return reviewMapper.reviewToReviewResponse(review);
	}

	@Override
	public List<ReviewSearchResponse> pagedReviewsToReviewSearchResponses(Page<ReviewImpl> pagedReviews) {
		return reviewMapper.pagedReviewsToReviewSearchResponses(pagedReviews);
	}
}
