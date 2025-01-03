package com.example.demo.auth.service.login;

import com.example.demo.auth.dto.oauth2.OAuth2TokenRequest;
import com.example.demo.auth.implement.token.JwtToken;

public interface LoginService {

	JwtToken socialLogin(OAuth2TokenRequest oAuth2TokenRequest);
}
