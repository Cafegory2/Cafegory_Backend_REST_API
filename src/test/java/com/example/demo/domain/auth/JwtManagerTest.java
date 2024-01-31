package com.example.demo.domain.auth;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class JwtManagerTest {
	@InjectMocks
	JwtManager jwtManager;

	@BeforeEach
	public void setUp() {
		ReflectionTestUtils.setField(jwtManager, "secret", "01234567890123456789012345678901234567890123456789");
		ReflectionTestUtils.setField(jwtManager, "type", "JWT");
	}

	@Test
	@DisplayName("jwt 생성 클레임과 jwt 디코딩 클레임이 같은지 확인하는 테스트")
	void encodeAndDecode() {
		//given
		Map<String, Object> claims = makeClaims();
		for (String key : claims.keySet()) {
			jwtManager.claim(key, claims.get(key));
		}
		jwtManager.setLife(Date.from(Instant.now()), 100000);
		//when
		String make = jwtManager.make();
		Map<String, Object> decodedClaims = new HashMap<>(jwtManager.decode(make));
		Long exp = (Long)decodedClaims.remove("exp");
		Long iat = (Long)decodedClaims.remove("iat");
		//then
		Assertions.assertThat(exp - iat)
			.isEqualTo(100000);
		Assertions.assertThat(decodedClaims)
			.isEqualTo(claims);
	}

	private static Map<String, Object> makeClaims() {
		Map<String, Object> claims = new HashMap<>();
		claims.put("a", "a");
		claims.put("b", "b");
		claims.put("c", "c");
		claims.put("d", "d");
		claims.put("e", "e");
		return claims;
	}

	@Test
	@DisplayName("만료된 토큰 디코딩시 예외 발생 테스트")
	void decodeFailCauseExpired() {
		Map<String, Object> claims = makeClaims();
		for (String key : claims.keySet()) {
			jwtManager.claim(key, claims.get(key));
		}
		jwtManager.setLife(Date.from(Instant.now()), 0);
		String jwt = jwtManager.make();
		Assertions.assertThatThrownBy(() -> jwtManager.decode(jwt))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("JWT 토큰이 만료되었습니다.");
	}

	@Test
	@DisplayName("깨진 토큰 디코딩시 예외 발생 테스트")
	void decodeFailCauseWrongToken() {
		Map<String, Object> claims = makeClaims();
		for (String key : claims.keySet()) {
			jwtManager.claim(key, claims.get(key));
		}
		jwtManager.setLife(Date.from(Instant.now()), 0);
		String jwt = jwtManager.make() + "34gvagafdga";
		Assertions.assertThatThrownBy(() -> jwtManager.decode(jwt))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("JWT 토큰이 잘못되었습니다.");
	}

	@ParameterizedTest
	@ValueSource(strings = {"", " ", "null"})
	@DisplayName("null 혹은 빈 문자열 디코딩시 예외 발생 테스트")
	void decodeFailCauseNullOrEmpty(String token) {
		if (token.equals("null")) {
			token = null;
		}
		String finalToken = token;
		Assertions.assertThatThrownBy(() -> jwtManager.decode(finalToken))
			.isInstanceOf(IllegalArgumentException.class);
	}
}