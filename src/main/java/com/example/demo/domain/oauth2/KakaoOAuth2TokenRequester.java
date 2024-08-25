package com.example.demo.domain.oauth2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.example.demo.dto.oauth2.KakaoOAuth2Token;
import com.example.demo.dto.oauth2.OAuth2Provider;
import com.example.demo.dto.oauth2.OAuth2Token;
import com.example.demo.dto.oauth2.OAuth2TokenRequest;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class KakaoOAuth2TokenRequester extends AbstractOAuth2TokenRequester {

	private final RestTemplate restTemplate;
	private static final String GRANT_TYPE = "authorization_code";

	@Value("${oauth.kakao.url.auth}")
	private String authUrl;
	@Value("${oauth.kakao.client-id}")
	private String clientId;
	@Value("${oauth.kakao.client-secret}")
	private String clientSecret;

	@Override
	protected OAuth2Provider getOAuth2Provider() {
		return OAuth2Provider.KAKAO;
	}

	protected String makeUrl() {
		return authUrl + "/oauth/token";
	}

	protected HttpHeaders makeHeader() {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		return httpHeaders;
	}

	protected MultiValueMap<String, String> makeBody(OAuth2TokenRequest oAuth2TokenRequest) {
		MultiValueMap<String, String> httpBody = oAuth2TokenRequest.getParameters();
		httpBody.add("grant_type", GRANT_TYPE);
		httpBody.add("client_id", clientId);
		httpBody.add("client_secret", clientSecret);
		return httpBody;
	}

	protected ResponseEntity<? extends OAuth2Token> callTokenApi(String url, HttpHeaders httpHeaders,
		MultiValueMap<String, String> httpBody) {
		HttpEntity<?> request = new HttpEntity<>(httpBody, httpHeaders);
		return restTemplate.postForEntity(url, request, KakaoOAuth2Token.class);
	}
}
