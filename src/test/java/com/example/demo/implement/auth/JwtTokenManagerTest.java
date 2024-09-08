package com.example.demo.implement.auth;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.Instant;
import java.util.Date;
import java.util.Map;

import com.example.demo.dto.auth.JwtClaims;
import com.example.demo.exception.ExceptionType;
import com.example.demo.exception.JwtCustomException;
import com.example.demo.factory.TestJwtFactory;
import com.example.demo.implement.tokenmanagerment.JwtTokenManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JwtTokenManagerTest {

	private final String testSecret = "01234567890123456789012345678901234567890123456789";
	private final JwtTokenManager sut = new JwtTokenManager(testSecret);

	@Test
	@DisplayName("JWT 토큰을 검증한다.")
	void verify_jwt() {
		//given
		String jwt = TestJwtFactory.createAccessToken(
				Map.of("a", "a", "b", "b"),
				Date.from(Instant.now()),
				3600,
				testSecret
		);
		//when
		JwtClaims claims = sut.verifyAndExtractClaims(jwt);
		//then
		assertAll(
				() -> assertThat(claims.getClaim("a")).isEqualTo("a"),
				() -> assertThat(claims.getClaim("b")).isEqualTo("b")
		);
	}

	@Test
	@DisplayName("JWT의 Claim이 유효하다.")
	void claim_is_valid() {
		//given
		String jwt = TestJwtFactory.createAccessToken(
				Map.of("tokenType", "access"),
				Date.from(Instant.now()),
				3600,
				testSecret
		);
		//then
		assertDoesNotThrow(() -> sut.validateClaim(jwt, "tokenType", "access"));
	}

	@Test
	@DisplayName("JWT의 Claim이 유효하지 않다.")
	void claim_is_invalid() {
		//given
		String jwt = TestJwtFactory.createAccessToken(
				Map.of("tokenType", "access"),
				Date.from(Instant.now()),
				3600,
				testSecret
		);
		//then
		assertThatThrownBy(() -> sut.validateClaim(jwt, "tokenType", "refreshToken"))
				.isInstanceOf(JwtCustomException.class)
				.hasMessage(ExceptionType.JWT_CLAIM_INVALID.getErrorMessage());
	}
}
