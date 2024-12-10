package com.example.demo.trash.infrastructure.oauth2;

import java.util.Map;

import com.example.demo.trash.dto.oauth2.OAuth2Provider;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OAuth2StrategyFactory {

	private final Map<OAuth2Provider, OAuth2TokenRequester> tokenRequesters;
	private final Map<OAuth2Provider, OAuth2ProfileRequester> profileRequesters;

	public OAuth2TokenRequester getTokenRequester(OAuth2Provider provider) {
		return tokenRequesters.get(provider);
	}

	public OAuth2ProfileRequester getProfileRequester(OAuth2Provider provider) {
		return profileRequesters.get(provider);
	}
}
