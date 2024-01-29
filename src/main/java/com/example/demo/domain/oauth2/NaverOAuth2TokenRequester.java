package com.example.demo.domain.oauth2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.example.demo.dto.oauth2.NaverOAuth2Token;
import com.example.demo.dto.oauth2.OAuth2LoginRequest;
import com.example.demo.dto.oauth2.OAuth2Provider;
import com.example.demo.dto.oauth2.OAuth2Token;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class NaverOAuth2TokenRequester implements OAuth2TokenRequester {
	private final RestTemplate restTemplate;
	@SuppressWarnings("checkstyle:AbbreviationAsWordInName")
	private final String GRANT_TYPE = "authorization_code";
	@Value("${oauth.naver.url.auth}")
	private String authUrl;
	@Value("${oauth.naver.client-id}")
	private String clientId;
	@Value("${oauth.naver.client-secret}")
	private String clientSecret;

	@Override
	public OAuth2Token getToken(OAuth2LoginRequest oAuth2LoginRequest) {
		validateProvider(oAuth2LoginRequest);
		String url = makeUrl();
		HttpHeaders httpHeaders = makeHeader();
		MultiValueMap<String, String> httpBody = makeBody(oAuth2LoginRequest);
		ResponseEntity<NaverOAuth2Token> naverOAuth2TokenResponseEntity = callNaverTokenApi(url, httpHeaders, httpBody);
		validateNaverTokenApiResponse(naverOAuth2TokenResponseEntity);
		return naverOAuth2TokenResponseEntity.getBody();
	}

	private ResponseEntity<NaverOAuth2Token> callNaverTokenApi(String url, HttpHeaders httpHeaders,
		MultiValueMap<String, String> httpBody) {
		HttpEntity<?> request = new HttpEntity<>(httpBody, httpHeaders);
		return restTemplate.postForEntity(url, request, NaverOAuth2Token.class);
	}

	private static void validateNaverTokenApiResponse(
		ResponseEntity<NaverOAuth2Token> naverOAuth2TokenResponseEntity) {
		if (naverOAuth2TokenResponseEntity.getStatusCode() != HttpStatus.OK) {
			throw new RuntimeException("토큰이 잘못되었습니다.");
		}
	}

	private String makeUrl() {
		return authUrl + "/oauth2.0/token";
	}

	private MultiValueMap<String, String> makeBody(OAuth2LoginRequest oAuth2LoginRequest) {
		MultiValueMap<String, String> httpBody = oAuth2LoginRequest.getParameters();
		httpBody.add("grant_type", GRANT_TYPE);
		httpBody.add("client_id", clientId);
		httpBody.add("client_secret", clientSecret);
		return httpBody;
	}

	private static HttpHeaders makeHeader() {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		return httpHeaders;
	}

	private static void validateProvider(OAuth2LoginRequest oAuth2LoginRequest) {
		if (oAuth2LoginRequest.getProvider() != OAuth2Provider.NAVER) {
			throw new IllegalArgumentException("잘못된 OAuth2.0 요청입니다.");
		}
	}
}
