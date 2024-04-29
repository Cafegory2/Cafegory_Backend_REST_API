package com.example.demo.dto.review;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReviewResponse {

	private final long reviewId;
	private final ReviewWriterSearchResponse writer;
	private final double rate;
	private final String content;
}
