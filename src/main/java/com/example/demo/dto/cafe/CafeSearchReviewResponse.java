package com.example.demo.dto.cafe;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CafeSearchReviewResponse {
	private final long reviewId;
	private final CafeSearchWriterResponse writer;
	private final double rate;
	private final String content;
}
