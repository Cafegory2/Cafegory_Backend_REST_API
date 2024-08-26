package com.example.demo.dto.auth;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CafegoryToken {

	private final String accessToken;
	private final String refreshToken;
}
