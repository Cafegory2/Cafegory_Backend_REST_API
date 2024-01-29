package com.example.demo.dto.auth;

import lombok.Data;

@Data
public class CafegoryToken {
	private final String accessToken;
	private final String refreshToken;
}
