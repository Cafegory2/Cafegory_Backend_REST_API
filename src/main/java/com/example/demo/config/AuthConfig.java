package com.example.demo.config;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.example.demo.trash.dto.oauth2.OAuth2Provider;
import com.example.demo.trash.implement.tokenmanagerment.JwtCafegoryTokenManager;
import com.example.demo.trash.implement.tokenmanagerment.JwtTokenManager;
import com.example.demo.trash.infrastructure.oauth2.KakaoOAuth2ProfileRequester;
import com.example.demo.trash.infrastructure.oauth2.KakaoOAuth2TokenRequester;
import com.example.demo.trash.infrastructure.oauth2.NaverOAuth2ProfileRequester;
import com.example.demo.trash.infrastructure.oauth2.NaverOAuth2TokenRequester;
import com.example.demo.trash.infrastructure.oauth2.OAuth2HandlerImpl;
import com.example.demo.trash.infrastructure.oauth2.OAuth2ProfileRequester;
import com.example.demo.trash.infrastructure.oauth2.OAuth2StrategyFactory;
import com.example.demo.trash.infrastructure.oauth2.OAuth2TokenRequester;

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
