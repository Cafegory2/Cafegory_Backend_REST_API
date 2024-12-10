package com.example.demo.trash.service.login;

import com.example.demo.trash.dto.oauth2.OAuth2TokenRequest;
import com.example.demo.trash.implement.token.JwtToken;

public interface LoginService {

	JwtToken socialLogin(OAuth2TokenRequest oAuth2TokenRequest);
}
