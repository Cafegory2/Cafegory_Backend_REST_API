package com.example.demo.domain.oauth2;

import com.example.demo.dto.oauth2.OAuth2LoginRequest;
import com.example.demo.dto.oauth2.OAuth2Token;

public interface OAuth2TokenRequester {
	OAuth2Token getToken(OAuth2LoginRequest oAuth2LoginRequest);
}
