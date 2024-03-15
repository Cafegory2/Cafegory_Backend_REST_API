package com.example.demo.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ReviewSearchResponse {

	private final long reviewId;
	private final WriterResponse writer;
	private final double rate;
	private final String content;
}
