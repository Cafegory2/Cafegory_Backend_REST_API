package com.example.demo.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class WriterResponse {

	private final long memberId;
	private final String name;
	private final String thumbnailImg;

}
