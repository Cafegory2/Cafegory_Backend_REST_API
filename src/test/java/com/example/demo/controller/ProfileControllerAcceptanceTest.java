package com.example.demo.controller;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import com.example.demo.config.AcceptanceTest;
import com.example.demo.member.presentation.WelcomeProfileResponse;
import com.example.demo.auth.implement.token.JwtToken;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;

@DisplayName("프로필 관련 기능")
@Slf4j
class ProfileControllerAcceptanceTest extends AcceptanceTest {

	@Test
	@DisplayName("회원가입 환영 페이지에 알맞는 데이터를 조회한다.")
	void welcome() {
		//given
		JwtToken jwtToken = memberSignupHelper.로그인_되어_있음();
		//when
		ExtractableResponse<Response> response = RestAssured.given().log().all()
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.header("Authorization", "Bearer " + jwtToken.getAccessToken())
			.when()
			.get("/profile/welcome")
			.then()
			.log().all()
			.extract();
		//then
		WelcomeProfileResponse welcomeProfileResponse = response.as(WelcomeProfileResponse.class);

		assertThat(welcomeProfileResponse.getNickname()).isEqualTo("testNickname");
		assertThat(welcomeProfileResponse.getProfileUrl()).isNotNull();
	}
}
