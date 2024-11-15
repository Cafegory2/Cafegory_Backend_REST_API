package com.example.demo.trash.infrastructure.oauth2;

import com.example.demo.trash.dto.oauth2.OAuth2Profile;
import com.example.demo.trash.dto.oauth2.OAuth2TokenRequest;

public interface OAuth2Client {

	OAuth2Profile fetchMemberProfile(OAuth2TokenRequest oAuth2TokenRequest);
}
