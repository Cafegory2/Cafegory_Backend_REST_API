package com.example.demo.domain.oauth2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.example.demo.dto.oauth2.NaverOAuth2Token;
import com.example.demo.dto.oauth2.OAuth2Provider;
import com.example.demo.dto.oauth2.OAuth2Token;
import com.example.demo.dto.oauth2.OAuth2TokenRequest;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class NaverOAuth2TokenRequester extends AbstractOAuth2TokenRequester {
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
	protected OAuth2Provider getOAuth2Provider() {
		return OAuth2Provider.NAVER;
	}

	protected String makeUrl() {
		return authUrl + "/oauth2.0/token";
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
		return restTemplate.postForEntity(url, request, NaverOAuth2Token.class);
	}
}
