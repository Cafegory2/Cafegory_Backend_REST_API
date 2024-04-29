package com.example.demo.service.review;

import com.example.demo.dto.PagedResponse;
import com.example.demo.dto.review.ReviewResponse;
import com.example.demo.dto.review.ReviewSearchListRequest;
import com.example.demo.dto.review.ReviewSearchListResponse;

public interface ReviewQueryService {

	PagedResponse<ReviewSearchListResponse> searchWithPagingByCafeId(ReviewSearchListRequest request);

	ReviewResponse searchOne(Long reviewId);
}
