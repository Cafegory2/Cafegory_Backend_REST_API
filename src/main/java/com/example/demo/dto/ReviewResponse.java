package com.example.demo.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReviewResponse {

	private final long reviewId;
	private final WriterResponse writer;
	private final double rate;
	private final String content;
}
