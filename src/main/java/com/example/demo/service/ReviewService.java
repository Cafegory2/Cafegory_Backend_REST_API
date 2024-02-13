package com.example.demo.service;

import com.example.demo.dto.ReviewSaveRequest;
import com.example.demo.dto.ReviewUpdateRequest;

public interface ReviewService {

	Long saveReview(Long memberId, Long cafeId, ReviewSaveRequest request);

	void updateReview(Long memberId, Long reviewId, ReviewUpdateRequest request);

	void deleteReview(Long memberId, Long reviewId);
}
