package com.example.demo.domain.auth;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.Instant;
import java.util.Date;

import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JwtManagerTest {

	private final String testSecret = "01234567890123456789012345678901234567890123456789";
	private final JwtManager sut = new JwtManager(testSecret);

	@Test
	@DisplayName("JWT 토큰을 검증한다.")
	void verify_jwt() {
		//given
		String jwt = sut.newTokenBuilder()
				.addClaim("a", "a")
				.addClaim("b", "b")
				.issuedAt(Date.from(Instant.now()))
				.lifeTimeAsSeconds(3600)
				.build();
		//when
		Claims decoded = sut.verifyAndExtractClaims(jwt);
		//then
		assertAll(
				() -> assertThat(decoded.get("a", String.class)).isEqualTo("a"),
				() -> assertThat(decoded.get("b", String.class)).isEqualTo("b")
		);
	}
}
