package com.example.demo.config;

import com.example.demo.implement.tokenmanagerment.JwtCafegoryTokenManager;
import com.example.demo.dto.oauth2.OAuth2Provider;
import com.example.demo.infrastructure.oauth2.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.example.demo.implement.tokenmanagerment.JwtTokenManager;

import java.util.Map;

@Configuration
public class AuthConfig {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Bean
    public JwtTokenManager jwtManager() {
        return new JwtTokenManager(jwtSecret);
    }

    @Bean
    public JwtCafegoryTokenManager jwtCafegoryTokenManager() {
        return new JwtCafegoryTokenManager(jwtManager());
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public KakaoOAuth2ProfileRequester kakaoOAuth2ProfileRequester() {
        return new KakaoOAuth2ProfileRequester(restTemplate());
    }

    @Bean
    public NaverOAuth2ProfileRequester naverOAuth2ProfileRequester() {
        return new NaverOAuth2ProfileRequester(restTemplate());
    }

    @Bean
    public KakaoOAuth2TokenRequester kakaoOAuth2TokenRequester() {
        return new KakaoOAuth2TokenRequester(restTemplate());
    }

    @Bean
    public NaverOAuth2TokenRequester naverOAuth2TokenRequester() {
        return new NaverOAuth2TokenRequester(restTemplate());
    }

    @Bean
    public Map<OAuth2Provider, OAuth2TokenRequester> tokenRequesterMap(
    ) {
        return Map.of(
                OAuth2Provider.KAKAO, kakaoOAuth2TokenRequester(),
                OAuth2Provider.NAVER, naverOAuth2TokenRequester()
        );
    }

    @Bean
    public Map<OAuth2Provider, OAuth2ProfileRequester> profileRequesterMap(
    ) {
        return Map.of(
                OAuth2Provider.KAKAO, kakaoOAuth2ProfileRequester(),
                OAuth2Provider.NAVER, naverOAuth2ProfileRequester()
        );
    }

    @Bean
    public OAuth2StrategyFactory oAuth2StrategyFactory() {
        return new OAuth2StrategyFactory(tokenRequesterMap(), profileRequesterMap());
    }

    @Bean
    public OAuth2HandlerImpl oAuth2Handler() {
        return new OAuth2HandlerImpl(oAuth2StrategyFactory());
    }
}
