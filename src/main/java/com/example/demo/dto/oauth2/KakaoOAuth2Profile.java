package com.example.demo.dto.oauth2;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@JsonIgnoreProperties(value = {"kakaoAccount"}, allowSetters = true)
public class KakaoOAuth2Profile implements OAuth2Profile {
	@JsonProperty("kakao_account")
	public void setKakaoAccount(KakaoAccount kakaoAccount) {
		this.kakaoAccount = kakaoAccount;
	}

	private KakaoAccount kakaoAccount;

	@Getter
	@JsonIgnoreProperties(ignoreUnknown = true)
	static class KakaoAccount {
		private KakaoProfile profile;
		private String email;
	}

	@Getter
	@JsonIgnoreProperties(ignoreUnknown = true)
	static class KakaoProfile {
		private String nickname;
		@JsonProperty("profile_image_url")
		private String profileImageUrl;
	}

	@Override
	public String getNickName() {
		return kakaoAccount.profile.nickname;
	}

	@Override
	public String getProfileImgUrl() {
		return kakaoAccount.profile.profileImageUrl;
	}

	@Override
	public String getEmailAddress() {
		return kakaoAccount.email;
	}
}
