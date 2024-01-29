package com.example.demo.dto.oauth2;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import lombok.Data;
import lombok.NonNull;

@Data
public class KakaoOAuth2LoginRequest implements OAuth2LoginRequest {
	@NonNull
	private final String code;

	@Override
	public OAuth2Provider getProvider() {
		return OAuth2Provider.KAKAO;
	}

	@Override
	public MultiValueMap<String, String> getParameters() {
		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add("code", code);
		return map;
	}
}
