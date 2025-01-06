package com.example.demo.auth.service.token;

import static com.example.demo.auth.implement.tokenmanagerment.TokenClaims.*;
import static com.example.demo.domain.exception.ExceptionType.*;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.demo.auth.implement.token.JwtAccessToken;
import com.example.demo.auth.implement.token.JwtClaims;
import com.example.demo.auth.implement.token.JwtTokenValidator;
import com.example.demo.auth.implement.tokenmanagerment.JwtCafegoryTokenManager;
import com.example.demo.auth.implement.tokenmanagerment.JwtTokenManager;
import com.example.demo.domain.exception.ExceptionType;
import com.example.demo.domain.exception.JwtTokenAuthenticationException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class JwtTokenManagementService {

	private final JwtCafegoryTokenManager jwtCafegoryTokenManager;
	private final JwtTokenManager jwtTokenManager;
	private final JwtTokenValidator jwtTokenValidator;

	public JwtAccessToken verifyAndRefreshAccessToken(final String accessToken, final String refreshToken) {
		jwtTokenValidator.validateNullToken(accessToken, JWT_ACCESS_TOKEN_MISSING);
		jwtTokenValidator.validateNullToken(refreshToken, ExceptionType.JWT_REFRESH_TOKEN_MISSING);

		JwtClaims accessTokenClaims = verifyAndExtractAccessClaims(accessToken);
		JwtClaims refreshTokenClaims = verifyAndExtractRefreshClaims(refreshToken);

		jwtTokenValidator.validateTokenSubjectMatch(accessTokenClaims, refreshTokenClaims);

		return jwtCafegoryTokenManager.createAccessToken(
			Map.of(SUBJECT.getValue(), refreshTokenClaims.getSubject())
		);
	}

	private JwtClaims verifyAndExtractRefreshClaims(final String refreshToken) {
		jwtTokenManager.validateClaim(refreshToken, TOKEN_TYPE.getValue(), REFRESH_TOKEN.getValue());
		return jwtTokenManager.verifyAndExtractClaims(refreshToken);
	}

	private JwtClaims verifyAndExtractAccessClaims(final String accessToken) {
		try {
			return jwtTokenManager.verifyAndExtractClaims(accessToken);
		} catch (JwtTokenAuthenticationException e) {
			if (e.getExceptionType() == JWT_EXPIRED) {
				return e.getClaims();
			}
			throw e;
		}
	}
}
