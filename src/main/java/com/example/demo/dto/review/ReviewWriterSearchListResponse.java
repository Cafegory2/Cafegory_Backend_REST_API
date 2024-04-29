package com.example.demo.dto.review;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ReviewWriterSearchListResponse {
	private final long memberId;
	private final String name;
	private final String thumbnailImg;
}
