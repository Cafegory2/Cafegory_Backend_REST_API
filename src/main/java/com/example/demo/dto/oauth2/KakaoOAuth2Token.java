package com.example.demo.dto.oauth2;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.ToString;

@Getter
public class KakaoOAuth2Token implements OAuth2Token {

	@JsonProperty("access_token")
	private String accessToken;

	@JsonProperty("token_type")
	private String tokenType;

	@JsonProperty("refresh_token")
	private String refreshToken;

	@JsonProperty("expires_in")
	private String expiresIn;

	@JsonProperty("refresh_token_expires_in")
	private String refreshTokenExpiresIn;

	@JsonProperty("scope")
	private String scope;
}
