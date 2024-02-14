package com.example.demo.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class WriterResponse {

	private final long id;
	private final String name;
	private final String thumbnailImg;

}
