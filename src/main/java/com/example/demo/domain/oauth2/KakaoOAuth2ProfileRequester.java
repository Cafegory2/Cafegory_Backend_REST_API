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

import com.example.demo.dto.oauth2.KakaoOAuth2Profile;
import com.example.demo.dto.oauth2.OAuth2Profile;
import com.example.demo.dto.oauth2.OAuth2Token;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class KakaoOAuth2ProfileRequester implements OAuth2ProfileRequester {
	private final RestTemplate restTemplate;
	@Value("${oauth.kakao.url.api}")
	private String apiUrl;

	@Override
	public OAuth2Profile getOAuth2Profile(OAuth2Token oAuth2Token) {
		String url = makeUrl();
		HttpHeaders httpHeaders = makeHeader(oAuth2Token);
		MultiValueMap<String, String> body = makeBody();
		ResponseEntity<KakaoOAuth2Profile> kakaoOAuth2ProfileResponse = callKakaoProfileApi(url, httpHeaders, body);
		validateKakaoProfileApiResponse(kakaoOAuth2ProfileResponse);
		return kakaoOAuth2ProfileResponse.getBody();
	}

	private static void validateKakaoProfileApiResponse(ResponseEntity<KakaoOAuth2Profile> kakaoOAuth2ProfileResponse) {
		if (kakaoOAuth2ProfileResponse.getStatusCode() != HttpStatus.OK) {
			throw new RuntimeException("카카오에서 거부했습니다.");
		}
	}

	private ResponseEntity<KakaoOAuth2Profile> callKakaoProfileApi(String url, HttpHeaders httpHeaders,
		MultiValueMap<String, String> body) {
		HttpEntity<?> request = new HttpEntity<>(body, httpHeaders);
		return restTemplate.postForEntity(url, request, KakaoOAuth2Profile.class);
	}

	private static MultiValueMap<String, String> makeBody() {
		MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
		body.add("property_keys", "[\"kakao_account.email\", \"kakao_account.profile\"]");
		return body;
	}

	private static HttpHeaders makeHeader(OAuth2Token oAuth2Token) {
		String accessToken = oAuth2Token.getAccessToken();
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		httpHeaders.set("Authorization", "Bearer " + accessToken);
		return httpHeaders;
	}

	private String makeUrl() {
		return apiUrl + "/v2/user/me";
	}
}
