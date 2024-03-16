package com.example.demo.mapper.review;

import java.util.List;

import org.springframework.data.domain.Page;

import com.example.demo.domain.ReviewImpl;
import com.example.demo.dto.ReviewResponse;
import com.example.demo.dto.ReviewSearchResponse;

public interface ReviewMapper {

	ReviewResponse reviewToReviewResponse(ReviewImpl review);

	List<ReviewSearchResponse> pagedReviewsToReviewSearchResponses(Page<ReviewImpl> pagedReviews);
}
