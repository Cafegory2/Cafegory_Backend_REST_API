package com.example.demo.auth.infrastructure.oauth2;

import com.example.demo.auth.dto.oauth2.OAuth2Token;
import com.example.demo.auth.dto.oauth2.OAuth2TokenRequest;

public interface OAuth2TokenRequester {

	OAuth2Token getToken(OAuth2TokenRequest oAuth2TokenRequest);
}
