package com.example.demo.auth.implement.token;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JwtAccessToken {

	private String accessToken;

	@Builder
	private JwtAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
}
