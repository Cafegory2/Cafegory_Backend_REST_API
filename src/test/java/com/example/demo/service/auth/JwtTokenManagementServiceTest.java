package com.example.demo.service.auth;

import static com.example.demo.auth.implement.tokenmanagerment.TokenClaims.*;
import static com.example.demo.builder.JwtTokenBuilder.*;
import static com.example.demo.persister.MemberPersister.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.example.demo.auth.service.token.JwtTokenManagementService;
import com.example.demo.config.ServiceTest;
import com.example.demo.db.member.MemberEntity;
import com.example.demo.domain.exception.ExceptionType;
import com.example.demo.domain.exception.JwtTokenAuthenticationException;

class JwtTokenManagementServiceTest extends ServiceTest {

	@Value(("${jwt.secret}"))
	private String testSecret;

	@Autowired
	private JwtTokenManagementService sut;

	@Test
	@DisplayName("만료되지 않은 액세스 토큰과 만료되지 않은 리프레시 토큰을 검증한다.")
	void verify_access_and_refresh_token() {
		//given
		MemberEntity member = aMember().persist();

		String accessToken = aToken().expiresInOneHour()
			.withClaims(
				Map.of(TOKEN_TYPE.getValue(), ACCESS_TOKEN.getValue(),
					SUBJECT.getValue(), String.valueOf(member.getId()))
			)
			.withKey(testSecret).build();
		String refreshToken = aToken().expiresInOneHour()
			.withClaims(
				Map.of(
					TOKEN_TYPE.getValue(), REFRESH_TOKEN.getValue(),
					SUBJECT.getValue(), String.valueOf(member.getId())))
			.withKey(testSecret).build();
		//when & then
		assertDoesNotThrow(() -> sut.verifyAndRefreshAccessToken(accessToken, refreshToken));
	}

	@Test
	@DisplayName("만료된 액세스 토큰과 만료되지 않은 리프레시 토큰을 검증한다.")
	void verify_expired_access_and_unexpired_refresh_token() {
		//given
		MemberEntity member = aMember().persist();

		String accessToken = aToken().expired()
			.withClaims(
				Map.of(TOKEN_TYPE.getValue(), ACCESS_TOKEN.getValue(),
					SUBJECT.getValue(), String.valueOf(member.getId()))
			)
			.withKey(testSecret).build();
		String refreshToken = aToken().expiresInOneHour()
			.withClaims(
				Map.of(
					TOKEN_TYPE.getValue(), REFRESH_TOKEN.getValue(),
					SUBJECT.getValue(), String.valueOf(member.getId())))
			.withKey(testSecret).build();
		//when & then
		assertDoesNotThrow(() -> sut.verifyAndRefreshAccessToken(accessToken, refreshToken));
	}

	@Test
	@DisplayName("만료된 액세스 토큰과 만료된 리프레시 토큰을 검증한다.")
	void verify_expired_access_and_expired_refresh_token() {
		//given
		MemberEntity member = aMember().persist();

		String accessToken = aToken().expired()
			.withClaims(
				Map.of(TOKEN_TYPE.getValue(), ACCESS_TOKEN.getValue(),
					SUBJECT.getValue(), String.valueOf(member.getId()))
			)
			.withKey(testSecret).build();
		String refreshToken = aToken().expired()
			.withClaims(
				Map.of(
					TOKEN_TYPE.getValue(), REFRESH_TOKEN.getValue(),
					SUBJECT.getValue(), String.valueOf(member.getId())))
			.withKey(testSecret).build();
		//when & then
		assertThatThrownBy(() -> sut.verifyAndRefreshAccessToken(accessToken, refreshToken))
			.isInstanceOf(JwtTokenAuthenticationException.class)
			.hasMessage(ExceptionType.JWT_EXPIRED.getErrorMessage());
	}

	@Test
	@DisplayName("만료되지 않은 액세스 토큰과 만료된 리프레시 토큰을 검증한다.")
	void verify_unexpired_access_and_expired_refresh_token() {
		//given
		MemberEntity member = aMember().persist();

		String accessToken = aToken().expiresInOneHour()
			.withClaims(
				Map.of(TOKEN_TYPE.getValue(), ACCESS_TOKEN.getValue(),
					SUBJECT.getValue(), String.valueOf(member.getId()))
			)
			.withKey(testSecret).build();
		String refreshToken = aToken().expired()
			.withClaims(
				Map.of(
					TOKEN_TYPE.getValue(), REFRESH_TOKEN.getValue(),
					SUBJECT.getValue(), String.valueOf(member.getId())))
			.withKey(testSecret).build();
		//when & then
		assertThatThrownBy(() -> sut.verifyAndRefreshAccessToken(accessToken, refreshToken))
			.isInstanceOf(JwtTokenAuthenticationException.class)
			.hasMessage(ExceptionType.JWT_EXPIRED.getErrorMessage());
	}
}
