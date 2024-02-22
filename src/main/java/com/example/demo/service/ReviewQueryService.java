package com.example.demo.service;

import com.example.demo.dto.PagedResponse;
import com.example.demo.dto.ReviewResponse;
import com.example.demo.dto.ReviewSearchRequest;
import com.example.demo.dto.ReviewSearchResponse;

public interface ReviewQueryService {

	PagedResponse<ReviewSearchResponse> searchWithPagingByCafeId(ReviewSearchRequest request);

	ReviewResponse searchOne(Long reviewId);
}
