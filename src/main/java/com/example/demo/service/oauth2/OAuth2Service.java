package com.example.demo.service.oauth2;

import com.example.demo.dto.oauth2.OAuth2Profile;
import com.example.demo.dto.oauth2.OAuth2Provider;
import com.example.demo.dto.oauth2.OAuth2Token;
import com.example.demo.dto.oauth2.OAuth2TokenRequest;

import com.example.demo.implement.oauth2.OAuth2ProfileRequester;
import com.example.demo.implement.oauth2.OAuth2TokenRequester;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OAuth2Service {

    private final OAuth2StrategyFactory oAuth2StrategyFactory;

    public OAuth2Profile fetchMemberProfile(OAuth2TokenRequest oAuth2TokenRequest) {
        OAuth2Provider provider = oAuth2TokenRequest.getProvider();

        OAuth2TokenRequester tokenRequester = oAuth2StrategyFactory.getTokenRequester(provider);
        OAuth2Token token = tokenRequester.getToken(oAuth2TokenRequest);

        OAuth2ProfileRequester profileRequester = oAuth2StrategyFactory.getProfileRequester(provider);
        return profileRequester.getOAuth2Profile(token);
    }
}
