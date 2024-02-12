package com.example.demo.domain.auth;

import static com.example.demo.exception.ExceptionType.*;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.demo.dto.auth.CafegoryToken;
import com.example.demo.exception.CafegoryException;

class JwtCafegoryTokenManagerTest {
	private final String testSecret = "01234567890123456789012345678901234567890123456789";
	JwtManager jwtManager = new JwtManager(testSecret);

	@Test
	@DisplayName("리프레쉬 토큰 제대로 생성되는지 테스트")
	void refreshToken() {
		//given
		JwtCafegoryTokenManager jwtCafegoryTokenManager = new JwtCafegoryTokenManager(jwtManager);
		Map<String, Object> memberInfo = Map.of("key1", "value1", "key2", "value2");
		CafegoryToken cafegoryToken = jwtCafegoryTokenManager.createToken(memberInfo);
		String accessToken = cafegoryToken.getAccessToken();
		String refreshToken = cafegoryToken.getRefreshToken();
		//when
		Map<String, Object> decodedRefreshTokenClaims = new HashMap<>(jwtManager.decode(refreshToken));
		Long exp = (Long)decodedRefreshTokenClaims.get("exp");
		Long iat = (Long)decodedRefreshTokenClaims.get("iat");
		String accessToken1 = (String)decodedRefreshTokenClaims.remove("accessToken");
		//then
		Assertions.assertThat(exp - iat)
			.isEqualTo(3600 * 24 * 7);
		Assertions.assertThat(decodedRefreshTokenClaims.entrySet())
			.containsAll(memberInfo.entrySet());
		Assertions.assertThat(accessToken1)
			.isEqualTo(accessToken);
	}

	@Test
	@DisplayName("엑세스 토큰 제대로 생성되는지 테스트")
	void accessToken() {
		//given
		JwtCafegoryTokenManager jwtCafegoryTokenManager = new JwtCafegoryTokenManager(jwtManager);
		Map<String, Object> memberInfo = Map.of("key1", "value1", "key2", "value2");
		CafegoryToken cafegoryToken = jwtCafegoryTokenManager.createToken(memberInfo);
		String accessToken = cafegoryToken.getAccessToken();
		//when
		Map<String, Object> decodedAccessTokenClaims = new HashMap<>(jwtManager.decode(accessToken));
		Long exp = (Long)decodedAccessTokenClaims.remove("exp");
		Long iat = (Long)decodedAccessTokenClaims.remove("iat");
		//then
		Assertions.assertThat(exp - iat)
			.isEqualTo(3600);
		Assertions.assertThat(decodedAccessTokenClaims.entrySet())
			.containsAll(memberInfo.entrySet());
	}

	@Test
	@DisplayName("토큰에서 인증 식별자 추출 테스트")
	void getIdentityId() {
		long id = 1L;
		CafegoryToken token = createToken(id);
		String accessToken = token.getAccessToken();
		JwtCafegoryTokenManager jwtCafegoryTokenManager = new JwtCafegoryTokenManager(jwtManager);
		long identityId = jwtCafegoryTokenManager.getIdentityId(accessToken);
		Assertions.assertThat(identityId).isEqualTo(id);
	}

	private CafegoryToken createToken(long id) {
		JwtCafegoryTokenManager jwtCafegoryTokenManager = new JwtCafegoryTokenManager(jwtManager);
		Map<String, Object> memberInfo = Map.of("memberId", id, "key1", "value1", "key2", "value2");
		return jwtCafegoryTokenManager.createToken(memberInfo);
	}

	@Test
	@DisplayName("정상적인 리프레쉬 토큰이 주어지면, 재발행이 가능하다고 하는지 확인")
	void canRefresh() {
		String refreshToken = makeRefreshAbleToken();
		JwtCafegoryTokenManager jwtCafegoryTokenManager = new JwtCafegoryTokenManager(jwtManager);
		boolean canRefresh = jwtCafegoryTokenManager.canRefresh(refreshToken);
		Assertions.assertThat(canRefresh).isEqualTo(true);
	}

	private String makeRefreshAbleToken() {
		jwtManager.claim("memberId", "1");
		jwtManager.claim("tokenType", "access");
		jwtManager.setLife(Date.from(Instant.now()), 0);
		String accessToken = jwtManager.make();
		jwtManager.claim("memberId", "1");
		jwtManager.claim("accessToken", accessToken);
		jwtManager.claim("tokenType", "refresh");
		jwtManager.setLife(Date.from(Instant.now()), 100000);
		return jwtManager.make();
	}

	@Test
	@DisplayName("엑세스 토큰을 리프레쉬 토큰으로 사용하면, 재발행 불가능하다고 하는지 확인")
	void canRefreshFalseCauseTokenIsAccessToken() {
		String refreshToken = makeAccessToken();
		JwtCafegoryTokenManager jwtCafegoryTokenManager = new JwtCafegoryTokenManager(jwtManager);
		boolean canRefresh = jwtCafegoryTokenManager.canRefresh(refreshToken);
		Assertions.assertThat(canRefresh).isEqualTo(false);
	}

	private String makeAccessToken() {
		jwtManager.claim("memberId", "1");
		jwtManager.claim("tokenType", "access");
		jwtManager.setLife(Date.from(Instant.now()), 10000);
		return jwtManager.make();
	}

	@Test
	@DisplayName("엑세스 토큰이 만료되지 않은 리프레쉬 토큰이 주어지면, 재발행이 불가능하다고 하는지 확인")
	void canRefreshFalseCauseAccessTokenNotExpired() {
		String refreshToken = makeAccessTokenNotExpiredRefreshToken();
		JwtCafegoryTokenManager jwtCafegoryTokenManager = new JwtCafegoryTokenManager(jwtManager);
		boolean canRefresh = jwtCafegoryTokenManager.canRefresh(refreshToken);
		Assertions.assertThat(canRefresh).isEqualTo(false);
	}

	private String makeAccessTokenNotExpiredRefreshToken() {
		jwtManager.claim("memberId", "1");
		jwtManager.claim("tokenType", "access");
		jwtManager.setLife(Date.from(Instant.now()), 10000);
		String accessToken = jwtManager.make();
		jwtManager.claim("memberId", "1");
		jwtManager.claim("accessToken", accessToken);
		jwtManager.claim("tokenType", "refresh");
		jwtManager.setLife(Date.from(Instant.now()), 100000);
		return jwtManager.make();
	}

	@Test
	@DisplayName("만료된 리프레쉬 토큰이 주어지면, 재발행이 불가능하다고 하는지 확인")
	void canRefreshFalseCauseRefreshTokenExpired() {
		String refreshToken = makeExpiredRefreshToken();
		JwtCafegoryTokenManager jwtCafegoryTokenManager = new JwtCafegoryTokenManager(jwtManager);
		Assertions.assertThatThrownBy(() -> jwtCafegoryTokenManager.canRefresh(refreshToken))
			.isInstanceOf(CafegoryException.class)
			.hasMessage(JWT_EXPIRED.getErrorMessage());
	}

	private String makeExpiredRefreshToken() {
		jwtManager.claim("memberId", "1");
		jwtManager.claim("tokenType", "access");
		jwtManager.setLife(Date.from(Instant.now()), 0);
		String accessToken = jwtManager.make();
		jwtManager.claim("memberId", "1");
		jwtManager.claim("accessToken", accessToken);
		jwtManager.claim("tokenType", "refresh");
		jwtManager.setLife(Date.from(Instant.now()), 0);
		return jwtManager.make();
	}
}
