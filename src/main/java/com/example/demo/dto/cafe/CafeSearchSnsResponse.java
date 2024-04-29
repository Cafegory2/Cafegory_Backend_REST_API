package com.example.demo.dto.cafe;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CafeSearchSnsResponse {
	private final String name;
	private final String url;
}
