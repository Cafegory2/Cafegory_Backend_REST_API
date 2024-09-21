package com.example.demo.service.login;

import com.example.demo.dto.oauth2.OAuth2TokenRequest;
import com.example.demo.implement.token.JwtToken;

public interface LoginService {

    JwtToken socialLogin(OAuth2TokenRequest oAuth2TokenRequest);
}
