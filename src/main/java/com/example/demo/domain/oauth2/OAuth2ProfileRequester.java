package com.example.demo.domain.oauth2;

import com.example.demo.dto.oauth2.OAuth2Profile;
import com.example.demo.dto.oauth2.OAuth2Token;

public interface OAuth2ProfileRequester {
	OAuth2Profile getOAuth2Profile(OAuth2Token oAuth2Token);
}
