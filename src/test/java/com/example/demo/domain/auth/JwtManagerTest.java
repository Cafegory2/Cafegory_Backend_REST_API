package com.example.demo.domain.auth;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.Instant;
import java.util.Date;
import java.util.stream.Stream;

import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class JwtManagerTest {

	private final String testSecret = "01234567890123456789012345678901234567890123456789";
	private final JwtManager sut = new JwtManager(testSecret);

	@Test
	@DisplayName("JWT 토큰을 디코딩한다.")
	void decode_jwt() {
		//given
		String jwt = sut.newTokenBuilder()
				.addClaim("a", "a")
				.addClaim("b", "b")
				.issuedAt(Date.from(Instant.now()))
				.lifeTimeAsSeconds(3600)
				.build();
		//when
		Claims decoded = sut.decode(jwt);
		//then
		assertAll(
				() -> assertThat(decoded.get("a", String.class)).isEqualTo("a"),
				() -> assertThat(decoded.get("b", String.class)).isEqualTo("b")
		);
	}

	@ParameterizedTest
	@MethodSource("checkTokenExpiration")
	@DisplayName("JWT 토큰이 만료되었는지 확인한다.")
	void check_token_expiration(int lifeTimeAsSeconds, boolean expected) {
		//given
		String jwt = sut.newTokenBuilder()
				.addClaim("a", "a")
				.addClaim("b", "b")
				.issuedAt(Date.from(Instant.now()))
				.lifeTimeAsSeconds(lifeTimeAsSeconds)
				.build();
		//when
		boolean isExpired = sut.isExpired(jwt);
		//then
		assertThat(isExpired).isEqualTo(expected);
	}

	static Stream<Arguments> checkTokenExpiration() {
		return Stream.of(
			Arguments.of(0, true),
			Arguments.of(3600, false)
		);
	}
}
