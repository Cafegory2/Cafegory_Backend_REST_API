package com.example.demo.dto.review;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ReviewSearchListResponse {
	private final long reviewId;
	private final ReviewWriterSearchListResponse writer;
	private final double rate;
	private final String content;
}
