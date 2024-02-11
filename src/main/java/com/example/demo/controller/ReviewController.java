package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.dto.PagedResponse;
import com.example.demo.dto.ReviewSearchRequest;
import com.example.demo.dto.ReviewSearchResponse;
import com.example.demo.service.ReviewQueryService;
import com.example.demo.service.ReviewService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ReviewController {

	private final ReviewService reviewService;
	private final ReviewQueryService reviewQueryService;

	@GetMapping("/cafe/{cafeId}/review/list")
	public ResponseEntity<PagedResponse<ReviewSearchResponse>> reviewList(ReviewSearchRequest request) {
		PagedResponse<ReviewSearchResponse> response = reviewQueryService.searchWithPagingByCafeId(
			request);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// @PostMapping("/cafe/{cafeId}/review")
	// public ResponseEntity<>
}
