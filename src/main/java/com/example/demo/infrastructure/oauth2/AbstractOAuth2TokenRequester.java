package com.example.demo.infrastructure.oauth2;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

import com.example.demo.dto.oauth2.OAuth2Provider;
import com.example.demo.dto.oauth2.OAuth2Token;
import com.example.demo.dto.oauth2.OAuth2TokenRequest;

public abstract class AbstractOAuth2TokenRequester implements OAuth2TokenRequester {
	@Override
	public final OAuth2Token getToken(OAuth2TokenRequest oAuth2TokenRequest) {
		validateProvider(oAuth2TokenRequest);
		String url = makeUrl();
		HttpHeaders httpHeaders = makeHeader();
		MultiValueMap<String, String> httpBody = makeBody(oAuth2TokenRequest);
		ResponseEntity<? extends OAuth2Token> oAuth2TokenResponseEntity = callTokenApi(url, httpHeaders, httpBody);
		validateTokenApiResponse(oAuth2TokenResponseEntity);
		return oAuth2TokenResponseEntity.getBody();
	}

	private void validateProvider(OAuth2TokenRequest oAuth2TokenRequest) {
		if (oAuth2TokenRequest.getProvider() != getOAuth2Provider()) {
			throw new IllegalArgumentException("잘못된 OAuth2.0 요청입니다.");
		}
	}

	protected abstract OAuth2Provider getOAuth2Provider();

	protected abstract String makeUrl();

	protected abstract HttpHeaders makeHeader();

	protected abstract MultiValueMap<String, String> makeBody(OAuth2TokenRequest oAuth2TokenRequest);

	protected abstract ResponseEntity<? extends OAuth2Token> callTokenApi(String url, HttpHeaders httpHeaders,
		MultiValueMap<String, String> httpBody);

	protected void validateTokenApiResponse(ResponseEntity<? extends OAuth2Token> oAuth2TokenResponseEntity) {
		if (oAuth2TokenResponseEntity.getStatusCode() != HttpStatus.OK) {
			throw new RuntimeException("토큰이 잘못되었습니다.");
		}
	}
}
