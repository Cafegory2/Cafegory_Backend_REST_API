package com.example.demo.domain.oauth2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.example.demo.dto.oauth2.NaverOAuth2Profile;
import com.example.demo.dto.oauth2.OAuth2Profile;
import com.example.demo.dto.oauth2.OAuth2Token;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class NaverOAuth2ProfileRequester implements OAuth2ProfileRequester {
	private final RestTemplate restTemplate;
	@Value("${oauth.naver.url.api}")
	private String apiUrl;

	@Override
	public OAuth2Profile getOAuth2Profile(OAuth2Token oAuth2Token) {
		String url = makeUrl();
		HttpHeaders httpHeaders = makeHeader(oAuth2Token);
		MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
		ResponseEntity<NaverOAuth2Profile> naverOAuth2ProfileResponse = callNaverProfileApi(url, httpHeaders, body);
		validateNaverProfileApiResponse(naverOAuth2ProfileResponse);
		return naverOAuth2ProfileResponse.getBody();
	}

	private static void validateNaverProfileApiResponse(ResponseEntity<NaverOAuth2Profile> naverOAuth2ProfileResponse) {
		if (naverOAuth2ProfileResponse.getStatusCode() != HttpStatus.OK) {
			throw new RuntimeException("네이버에서 거부했습니다.");
		}
	}

	private ResponseEntity<NaverOAuth2Profile> callNaverProfileApi(String url, HttpHeaders httpHeaders,
		MultiValueMap<String, String> body) {
		HttpEntity<?> request = new HttpEntity<>(body, httpHeaders);
		return restTemplate.postForEntity(url, request, NaverOAuth2Profile.class);
	}

	private static HttpHeaders makeHeader(OAuth2Token oAuth2Token) {
		String accessToken = oAuth2Token.getAccessToken();
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		httpHeaders.set("Authorization", "Bearer " + accessToken);
		return httpHeaders;
	}

	private String makeUrl() {
		return apiUrl + "/v1/nid/me";
	}
}
