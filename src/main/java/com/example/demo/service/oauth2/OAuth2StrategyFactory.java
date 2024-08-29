package com.example.demo.service.oauth2;

import com.example.demo.domain.oauth2.OAuth2ProfileRequester;
import com.example.demo.domain.oauth2.OAuth2TokenRequester;
import com.example.demo.dto.oauth2.OAuth2Provider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class OAuth2StrategyFactory {

    private final Map<OAuth2Provider, OAuth2TokenRequester> tokenRequesters;
    private final Map<OAuth2Provider, OAuth2ProfileRequester> profileRequesters;

    public OAuth2TokenRequester getTokenRequester(OAuth2Provider provider) {
        return tokenRequesters.get(provider);
    }

    public OAuth2ProfileRequester getProfileRequester(OAuth2Provider provider) {
        return profileRequesters.get(provider);
    }
}
