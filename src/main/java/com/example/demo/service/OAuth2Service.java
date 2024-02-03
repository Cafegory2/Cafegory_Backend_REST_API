package com.example.demo.service;

import com.example.demo.dto.auth.CafegoryToken;
import com.example.demo.dto.oauth2.OAuth2TokenRequest;

public interface OAuth2Service {
	CafegoryToken joinOrLogin(OAuth2TokenRequest oAuth2TokenRequest);

	CafegoryToken refresh(String refreshToken);
}
