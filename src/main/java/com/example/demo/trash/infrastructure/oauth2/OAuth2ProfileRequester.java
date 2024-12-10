package com.example.demo.trash.infrastructure.oauth2;

import com.example.demo.trash.dto.oauth2.OAuth2Profile;
import com.example.demo.trash.dto.oauth2.OAuth2Token;

public interface OAuth2ProfileRequester {

	OAuth2Profile getOAuth2Profile(OAuth2Token oAuth2Token);
}
