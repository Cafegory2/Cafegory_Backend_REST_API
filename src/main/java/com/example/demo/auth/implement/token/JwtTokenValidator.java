package com.example.demo.auth.implement.token;

import static com.example.demo.auth.implement.tokenmanagerment.TokenClaims.*;
import static com.example.demo.domain.exception.ExceptionType.*;

import org.springframework.stereotype.Component;

import com.example.demo.domain.exception.ExceptionType;
import com.example.demo.domain.exception.JwtTokenAuthenticationException;

@Component
public class JwtTokenValidator {

	public void validateNullToken(final String token, ExceptionType exceptionType) {
		if (token == null) {
			throw new JwtTokenAuthenticationException(exceptionType);
		}
	}

	public void validateTokenSubjectMatch(final JwtClaims accessTokenClaims, final JwtClaims refreshTokenClaims) {
		String accessTokenSubject = accessTokenClaims.getClaim(SUBJECT.getValue());
		String refreshTokenSubject = refreshTokenClaims.getClaim(SUBJECT.getValue());

		if (!accessTokenSubject.equals(refreshTokenSubject)) {
			throw new JwtTokenAuthenticationException(JWT_ACCESS_SUB_AND_REFRESH_SUB_NOT_MATCHED);
		}
	}
}
