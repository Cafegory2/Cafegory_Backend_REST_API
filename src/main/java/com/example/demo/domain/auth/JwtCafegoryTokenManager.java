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
	public CafegoryToken createToken(Map<String, Object> memberInformation) {
		Date issuedAt = Date.from(Instant.now());
		String accessToken = makeAccessToken(memberInformation, issuedAt);
		String refreshToken = makeRefreshToken(accessToken, memberInformation, issuedAt);
		return new CafegoryToken(accessToken, refreshToken);
	}

	@Override
	public long getIdentityId(String accessToken) {
		Claims claims = jwtManager.decode(accessToken);
		return claims.get("memberId", Long.class);
	}

	@Override
	public boolean canRefresh(String refreshToken) {
		try {
			Claims claims = jwtManager.decode(refreshToken);
			String tokenType = claims.get("tokenType", String.class);
			if (!tokenType.equals("refresh")) {
				return false;
			}
			String accessToken = claims.get("accessToken", String.class);
			return jwtManager.isExpired(accessToken);
		} catch (IllegalArgumentException e) {
			return false;
		}
	}

	@Override
	public boolean isAccessToken(String token) {
		try {
			Claims claims = jwtManager.decode(token);
			String tokenType = claims.get("tokenType", String.class);
			return tokenType.equals("access");
		} catch (Exception e) {
			return false;
		}
	}

	private String makeAccessToken(Map<String, Object> memberInformation, Date issuedAt) {
		jwtManager.setLife(issuedAt, accessTokenLifeAsSeconds);
		for (String key : memberInformation.keySet()) {
			Object value = memberInformation.get(key);
			jwtManager.claim(key, value);
		}
		jwtManager.claim("tokenType", "access");
		return jwtManager.make();
	}

	private String makeRefreshToken(String accessToken, Map<String, Object> memberInformation, Date issuedAt) {
		jwtManager.setLife(issuedAt, refreshTokenLifeAsSeconds);
		for (String key : memberInformation.keySet()) {
			Object value = memberInformation.get(key);
			jwtManager.claim(key, value);
		}
		jwtManager.claim("accessToken", accessToken);
		jwtManager.claim("tokenType", "refresh");
		return jwtManager.make();
	}
}
