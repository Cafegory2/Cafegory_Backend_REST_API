package com.example.demo.dto.cafe;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CafeSearchWriterResponse {
	private final long memberId;
	private final String name;
	private final String thumbnailImg;
}
