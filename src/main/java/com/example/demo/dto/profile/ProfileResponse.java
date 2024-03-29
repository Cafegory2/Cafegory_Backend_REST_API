package com.example.demo.dto.profile;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
public class ProfileResponse {
	private final String name;
	private final String thumbnailImg;
	private final String introduction;
}
