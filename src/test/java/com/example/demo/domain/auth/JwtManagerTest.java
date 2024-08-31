package com.example.demo.domain.auth;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.Instant;
import java.util.Date;
import java.util.Map;

import com.example.demo.dto.auth.JwtClaims;
import com.example.demo.exception.ExceptionType;
import com.example.demo.exception.JwtCustomException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JwtManagerTest {

	private final String testSecret = "01234567890123456789012345678901234567890123456789";
	private final JwtManager sut = new JwtManager(testSecret);

	@Test
	@DisplayName("JWT 토큰을 검증한다.")
	void verify_jwt() {
		//given
		Date issuedAt = Date.from(Instant.now());
		String jwt = Jwts.builder()
				.header().type("JWT").and()
				.claims(Map.of("a", "a", "b", "b"))
				.issuedAt(issuedAt)
				.expiration(Date.from(issuedAt.toInstant().plusSeconds(3600)))
				.signWith(Keys.hmacShaKeyFor(testSecret.getBytes()))
				.compact();
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
		Date issuedAt = Date.from(Instant.now());
		String jwt = Jwts.builder()
				.header().type("JWT").and()
				.claim("tokenType", "access")
				.issuedAt(issuedAt)
				.expiration(Date.from(issuedAt.toInstant().plusSeconds(3600)))
				.signWith(Keys.hmacShaKeyFor(testSecret.getBytes()))
				.compact();
		//then
		assertDoesNotThrow(() -> sut.validateClaim(jwt, "tokenType", "access"));
	}

	@Test
	@DisplayName("JWT의 Claim이 유효하지 않다.")
	void claim_is_invalid() {
		//given
		Date issuedAt = Date.from(Instant.now());
		String jwt = Jwts.builder()
				.header().type("JWT").and()
				.claim("tokenType", "access")
				.issuedAt(issuedAt)
				.expiration(Date.from(issuedAt.toInstant().plusSeconds(3600)))
				.signWith(Keys.hmacShaKeyFor(testSecret.getBytes()))
				.compact();
		//then
		assertThatThrownBy(() -> sut.validateClaim(jwt, "tokenType", "refreshToken"))
				.isInstanceOf(JwtCustomException.class)
				.hasMessage(ExceptionType.JWT_CLAIM_INVALID.getErrorMessage());
	}
}
