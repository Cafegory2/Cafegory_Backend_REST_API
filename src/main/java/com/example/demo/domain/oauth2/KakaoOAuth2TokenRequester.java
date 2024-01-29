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

import com.example.demo.dto.oauth2.KakaoOAuth2Token;
import com.example.demo.dto.oauth2.OAuth2Provider;
import com.example.demo.dto.oauth2.OAuth2Token;
import com.example.demo.dto.oauth2.OAuth2TokenRequest;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class KakaoOAuth2TokenRequester implements OAuth2TokenRequester {
	private final RestTemplate restTemplate;
	@SuppressWarnings("checkstyle:AbbreviationAsWordInName")
	private final String GRANT_TYPE = "authorization_code";
	@Value("${oauth.kakao.url.auth}")
	private String authUrl;
	@Value("${oauth.kakao.client-id}")
	private String clientId;

	@Override
	public OAuth2Token getToken(OAuth2TokenRequest oAuth2TokenRequest) {
		validateProvider(oAuth2TokenRequest);
		String url = makeUrl();
		HttpHeaders httpHeaders = makeHeader();
		MultiValueMap<String, String> httpBody = makeBody(oAuth2TokenRequest);
		ResponseEntity<KakaoOAuth2Token> kakaoOAuth2TokenResponseEntity = callKakaoTokenApi(url, httpHeaders, httpBody);
		validateKakaoTokenApiResponse(kakaoOAuth2TokenResponseEntity);
		return kakaoOAuth2TokenResponseEntity.getBody();
	}

	private ResponseEntity<KakaoOAuth2Token> callKakaoTokenApi(String url, HttpHeaders httpHeaders,
		MultiValueMap<String, String> httpBody) {
		HttpEntity<?> request = new HttpEntity<>(httpBody, httpHeaders);
		return restTemplate.postForEntity(url, request, KakaoOAuth2Token.class);
	}

	private static void validateKakaoTokenApiResponse(
		ResponseEntity<KakaoOAuth2Token> kakaoOAuth2TokenResponseEntity) {
		if (kakaoOAuth2TokenResponseEntity.getStatusCode() != HttpStatus.OK) {
			throw new RuntimeException("토큰이 잘못되었습니다.");
		}
	}

	private String makeUrl() {
		return authUrl + "/oauth/token";
	}

	private MultiValueMap<String, String> makeBody(OAuth2TokenRequest oAuth2TokenRequest) {
		MultiValueMap<String, String> httpBody = oAuth2TokenRequest.getParameters();
		httpBody.add("grant_type", GRANT_TYPE);
		httpBody.add("client_id", clientId);
		return httpBody;
	}

	private static HttpHeaders makeHeader() {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		return httpHeaders;
	}

	private static void validateProvider(OAuth2TokenRequest oAuth2TokenRequest) {
		if (oAuth2TokenRequest.getProvider() != OAuth2Provider.KAKAO) {
			throw new IllegalArgumentException("잘못된 OAuth2.0 요청입니다.");
		}
	}
}
