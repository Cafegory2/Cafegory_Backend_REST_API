package com.example.demo.service;

import com.example.demo.dto.ReviewSaveRequest;

public interface ReviewService {

	void saveReview(Long memberId, Long cafeId, ReviewSaveRequest request);
}
