package com.example.demo.dto.oauth2;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import lombok.Data;
import lombok.NonNull;

@Getter
@RequiredArgsConstructor
public class KakaoOAuth2TokenRequest implements OAuth2TokenRequest {

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
