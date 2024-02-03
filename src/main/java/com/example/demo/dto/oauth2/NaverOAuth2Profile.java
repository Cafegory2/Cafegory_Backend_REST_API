package com.example.demo.dto.oauth2;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@JsonIgnoreProperties(value = {"response"}, allowSetters = true)
public class NaverOAuth2Profile implements OAuth2Profile {
	@JsonProperty("response")
	private Response response;

	@Getter
	@JsonIgnoreProperties(ignoreUnknown = true)
	static class Response {
		private String email;
		private String nickname;
		@JsonProperty("profile_image")
		private String profileImageUrl;
	}

	@Override
	public String getNickName() {
		return response.nickname;
	}

	@Override
	public String getProfileImgUrl() {
		return response.profileImageUrl;
	}

	@Override
	public String getEmailAddress() {
		return response.email;
	}
}
