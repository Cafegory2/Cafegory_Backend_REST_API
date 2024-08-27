package com.example.demo.config;

import com.example.demo.domain.auth.JwtCafegoryTokenManager;
import com.example.demo.domain.oauth2.*;
import com.example.demo.dto.oauth2.OAuth2Provider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.example.demo.domain.auth.JwtManager;

import java.util.Map;

@Configuration
public class AuthConfig {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Bean
    public JwtManager jwtManager() {
        return new JwtManager(jwtSecret);
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
    @Qualifier("")
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
}
