package com.example.demo.domain.auth;

import java.time.Instant;
import java.util.Date;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.example.demo.dto.auth.CafegoryToken;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtCafegoryTokenManager implements CafegoryTokenManager {
	private static final int accessTokenLifeAsSeconds = 3600;
	private static final int refreshTokenLifeAsSeconds = 3600 * 24 * 7;
	private final JwtManager jwtManager;

	@Override
	public CafegoryToken createToken(Map<String, String> memberInformation) {
		Date issuedAt = Date.from(Instant.now());
		String accessToken = makeAccessToken(memberInformation, issuedAt);
		String refreshToken = makeRefreshToken(accessToken, memberInformation, issuedAt);
		return new CafegoryToken(accessToken, refreshToken);
	}

	@Override
	public long getIdentityId(String accessToken) {
		Claims claims = jwtManager.decode(accessToken);
		return Long.parseLong(claims.get("memberId", String.class));
	}

	private String makeAccessToken(Map<String, String> memberInformation, Date issuedAt) {
		jwtManager.setLife(issuedAt, accessTokenLifeAsSeconds);
		for (String key : memberInformation.keySet()) {
			String value = memberInformation.get(key);
			jwtManager.claim(key, value);
		}
		return jwtManager.make();
	}

	private String makeRefreshToken(String accessToken, Map<String, String> memberInformation, Date issuedAt) {
		jwtManager.setLife(issuedAt, refreshTokenLifeAsSeconds);
		for (String key : memberInformation.keySet()) {
			String value = memberInformation.get(key);
			jwtManager.claim(key, value);
		}
		jwtManager.claim("accessToken", accessToken);
		return jwtManager.make();
	}
}
