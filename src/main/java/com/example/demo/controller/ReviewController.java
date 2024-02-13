package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.example.demo.domain.auth.CafegoryTokenManager;
import com.example.demo.dto.PagedResponse;
import com.example.demo.dto.ReviewResponse;
import com.example.demo.dto.ReviewSaveRequest;
import com.example.demo.dto.ReviewSearchRequest;
import com.example.demo.dto.ReviewSearchResponse;
import com.example.demo.dto.ReviewUpdateRequest;
import com.example.demo.service.ReviewQueryService;
import com.example.demo.service.ReviewService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ReviewController {

	private final ReviewService reviewService;
	private final ReviewQueryService reviewQueryService;
	private final CafegoryTokenManager cafegoryTokenManager;

	@GetMapping("/cafe/{cafeId}/review/list")
	public ResponseEntity<PagedResponse<ReviewSearchResponse>> reviewList(ReviewSearchRequest request) {
		PagedResponse<ReviewSearchResponse> response = reviewQueryService.searchWithPagingByCafeId(
			request);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/cafe/{cafeId}/review")
	public ResponseEntity<ReviewResponse> saveReview(@PathVariable Long cafeId,
		@RequestHeader("Authorization") String authorization,
		@RequestBody @Validated ReviewSaveRequest reviewSaveRequest) {

		long identityId = cafegoryTokenManager.getIdentityId(authorization);
		Long savedReviewId = reviewService.saveReview(identityId, cafeId, reviewSaveRequest);
		ReviewResponse response = reviewQueryService.searchOne(savedReviewId);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PatchMapping("/cafe/review/{reviewId}")
	public ResponseEntity<ReviewResponse> updateReview(@PathVariable Long reviewId,
		@RequestHeader("Authorization") String authorization,
		@RequestBody @Validated ReviewUpdateRequest reviewUpdateRequest) {

		long identityId = cafegoryTokenManager.getIdentityId(authorization);
		reviewService.updateReview(identityId, reviewId, reviewUpdateRequest);
		ReviewResponse response = reviewQueryService.searchOne(reviewId);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
