package com.example.demo.auth.infrastructure.oauth2;

import com.example.demo.auth.dto.oauth2.OAuth2Profile;
import com.example.demo.auth.dto.oauth2.OAuth2TokenRequest;

public interface OAuth2Client {

	OAuth2Profile fetchMemberProfile(OAuth2TokenRequest oAuth2TokenRequest);
}
