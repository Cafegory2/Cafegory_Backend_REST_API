package com.example.demo.implement.oauth2;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

import com.example.demo.dto.oauth2.OAuth2Profile;
import com.example.demo.dto.oauth2.OAuth2Token;

public abstract class AbstractOAuth2ProfileRequester implements OAuth2ProfileRequester {
	@Override
	public final OAuth2Profile getOAuth2Profile(OAuth2Token oAuth2Token) {
		String url = makeUrl();
		HttpHeaders httpHeaders = makeHeader(oAuth2Token);
		MultiValueMap<String, String> body = makeBody();
		ResponseEntity<? extends OAuth2Profile> oAuth2ProfileResponse = callProfileApi(url, httpHeaders, body);
		validateProfileApiResponse(oAuth2ProfileResponse);
		return oAuth2ProfileResponse.getBody();
	}

	protected abstract String makeUrl();

	protected abstract HttpHeaders makeHeader(OAuth2Token oAuth2Token);

	protected abstract MultiValueMap<String, String> makeBody();

	protected abstract ResponseEntity<? extends OAuth2Profile> callProfileApi(String url, HttpHeaders httpHeaders,
		MultiValueMap<String, String> body);

	protected abstract void validateProfileApiResponse(ResponseEntity<? extends OAuth2Profile> oAuth2ProfileResponse);

}
