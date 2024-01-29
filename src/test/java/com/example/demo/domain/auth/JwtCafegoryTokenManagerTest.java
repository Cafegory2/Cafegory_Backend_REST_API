package com.example.demo.domain.auth;

import java.util.HashMap;
import java.util.Map;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import com.example.demo.dto.auth.CafegoryToken;

@ExtendWith(MockitoExtension.class)
class JwtCafegoryTokenManagerTest {
	@InjectMocks
	JwtManager jwtManager;

	@BeforeEach
	public void setUp() {
		ReflectionTestUtils.setField(jwtManager, "secret", "01234567890123456789012345678901234567890123456789");
		ReflectionTestUtils.setField(jwtManager, "type", "JWT");
	}

	@Test
	@DisplayName("리프레쉬 토큰 제대로 생성되는지 테스트")
	void refreshToken() {
		//given
		JwtCafegoryTokenManager jwtCafegoryTokenManager = new JwtCafegoryTokenManager(jwtManager);
		Map<String, String> memberInfo = Map.of("key1", "value1", "key2", "value2");
		CafegoryToken cafegoryToken = jwtCafegoryTokenManager.createToken(memberInfo);
		String accessToken = cafegoryToken.getAccessToken();
		String refreshToken = cafegoryToken.getRefreshToken();
		//when
		Map<String, Object> decodedRefreshTokenClaims = new HashMap<>(jwtManager.decode(refreshToken));
		Long exp = (Long)decodedRefreshTokenClaims.remove("exp");
		Long iat = (Long)decodedRefreshTokenClaims.remove("iat");
		String accessToken1 = (String)decodedRefreshTokenClaims.remove("accessToken");
		//then
		Assertions.assertThat(exp - iat)
			.isEqualTo(3600 * 24 * 7);
		Assertions.assertThat(decodedRefreshTokenClaims)
			.isEqualTo(memberInfo);
		Assertions.assertThat(accessToken1)
			.isEqualTo(accessToken);
	}

	@Test
	@DisplayName("엑세스 토큰 제대로 생성되는지 테스트")
	void accessToken() {
		//given
		JwtCafegoryTokenManager jwtCafegoryTokenManager = new JwtCafegoryTokenManager(jwtManager);
		Map<String, String> memberInfo = Map.of("key1", "value1", "key2", "value2");
		CafegoryToken cafegoryToken = jwtCafegoryTokenManager.createToken(memberInfo);
		String accessToken = cafegoryToken.getAccessToken();
		//when
		Map<String, Object> decodedAccessTokenClaims = new HashMap<>(jwtManager.decode(accessToken));
		Long exp = (Long)decodedAccessTokenClaims.remove("exp");
		Long iat = (Long)decodedAccessTokenClaims.remove("iat");
		//then
		Assertions.assertThat(exp - iat)
			.isEqualTo(3600);
		Assertions.assertThat(decodedAccessTokenClaims)
			.isEqualTo(memberInfo);
	}

	private CafegoryToken createToken() {
		JwtCafegoryTokenManager jwtCafegoryTokenManager = new JwtCafegoryTokenManager(jwtManager);
		Map<String, String> memberInfo = Map.of("key1", "value1", "key2", "value2");
		return jwtCafegoryTokenManager.createToken(memberInfo);
	}
}